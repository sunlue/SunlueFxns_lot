package com.sunlue.tourists_flow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerThread extends Thread {
	private ServerSocket serverSocket;
	private ArrayList<ClientThread> clients;
	private int max;

	// �������̹߳��캯��
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
				//allListModel.addElement(client.getClient().getName());// ���������б�
				//contentArea.insert(client.getClient().getName() + "����!\r\n",0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
