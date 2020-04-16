package com.socket.tourists_number;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.ArrayList;

import com.view.page.TouristsNumberView;


public class Server {
	private int port;

	private static ServerSocket serverSocket;
	private static ServerThread serverThread;
	private static Boolean isStart = false;
	public static ArrayList<ClientThread> clients;

	public Server(int port) {
		this.port = port;
	}

	public void start() throws Exception {
		if (isStart) {
			throw new Exception("服务器已启动！");
		}
		try {
			clients = new ArrayList<ClientThread>();
			serverSocket = new ServerSocket(port);
			serverThread = new ServerThread(serverSocket, 20);
			serverThread.start();
			isStart = true;
		} catch (BindException e) {
			isStart = false;
			throw new Exception("端口号已被占用，请换一个！");
		} catch (Exception e1) {
			e1.printStackTrace();
			isStart = false;
			throw new Exception("启动服务器异常！");
		}
	}

	@SuppressWarnings("deprecation")
	public static void stop() throws Exception {
		try {
			if (serverThread != null) {
				serverThread.stop();
			}
			for (int i = clients.size() - 1; i >= 0; i--) {
				clients.get(i).stop();
				clients.get(i).socket.close();
				TouristsNumberView.removeClient(clients.get(i).getClient());
				clients.remove(i);
			}
			
			if (serverSocket != null) {
				serverSocket.close();
			}
			isStart = false;
		} catch (IOException e) {
			e.printStackTrace();
			isStart = true;
			throw new Exception(e.getMessage());
		}
	}
}
