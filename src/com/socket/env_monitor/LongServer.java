package com.socket.env_monitor;

import java.io.IOException;
import java.io.InputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Thread {
	Socket socket = null;
	ServerSocket server = null;
	public Server(int port) throws BindException {
		try {
			server = new ServerSocket(port);
		} catch (BindException e) {
			e.printStackTrace();
			throw new BindException("端口号已被占用，请换一个！");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BindException("启动服务器异常！");
		}
	}

	@Override
	public void run() {
		super.run();
		try {
//			East.insert("服务端启动监听");
			while (true) {
				socket = server.accept();
//				East.insert("-start【"+socket.getInetAddress().getHostAddress()+"】-");
//				East.insert("客户端连接成功");
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

//					East.insert("收到数据：[" + new String(buf, 0, len) + "]");
					String data_t = new String(buf, 0, len);
					if (!data_t.matches("^[A-Fa-f0-9]+$")) {
//						East.insert("数据非法");
					}else {
						SocketEntity entity = new DataTreating().treat(data_t);
						if (entity == null) {
							System.out.println("实体异常");
//							East.insert("处理" + socket.getInetAddress().getHostAddress() + " 数据异常");
							return;
						}
						boolean saveBeans = new DBHelper().saveBeans(entity);
						if (!saveBeans) {
							System.out.println("数据库处理异常");
//							East.insert("处理" + socket.getInetAddress().getHostAddress() + " 数据库异常");
							return;
						}
//						East.insert("数据结果【" + entity.getString() + "】");
					}
//					East.insert("-stop【"+socket.getInetAddress().getHostAddress()+"】-");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (socket != null) {
//						East.insert(socket.getInetAddress().getHostAddress() + " 客户端关闭成功");
//						East.btnStart.setEnabled(true);
//						East.btnStart.setText("重新启动");
						socket.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}

		public void close() throws IOException {
			socket.close();
		}
	}

}
