package com.sunlue.tourists_flow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerThread extends Thread {
	private ServerSocket serverSocket;
	private ArrayList<ClientThread> clients;
	private int max;

	// 服务器线程构造函数
	public ServerThread(ServerSocket serverSocket, int max) {
		this.serverSocket = serverSocket;
		this.max = max;
	}

	public void run() {
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				if (clients.size() == max) {
					socket.close();
					continue;
				}

				ClientThread client = new ClientThread(socket);
				client.start();
				clients.add(client);
				//allListModel.addElement(client.getClient().getName());// 更新在线列表
				//contentArea.insert(client.getClient().getName() + "上线!\r\n",0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
