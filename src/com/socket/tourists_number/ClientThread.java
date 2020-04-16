package com.socket.tourists_number;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.action.TouristsNumberAction;
import com.view.page.TouristsNumberView;

public class ClientThread extends Thread {
	public Socket socket;
	private static Client client;
	private byte b[] = new byte[1024];

	public ClientThread(Socket socket) {
		this.socket = socket;
		client = new Client(socket.getInetAddress().getHostAddress(), socket.getPort());
	}

	public void run() {
		while (true) {
			if (Thread.currentThread().isInterrupted()) {
				break;
			}
			try {
				// 接收client数据
				InputStream is = socket.getInputStream();
				int length = is.read(b);
				if (length > 0) {
					// 协议解析
					TouristsNumberAction.DataParse(this.getClient().getName(),b, length);
				} else if (length < 0) {
					// client断开了
					try {
						socket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					} finally {
						killThread();
					}
				} else {
					continue;
				}
			} catch (IOException e) {
				e.printStackTrace();
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					killThread();
				}
			}
		}
	}

	public Client getClient() {
		return client;
	}

	private void killThread() {
		ArrayList<ClientThread> clients = ServerThread.clients;
		for (int i = clients.size() - 1; i >= 0; i--) {
			if (clients.get(i).getClient() == client) {
				ClientThread temp = clients.get(i);
				clients.remove(i);
				temp.interrupt();
				TouristsNumberView.removeClient(client);
				return;
			}
		}
	}

}
