package com.sunlue.gui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class East {
	public static JFrame jf;

	public static JPanel init(JFrame jf) {
		East.jf = jf;
		return init();
	}

	public static JPanel init() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(Frame.width / 2 - 5 / 2, Frame.height));
		JLabel label = new JLabel();
		label.setText("环境监测");
		label.setFont(new Font("楷体", 1, 20));
		panel.add(label);
		return panel;
	}
}
