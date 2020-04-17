package com.socket.tourists_number;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.util.Log;

public class ClientThread extends Thread {
	public Socket socket;
	private static Client client;
	private byte b[] = new byte[1024];

	public ClientThread(Socket socket) {
		this.socket = socket;
		client = new Client(socket);
	}

	public void run() {

		System.out.println(isInterrupted());
		while (!isInterrupted()) {
			try {
				// 接收client数据
				InputStream is = socket.getInputStream();
				int length = is.read(b);
				System.out.println(socket);
				if (length > 0) {
					try {
						new Action(socket, this.getClient(), b, length);
					} catch (Exception e) {
						Log.write(e.getMessage(),Log.Error);
					}
				} else if (length < 0) {
					// client断开了
					try {
						socket.close();
					} catch (IOException e1) {
						Log.write(e1.getMessage(),Log.Error);
						e1.printStackTrace();
					} finally {
						killThread(socket);
					}
				} else {
					continue;
				}
			} catch (IOException e) {
				e.printStackTrace();
				Log.write(e.getMessage(),Log.Error);
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					Log.write(e1.getMessage(),Log.Error);
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
		ArrayList<ClientThread> clients = ServerThread.clients;
		System.out.println(client);
		for (int i = clients.size() - 1; i >= 0; i--) {
			if (clients.get(i).getClient() == client) {
//				ClientThread temp = clients.get(i);
//				clients.remove(i);
//				temp.interrupt();
				Action.offline(socket,client);
				return;
			}
		}
	}

}




