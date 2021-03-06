package com.server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.ini4j.Profile.Section;

import com.action.Action;
import com.util.Util;

/**
 * 套接字服务端管理类
 * 
 * @author xiebing
 */
public class Server {
	private int port;

	private static ServerSocket serverSocket;
	private static ServerThread serverThread;
	public static ArrayList<ClientThread> clients;

	public Server(int port) throws Exception {
		this.port = port;
	}

	/**
	 * 启动一个服务
	 *
	 * @throws Exception
	 */
	public void start() throws Exception {
		String keyPort = "port";
		String keyIsStart = "isStart";
		Section touristsNumber = Util.getIni().get("tourists_number");
		if (port == Integer.parseInt(touristsNumber.get(keyPort))
				&& String.valueOf(true).equals(touristsNumber.get(keyIsStart))) {
			throw new Exception("服务器已启动！");
		}

		try {
			clients = new ArrayList<ClientThread>();
			serverSocket = new ServerSocket(port);
			serverThread = new ServerThread(serverSocket, 20);
			serverThread.start();
		} catch (BindException e) {
			throw new Exception("端口号已被占用，请换一个！");
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new Exception("启动服务器异常！");
		}
	}

	/**
	 * 关闭一个服务
	 *
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public void stop() throws Exception {
		try {
			if (serverThread != null) {
				serverThread.stop();
			}
			for (int i = clients.size() - 1; i >= 0; i--) {
				Socket socket = clients.get(i).socket;
				Client client = clients.get(i).getClient();
				clients.get(i).stop();
				socket.close();
				clients.remove(i);
				Action.offline(socket, client);
			}

			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
}
