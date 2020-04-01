package com.sunlue.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.sunlue.DBHelper;
import com.sunlue.DataTreating;
import com.sunlue.Sgui;
import com.sunlue.SocketEntity;

public class Server extends Thread {
	Socket socket = null;
	ServerSocket server = null;

	public Server(int port) {
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		super.run();
		try {
			Sgui.insert("�������������");
			// ͨ����ѭ�����������ӣ������߳�ȥ������Ϣ
			while (true) {
				socket = server.accept();
				Sgui.insert(socket.getInetAddress().getHostAddress() + " �ͻ������ӳɹ�");
				new Thread(new MyRuns(socket)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (server != null) {
					server.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	class MyRuns implements Runnable {
		Socket socket;

		public MyRuns(Socket socket) {
			super();
			this.socket = socket;
		}

		public void run() {
			try {
				InputStream in = socket.getInputStream();
				int len = 0;
				byte[] buf = new byte[1024];
				while ((len = in.read(buf)) != -1) {

					Sgui.insert(socket.getInetAddress().getHostAddress() + "�յ�����\r\n[" + new String(buf, 0, len)+"]");
					System.out.println("�յ��ͻ�������: " + new String(buf, 0, len));
					// ���ݴ���
					String data_t = new String(buf, 0, len);
					SocketEntity entity = new DataTreating().treat(data_t);
					if (entity == null) {
						System.out.println("ʵ���쳣");
						Sgui.insert("����" + socket.getInetAddress().getHostAddress() + " �����쳣");
						return;
					}
					boolean saveBeans = new DBHelper().saveBeans(entity);
					if (!saveBeans) {
						System.out.println("���ݿ⴦���쳣");
						Sgui.insert("����" + socket.getInetAddress().getHostAddress() + " ���ݿ��쳣");
						return;
					}
					Sgui.insert("���ݽ����" + entity.getString()+"��");
					Sgui.insert("-------------------end-----------------------");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (socket != null) {
						Sgui.insert(socket.getInetAddress().getHostAddress() + " �ͻ��˹رճɹ�");
						socket.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

}
