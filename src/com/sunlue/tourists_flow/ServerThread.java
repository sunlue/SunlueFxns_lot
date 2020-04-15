package com.sunlue.tourists_flow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.sunlue.page.TouristrFlow;

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
				if (serverSocket.isClosed() == false) {
					Socket socket = serverSocket.accept();
					clients = new ArrayList<ClientThread>();
					if (clients.size() == maxClient) {
						socket.close();
						continue;
					}
					ClientThread client = new ClientThread(socket);
					client.start();
					clients.add(client);
					TouristrFlow.addClient(client);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void close() throws IOException {
		if (clients.size() > 0) {
			for (int i = clients.size() - 1; i >= 0; i--) {
				clients.get(i).interrupt();
				clients.get(i).socket.close();
				clients.remove(i);
			}
		}
	}

}
