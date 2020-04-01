package com.sunlue;

import com.sunlue.socket.Server;

public class Index {
	public static void main(String[] args) {
		Sgui jFrame = new Sgui();
		jFrame.setVisible(true);
		Server s = new Server(8050);
		s.start();
	}
}
