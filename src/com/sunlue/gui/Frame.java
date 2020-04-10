package com.sunlue.gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Frame {
	public static JFrame jf = new JFrame();

	public static int width = 1000;
	public static int height = 600;

	public Frame() {

		int x = ((Toolkit.getDefaultToolkit().getScreenSize().width) / 2) - width / 2;
		int y = ((Toolkit.getDefaultToolkit().getScreenSize().height) / 2) - height / 2;

		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setUndecorated(true);
		jf.setBounds(x, y, width, height);
		jf.setIconImage(new ImageIcon("img/logo.png").getImage());

//		jf.add(Header.init(jf), BorderLayout.NORTH);
//		jf.add(West.init(jf), BorderLayout.WEST);
//		jf.add(Center.init(),BorderLayout.CENTER);
//		jf.add(East.init(jf), BorderLayout.EAST);
		jf.add(Menu.init(jf),BorderLayout.WEST);
		jf.add(Content.init(jf),BorderLayout.CENTER);
		jf.setVisible(true);

	}
}
