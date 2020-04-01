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
			Sgui.insert("服务端启动监听");
			// 通过死循环开启长连接，开启线程去处理消息
			while (true) {
				socket = server.accept();
				Sgui.insert(socket.getInetAddress().getHostAddress() + " 客户端连接成功");
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

					Sgui.insert(socket.getInetAddress().getHostAddress() + "收到数据\r\n[" + new String(buf, 0, len)+"]");
					System.out.println("收到客户端数据: " + new String(buf, 0, len));
					// 数据处理
					String data_t = new String(buf, 0, len);
					SocketEntity entity = new DataTreating().treat(data_t);
					if (entity == null) {
						System.out.println("实体异常");
						Sgui.insert("处理" + socket.getInetAddress().getHostAddress() + " 数据异常");
						return;
					}
					boolean saveBeans = new DBHelper().saveBeans(entity);
					if (!saveBeans) {
						System.out.println("数据库处理异常");
						Sgui.insert("处理" + socket.getInetAddress().getHostAddress() + " 数据库异常");
						return;
					}
					Sgui.insert("数据结果【" + entity.getString()+"】");
					Sgui.insert("-------------------end-----------------------");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (socket != null) {
						Sgui.insert(socket.getInetAddress().getHostAddress() + " 客户端关闭成功");
						socket.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

}
