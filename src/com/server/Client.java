package com.server;

import java.net.Socket;
/**
 * 套接字客户端管理类
 * @author xiebing
 */
public class Client {
	private String name;
	private String ip;
	private int port;

	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
		this.name = ip + "@" + Integer.toString(port);
	}

	public Client(Socket socket) {
		this.ip = socket.getInetAddress().getHostAddress();
		this.port = socket.getPort();
		this.name = this.ip + "@" + String.valueOf(this.port);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getName() {
		return this.name;
	}
}
