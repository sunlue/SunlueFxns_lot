package com.socket.tourists_number;

import java.net.Socket;

import com.action.TouristsNumberAction;
import com.view.hardware.page.EnvMonitorView;
import com.view.hardware.page.TouristsNumberView;

public class Action {

	public Action(Socket socket, Client client, byte[] b, int length) throws Exception {
		int EnvMonitorViewPort = Integer.valueOf(EnvMonitorView.portNum.getText());
		int TouristsNumberPort = Integer.valueOf(TouristsNumberView.portNum.getText());
		int port;
		if ((port = socket.getLocalPort()) > 0) {
			if (port == TouristsNumberPort) {
				TouristsNumberAction.DataParse(client.getName(), b, length);
			} else if (port == EnvMonitorViewPort) {

			}
		}
	}

	public static void offline(Socket socket, Client client) {
		int EnvMonitorViewPort = Integer.valueOf(EnvMonitorView.portNum.getText());
		int TouristsNumberPort = Integer.valueOf(TouristsNumberView.portNum.getText());
		int port;
		if ((port = socket.getLocalPort()) > 0) {
			if (port == TouristsNumberPort) {
				TouristsNumberView.removeClient(client);
			} else if (port == EnvMonitorViewPort) {

			}
		}
	}

}
