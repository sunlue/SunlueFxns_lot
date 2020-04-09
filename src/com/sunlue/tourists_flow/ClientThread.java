package com.sunlue.tourists_flow;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientThread extends Thread {
	private Socket socket;
	private Client client;
	private int sendForWho;
	private ArrayList<ClientThread> clients;
	private static final int FUNC_CODE_REP = 0x42; // 客流数据上传
	private static final int FUNC_CODE_HB = 0x43; // 心跳
	private static final int FUNC_CODE_AUTH = 0x44; // 鉴权
	private byte b[] = new byte[1024];
	
	
	private JTextArea contentArea;
	private JTextField txtHbGap;
	private JTextField txtLastRepTime;
	private JTextField txtSyncTime;
	private static DefaultListModel allListModel;

	public Client getClient() {
		return client;
	}

	public String bytesToHex(byte[] bytes, int lenght) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lenght; i++) {
			sb.append(String.format("%02X ", new Integer(bytes[i] & 0xFF)));
		}

		return sb.toString();
	}

	public int modbusResponseHeartbeat(byte[] b) throws IOException {
		// 解析收到的client端发送的数据
		// 1.版本号4字节，大端字节序，高位在前
		int version = ((b[11] & 0xff) | ((b[10] & 0xff) << 8) | ((b[9] & 0xff) << 16) | ((b[8] & 0xff) << 24));
		// System.out.println("version=" + version);
		// 2.设备序列号
		byte[] devSN = new byte[16];
		System.arraycopy(b, 12, devSN, 0, 15);
		String sn = new String(devSN);
		contentArea.insert("收到心跳数据： version:" + version + ", sn:" + sn + "\r\n", 0);

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
		contentArea.insert("发送" + this.getClient().getName() + ": " + bytesToHex(b, packLen) + "\r\n", 0);

		// 发送数据
		OutputStream os = socket.getOutputStream();

		os.write(b, 0, packLen);

		return sendForWho;
	}

	public int modbusResponseAuth(byte[] b) throws IOException {
		// 解析收到的client端发送的数据
		// 1.版本号4字节，大端字节序，高位在前
		int version = ((b[11] & 0xff) | ((b[10] & 0xff) << 8) | ((b[9] & 0xff) << 16) | ((b[8] & 0xff) << 24));
		// System.out.println("version=" + version);
		// 2.设备序列号
		byte[] devSN = new byte[16];
		System.arraycopy(b, 12, devSN, 0, 15);
		String sn = new String(devSN);
		contentArea.insert("收到鉴权数据： version:" + version + ", sn:" + sn + "\r\n", 0);

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
		contentArea.insert("发送" + this.getClient().getName() + ": " + bytesToHex(b, packLen) + "\r\n", 0);

		// 发送数据
		OutputStream os = socket.getOutputStream();

		os.write(b, 0, packLen);

		return 0;
	}

	private int modbusResponseRepData(byte[] b) throws IOException {
		// 解析收到的client端发送的数据
		// 1.客流记录数量2字节，大端字节序，高位在前
		int num = ((b[9] & 0xff) | ((b[8] & 0xff) << 8));
		System.out.println("num=" + num);
		// 2.客流记录数据
		contentArea.insert("收到客流数据：\r\n", 0);
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
			contentArea.insert(sn + ", in: " + in + ", out: " + out + ", 时间: " + time + "\r\n", 0);
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
		contentArea.insert("发送" + this.getClient().getName() + ": " + bytesToHex(b, packLen) + "\r\n", 0);

		// 发送数据
		OutputStream os = socket.getOutputStream();

		os.write(b, 0, packLen);

		return 0;
	}

	public void modbusDataParse(byte[] b, int lenght) throws IOException {
		byte funCode = b[7];
		switch (funCode) {
		case FUNC_CODE_AUTH:
			modbusResponseAuth(b);
			break;
		case FUNC_CODE_HB:
			modbusResponseHeartbeat(b);
			break;
		case FUNC_CODE_REP:
			modbusResponseRepData(b);
			break;
		default:
			System.out.println("unkonw funcode=" + funCode);
			break;
		}
	}

	// 构造函数
	public ClientThread(Socket socket) {
		this.socket = socket;
		client = new Client(socket.getInetAddress().getHostAddress(), socket.getPort());
	}

	public void run() {
		while (true) {
			try {
				// 接收client数据
				InputStream is = socket.getInputStream();
				int length = is.read(b);
				// System.out.print("recv lenght=" + length + "\r\n");
				if (length > 0) {
					contentArea.insert("收到" + this.getClient().getName() + ": " + bytesToHex(b, length) + "\r\n", 0);
					// 协议解析
					modbusDataParse(b, length);
				} else if (length < 0) {
					// client断开了
					contentArea.insert(this.getClient().getName() + "下线!\r\n", 0);
					try {
						socket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					allListModel.removeElement(client.getName());// 更新在线列表

					// 删除此条客户端服务线程
					for (int i = clients.size() - 1; i >= 0; i--) {
						if (clients.get(i).getClient() == client) {
							ClientThread temp = clients.get(i);
							clients.remove(i);// 删除此用户的服务线程
							temp.stop();// 停止这条服务线程
							return;
						}
					}
				} else {
					continue;
				}
				// System.out.println(bytesToHex(b, length));

			} catch (IOException e) {
				e.printStackTrace();
				contentArea.insert(this.getClient().getName() + "下线!\r\n", 0);
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				allListModel.removeElement(client.getName());// 更新在线列表

				// 删除此条客户端服务线程
				for (int i = clients.size() - 1; i >= 0; i--) {
					if (clients.get(i).getClient() == client) {
						ClientThread temp = clients.get(i);
						clients.remove(i);// 删除此用户的服务线程
						temp.stop();// 停止这条服务线程
						return;
					}
				}
			}
		}
	}

	public String getUserServerPort(String userName) {
		try {

		} catch (Exception e) {

		}

		return "";
	}

	public String getUserServerIp(String userName) {
		try {

		} catch (Exception e) {

		}

		return "";
	}
}
