package com.action;

import java.net.Socket;

import com.server.Client;
import com.service.EnvMonitorService;
import com.service.TouristsNumberService;
import com.util.Util;
import com.view.hardware.page.EnvMonitorView;
import com.view.hardware.page.TouristsNumberView;

/**
 * 处理套接字数据
 * @author xiebing
 */
public class Action {
	static int EnvMonitorViewPort = Integer.valueOf(Util.getIni().get("env_monitor","port"));
	static int TouristsNumberPort = Integer.valueOf(Util.getIni().get("tourists_number","port"));
	/**
	 * 处理客户端数据
	 * 
	 * @param socket
	 * @param client
	 */
	public Action(Socket socket, Client client, byte[] b, int length) throws Exception {
		int port;
		if ((port = socket.getLocalPort()) > 0) {
			if (port == TouristsNumberPort) {
				new TouristsNumberService(client, b, length).handle();
			} else if (port == EnvMonitorViewPort) {
				new EnvMonitorService(client,b,length).handle();
			}
		}
	}

	/**
	 * 客户端上线了
	 * 
	 * @param socket
	 * @param client
	 */
	public static void online(Socket socket, Client client) {
		int port;
		if ((port = socket.getLocalPort()) > 0) {
			if (port == TouristsNumberPort) {
				TouristsNumberView.addClient(client);
			} else if (port == EnvMonitorViewPort) {
				EnvMonitorView.addClient(client);
			}
		}
	}

	/**
	 * 客户端下线了
	 * 
	 * @param socket
	 * @param client
	 */
	public static void offline(Socket socket, Client client) {
		int port;
		if ((port = socket.getLocalPort()) > 0) {
			if (port == TouristsNumberPort) {
				TouristsNumberView.removeClient(client);
			} else if (port == EnvMonitorViewPort) {
				EnvMonitorView.removeClient(client);
			}
		}
	}

}
