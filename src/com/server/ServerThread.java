package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.action.Action;
/**
 * 套接字服务端线程池
 * @author xiebing
 */
public class ServerThread extends Thread {
	private int maxClient;
	private ServerSocket serverSocket;

	public ServerThread(ServerSocket serverSocket, int maxClient) {
		this.serverSocket = serverSocket;
		this.maxClient = maxClient;
	}
	@Override
	public void run() {
		while (!interrupted()) {
			try {
				Socket socket = serverSocket.accept();
				ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
				if (clients.size() == maxClient) {
					socket.close();
					continue;
				}
				ClientThread client = new ClientThread(socket);
				client.start();
				Server.clients.add(client);
				Action.online(socket, client.getClient());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
