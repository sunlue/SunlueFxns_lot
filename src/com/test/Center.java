package com.test;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Center {
	public static JFrame jf;

	public static JPanel init(JFrame jf) {
		Center.jf = jf;
		return init();
	}

	public static JPanel init() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		return panel;
	}
}
