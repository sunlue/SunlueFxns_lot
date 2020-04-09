package com.sunlue.tourists_flow;

public class Client {
	private String name;
	private String ip;
	private int port;
	
	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
		this.name = ip + "@" + String.valueOf(port);
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
