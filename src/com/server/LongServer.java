package com.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * 套接字客户端管理类
 * @author xiebing
 */
public class LongServer extends Thread {
	Socket socket = null;
	ServerSocket server = null;

	public LongServer(int port) throws BindException {
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
			System.out.println("服务端启动监听");
			while (true) {
				socket = server.accept();
				System.out.println("-start【" + socket.getInetAddress().getHostAddress() + "】-");
				System.out.println("客户端连接成功");
				new MyRuns(socket).start();
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

	class MyRuns extends Thread implements Runnable {
		Socket socket;

		public MyRuns(Socket socket) {
			super();
			this.socket = socket;
		}
		@Override
		public void run() {
			try {
				InputStream in = socket.getInputStream();
				int len = 0;
				byte[] buf = new byte[1024];
				while ((len = in.read(buf)) != -1) {
					System.out.println("收到数据：[" + new String(buf, 0, len) + "]");
					String data = new String(buf, 0, len);
					if (!data.matches("^[A-Fa-f0-9]+$")) {
						System.out.println("数据非法");
					} else {
						System.out.println("处理数据：" + data);
					}
					System.out.println("-stop【" + socket.getInetAddress().getHostAddress() + "】-");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (socket != null) {
						System.out.println(socket.getInetAddress().getHostAddress() + " 客户端关闭成功");
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
