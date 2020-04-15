package com.sunlue.tourists_flow;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JTextField;

import com.sunlue.page.TouristrFlow;

public class Modbus {
	private static JTextField txtHbGap;
	private static JTextField txtLastRepTime;
	private static JTextField txtSyncTime;
	private static Socket socket;
	private static int sendForWho;
	private static final int FUNC_CODE_REP = 0x42; // 客流数据上传
	private static final int FUNC_CODE_HB = 0x43; // 心跳
	private static final int FUNC_CODE_AUTH = 0x44; // 鉴权

	public static void DataParse(byte[] b, int lenght) throws IOException {
		byte funCode = b[7];
		switch (funCode) {
		case FUNC_CODE_AUTH:
			ResponseAuth(b);
			break;
		case FUNC_CODE_HB:
			ResponseHeartbeat(b);
			break;
		case FUNC_CODE_REP:
			ResponseRepData(b);
			break;
		default:
			System.out.println("unkonw funcode=" + funCode);
			break;
		}
	}
	
	public static int ResponseHeartbeat(byte[] b) throws IOException {
		// 解析收到的client端发送的数据
		// 1.版本号4字节，大端字节序，高位在前
		int version = ((b[11] & 0xff) | ((b[10] & 0xff) << 8) | ((b[9] & 0xff) << 16) | ((b[8] & 0xff) << 24));
		// System.out.println("version=" + version);
		// 2.设备序列号
		byte[] devSN = new byte[16];
		System.arraycopy(b, 12, devSN, 0, 15);
		String sn = new String(devSN);
		TouristrFlow.insertMsg("收到心跳数据： version:" + version + ", sn:" + sn);
		

		// 构建应答数据包
		// 数据长度： 单元标识符1字节 + 功能码1字节 + 结果码1字节
		int length = 1 + 1 + 1;
		// 大端字节序，高位在前
		b[4] = new Integer(length >> 8 & 0xff).byteValue();
		b[5] = new Integer(length & 0xff).byteValue();

		// 结果码1字节
		b[8] = 0x00;

		// 数据包总长度：MBAP头7字节+功能码1字节+数据1字节
		int packLen = 9;
//		TouristrFlow.insertMsg("发送" + ClientThread.getClient().getName() + ": " + bytesToHex(b, packLen));

		// 发送数据
		OutputStream os = socket.getOutputStream();

		os.write(b, 0, packLen);

		return sendForWho;
	}

	public static int ResponseAuth(byte[] b) throws IOException {
		// 解析收到的client端发送的数据
		// 1.版本号4字节，大端字节序，高位在前
		int version = ((b[11] & 0xff) | ((b[10] & 0xff) << 8) | ((b[9] & 0xff) << 16) | ((b[8] & 0xff) << 24));
		// System.out.println("version=" + version);
		// 2.设备序列号
		byte[] devSN = new byte[16];
		System.arraycopy(b, 12, devSN, 0, 15);
		String sn = new String(devSN);
		TouristrFlow.insertMsg("收到鉴权数据： version:" + version + ", sn:" + sn);

		// 构造应答数据包
		// 心跳间隔
		int hbGap = Integer.parseInt(txtHbGap.getText());

		// 数据长度： 单元标识符1字节 + 功能码1字节 + 最后上传时间20字节 + 同步时间20字节 + 心跳间隔4字节
		int length = 1 + 1 + 20 + 20 + 4;
		// 大端字节序，高位在前
		b[4] = new Integer(length >> 8 & 0xff).byteValue();
		b[5] = new Integer(length & 0xff).byteValue();

		// 数据之最后上传时间、同步时间
		byte[] arrayLastRepTime = txtLastRepTime.getText().getBytes();
		byte[] arraySyncTime = txtSyncTime.getText().getBytes();
		System.arraycopy(arrayLastRepTime, 0, b, 8, 19);
		System.arraycopy(arraySyncTime, 0, b, 28, 19);

		// 数据之心跳时间间隔，大端字节序，高位在前
		b[48] = new Integer(hbGap >> 24 & 0xff).byteValue();
		b[49] = new Integer(hbGap >> 16 & 0xff).byteValue();
		b[50] = new Integer(hbGap >> 8 & 0xff).byteValue();
		b[51] = new Integer(hbGap & 0xff).byteValue();

		// 数据包总长度：MBAP头7字节+功能码1字节+数据44字节
		int packLen = 52;
//		TouristrFlow.insertMsg("发送" + ClientThread.getClient().getName() + ": " + bytesToHex(b, packLen));

		// 发送数据
		OutputStream os = socket.getOutputStream();

		os.write(b, 0, packLen);

		return 0;
	}

	private static int ResponseRepData(byte[] b) throws IOException {
		// 解析收到的client端发送的数据
		// 1.客流记录数量2字节，大端字节序，高位在前
		int num = ((b[9] & 0xff) | ((b[8] & 0xff) << 8));
		System.out.println("num=" + num);
//		new DBHelper().insert(num);
		// 2.客流记录数据
		TouristrFlow.insertMsg("收到客流数据");
		int index = 10;
		int in = 0;
		int out = 0;
		byte[] devSN = new byte[16];
		byte[] dateTime = new byte[20];
		for (int i = 0; i < num; i++) {
			System.arraycopy(b, index, devSN, 0, 15);
			index += 16;
			in = ((b[index + 1] & 0xff) | ((b[index] & 0xff) << 8));
			index += 2;
			out = ((b[index + 1] & 0xff) | ((b[index] & 0xff) << 8));
			index += 2;
			System.arraycopy(b, index, dateTime, 0, 19);
			index += 20;
			String sn = new String(devSN);
			String time = new String(dateTime);
			TouristrFlow.insertMsg(sn + ", in: " + in + ", out: " + out + ", 时间: " + time);
		}
//		writeLastRepTime(new String(dateTime));

		// 构建应答数据包
		// 数据长度： 单元标识符1字节 + 功能码1字节 + 结果码1字节
		int length = 1 + 1 + 1;
		// 大端字节序，高位在前
		b[4] = new Integer(length >> 8 & 0xff).byteValue();
		b[5] = new Integer(length & 0xff).byteValue();

		// 结果码1字节
		b[8] = 0x00;

		// 数据包总长度：MBAP头7字节+功能码1字节+数据1字节
		int packLen = 9;
//		TouristrFlow.insertMsg("发送" + ClientThread.getClient().getName() + ": " + bytesToHex(b, packLen));

		// 发送数据
		OutputStream os = socket.getOutputStream();

		os.write(b, 0, packLen);

		return 0;
	}
	
	public static String bytesToHex(byte[] bytes, int lenght) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lenght; i++) {
			sb.append(String.format("%02X ", new Integer(bytes[i] & 0xFF)));
		}

		return sb.toString();
	}


}
