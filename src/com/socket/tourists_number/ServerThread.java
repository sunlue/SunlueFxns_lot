package com.socket.tourists_number;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.view.page.TouristsNumberView;

public class ServerThread extends Thread {
	private int maxClient;
	private ServerSocket serverSocket;
	public static ArrayList<ClientThread> clients;

	public ServerThread(ServerSocket serverSocket, int maxClient) {
		this.serverSocket = serverSocket;
		this.maxClient = maxClient;
	}

	public void run() {
		while (true) {
			if (Thread.currentThread().isInterrupted()) {
				break;
			}
			try {
				Socket socket = serverSocket.accept();
				clients = new ArrayList<ClientThread>();
				if (clients.size() == maxClient) {
					socket.close();
					continue;
				}
				ClientThread client = new ClientThread(socket);
				client.start();
				clients.add(client);
				Server.clients.add(client);
				TouristsNumberView.addClient(client);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
