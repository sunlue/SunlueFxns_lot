package com.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.action.Action;
import com.util.Log;

public class ClientThread extends Thread {
	public Socket socket;
	private Client client;
	private byte b[] = new byte[1024];

	public ClientThread(Socket socket) {
		this.socket = socket;
		client = new Client(socket);
	}

	public void run() {
		while (!isInterrupted()) {
			try {
				// 接收client数据
				InputStream is = socket.getInputStream();
				int length = is.read(b);
				if (length > 0) {
					try {
						new Action(socket, this.getClient(), b, length);
					} catch (Exception e) {
						Log.write(e.getMessage(), Log.Error);
					}
				} else if (length < 0) {
					// client断开了
					try {
						socket.close();
					} catch (IOException e1) {
						Log.write(e1.getMessage(), Log.Error);
						interrupt();
					}
					killThread(socket);
				} else {
					continue;
				}
			} catch (IOException e) {
				Log.write(e.getMessage(), Log.Error);
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					Log.write(e1.getMessage(), Log.Error);
				} finally {
					killThread(socket);
				}
			}
		}
	}

	public Client getClient() {
		return client;
	}

	private void killThread(Socket socket) {
		Action.offline(socket, client);
		ArrayList<ClientThread> clients = Server.clients;
		for (int i = clients.size() - 1; i >= 0; i--) {
			Client killClient = clients.get(i).getClient();
			if (killClient == client) {
				clients.get(i).interrupt();
				clients.remove(i);
				return;
			}
		}
	}

}
