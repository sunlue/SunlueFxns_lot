package com.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.dao.Factory;
import com.dao.TouristsNumberDao;
import com.dao.impl.TouristsNumberDaoImpl;
import com.server.Client;
import com.util.Util;
import com.view.hardware.page.EnvMonitorView;
import com.view.hardware.page.TouristsNumberView;

/**
 * 套接字服务端客流统计数据处理类
 * @author xiebing
 */
public class TouristsNumberService {
	private TouristsNumberDao dao = (TouristsNumberDao) Factory.dao(TouristsNumberDaoImpl.class.getName());
	private Client client;
	private byte[] data;
	private int length;

	private static Socket socket;
	private static int sendForWho;
	/**
	 * 客流数据上传
	 */
	private static final int FUNC_CODE_REP = 0x42;
	/**
	 *  心跳
	 */
	private static final int FUNC_CODE_HB = 0x43;
	/**
	 *  鉴权
	 */
	private static final int FUNC_CODE_AUTH = 0x44;

	public TouristsNumberService(Client client, byte[] data, int length) {
		this.client = client;
		this.length = length;
		this.data = data;
	}

	public int insert(int num, int in, int out, String time) {
		return dao.insert(num, in, out, time);
	}

	public void handle() throws Exception {
		TouristsNumberView.insertMsg("收到" + client.getName() + ":" + bytesToHex(data, length));
		byte funCode = data[7];
		switch (funCode) {
		case FUNC_CODE_AUTH:
			responseAuth(data);
			break;
		case FUNC_CODE_HB:
			responseHeartbeat(data);
			break;
		case FUNC_CODE_REP:
			responseRepData(data);
			break;
		default:
			TouristsNumberView.insertMsg("unkonw funcode=" + funCode);
			break;
		}
	}

	public int responseHeartbeat(byte[] b) throws IOException {
		// 解析收到的client端发送的数据
		// 1.版本号4字节，大端字节序，高位在前
		int version = ((b[11] & 0xff) | ((b[10] & 0xff) << 8) | ((b[9] & 0xff) << 16) | ((b[8] & 0xff) << 24));
		// 2.设备序列号
		byte[] devSn = new byte[16];
		System.arraycopy(b, 12, devSn, 0, 15);
		String sn = new String(devSn);
		TouristsNumberView.insertMsg("收到心跳数据： version:" + version + ", sn:" + sn);

		// 构建应答数据包
		// 数据长度： 单元标识符1字节 + 功能码1字节 + 结果码1字节
		int length = 1 + 1 + 1;
		// 大端字节序，高位在前

		b[4] = Integer.valueOf(length >> 8 & 0xff).byteValue();
		b[5] = Integer.valueOf(length & 0xff).byteValue();

		// 结果码1字节
		b[8] = 0x00;

		// 数据包总长度：MBAP头7字节+功能码1字节+数据1字节
		int packLen = 9;
		TouristsNumberView.insertMsg("发送" + client.getName() + ": " + bytesToHex(b, packLen));

		// 发送数据
		OutputStream os = socket.getOutputStream();

		os.write(b, 0, packLen);

		return sendForWho;
	}

	public int responseAuth(byte[] b) throws IOException {
		// 解析收到的client端发送的数据
		// 1.版本号4字节，大端字节序，高位在前
		int version = ((b[11] & 0xff) | ((b[10] & 0xff) << 8) | ((b[9] & 0xff) << 16) | ((b[8] & 0xff) << 24));
		// 2.设备序列号
		byte[] devSn = new byte[16];
		System.arraycopy(b, 12, devSn, 0, 15);
		String sn = new String(devSn);
		TouristsNumberView.insertMsg("收到鉴权数据： version:" + version + ", sn:" + sn);

		// 构造应答数据包
		// 心跳间隔
		int hbGap = Integer.parseInt(TouristsNumberView.HbGapNum.getText());

		// 数据长度： 单元标识符1字节 + 功能码1字节 + 最后上传时间20字节 + 同步时间20字节 + 心跳间隔4字节
		int length = 1 + 1 + 20 + 20 + 4;
		// 大端字节序，高位在前
		b[4] = Integer.valueOf(length >> 8 & 0xff).byteValue();
		b[5] = Integer.valueOf(length & 0xff).byteValue();

		// 数据之最后上传时间、同步时间
		byte[] arrayLastRepTime = TouristsNumberView.lastRepTimeVal.getText().getBytes();
		byte[] arraySyncTime = TouristsNumberView.syncTimeVal.getText().getBytes();
		System.arraycopy(arrayLastRepTime, 0, b, 8, 19);
		System.arraycopy(arraySyncTime, 0, b, 28, 19);

		// 数据之心跳时间间隔，大端字节序，高位在前
		b[48] = Integer.valueOf(hbGap >> 24 & 0xff).byteValue();
		b[49] = Integer.valueOf(hbGap >> 16 & 0xff).byteValue();
		b[50] = Integer.valueOf(hbGap >> 8 & 0xff).byteValue();
		b[51] = Integer.valueOf(hbGap & 0xff).byteValue();

		// 数据包总长度：MBAP头7字节+功能码1字节+数据44字节
		int packLen = 52;
		TouristsNumberView.insertMsg("发送" + client.getName() + ": " + bytesToHex(b, packLen));
		// 发送数据
		OutputStream os = socket.getOutputStream();
		os.write(b, 0, packLen);
		return 0;
	}

	private int responseRepData(byte[] b) throws IOException {
		// 解析收到的client端发送的数据
		// 1.客流记录数量2字节，大端字节序，高位在前
		int num = ((b[9] & 0xff) | ((b[8] & 0xff) << 8));
		// 2.客流记录数据
		int index = 10;
		int in = 0;
		int out = 0;
		byte[] devSn = new byte[16];
		byte[] dateTime = new byte[20];
		for (int i = 0; i < num; i++) {
			System.arraycopy(b, index, devSn, 0, 15);
			index += 16;
			in = ((b[index + 1] & 0xff) | ((b[index] & 0xff) << 8));
			index += 2;
			out = ((b[index + 1] & 0xff) | ((b[index] & 0xff) << 8));
			index += 2;
			System.arraycopy(b, index, dateTime, 0, 19);
			index += 20;
			String sn = new String(devSn);
			String time = new String(dateTime);
			TouristsNumberView.insertMsg("[" + sn + "]收到客流数据:" + num + ", in: " + in + ", out: " + out + ", 时间: " + time);
			// 数据处理
			int result = dao.insert(num, in, out, time);
			EnvMonitorView.insertMsg("成功处理数据" + result + "条");
		}

		Map<String, String> data = new HashMap<String, String>(1);
		data.put("lastRepTime", new String(dateTime));
		Map<String, Map<String, String>> update = new HashMap<String, Map<String, String>>(1);
		update.put("tourists_number", data);
		Util.updateIni(update);

		// 构建应答数据包
		// 数据长度： 单元标识符1字节 + 功能码1字节 + 结果码1字节
		int length = 1 + 1 + 1;
		// 大端字节序，高位在前
		b[4] = Integer.valueOf(length >> 8 & 0xff).byteValue();
		b[5] = Integer.valueOf(length & 0xff).byteValue();

		// 结果码1字节
		b[8] = 0x00;

		// 数据包总长度：MBAP头7字节+功能码1字节+数据1字节
		int packLen = 9;
		TouristsNumberView.insertMsg("发送" + client.getName() + ": " + bytesToHex(b, packLen));

		// 发送数据
		OutputStream os = socket.getOutputStream();

		os.write(b, 0, packLen);

		return 0;
	}

	public String bytesToHex(byte[] bytes, int lenght) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lenght; i++) {
			sb.append(String.format("%02X ", Integer.valueOf(bytes[i] & 0xFF)));
		}

		return sb.toString();
	}

}
