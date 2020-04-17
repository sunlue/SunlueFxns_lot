package com.socket.tourists_number;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.ArrayList;

import org.ini4j.Profile.Section;

import com.util.Util;

public class Server {
	private int port;

	private static ServerSocket serverSocket;
	private static ServerThread serverThread;
	public static ArrayList<ClientThread> clients;

	public Server(int port) throws Exception {
		this.port = port;
	}

	public void start() throws Exception {

		Section tourists_number = Util.getIni().get("tourists_number");
		if (port == Integer.parseInt(tourists_number.get("port")) && tourists_number.get("isStart")=="true") {
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

	@SuppressWarnings("deprecation")
	public void stop() throws Exception {
		try {
			if (serverThread != null) {
				serverThread.stop();
			}
			for (int i = clients.size() - 1; i >= 0; i--) {
				clients.get(i).stop();
				clients.get(i).socket.close();
				clients.remove(i);
				Action.offline(clients.get(i).socket, clients.get(i).getClient());
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
