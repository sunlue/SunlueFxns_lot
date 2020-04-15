package com.sunlue;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sunlue.page.EnvMonitor;
import com.sunlue.util.Util;

public class Module {
	public static JFrame jf;
	public static int width = 60;

	public static JPanel init(JFrame jf) {
		Module.jf = jf;
		return init();
	}

	private static JPanel init() {
		ImageIcon logo = new ImageIcon(Util.getResource("logo200_200.png"));
		logo.setImage(logo.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		JButton logoBtn = new JButton(logo);
		logoBtn.setContentAreaFilled(false);
		logoBtn.setBorderPainted(false);
		logoBtn.setBounds(15, 10, 30, 30);
		logoBtn.setFocusPainted(false);
		logoBtn.setCursor(new Cursor(12));
		
		ImageIcon envImgIcon=new ImageIcon(Util.getResource("env.png"));
		envImgIcon.setImage(envImgIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		ImageIcon _envImgIcon=new ImageIcon(Util.getResource("_env.png"));
		_envImgIcon.setImage(_envImgIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));

		JButton btn1 = new JButton(_envImgIcon);
		btn1.setBorderPainted(false);
		btn1.setBackground(new Color(51, 51, 51));
		btn1.setPreferredSize(new Dimension(width, 40));
		btn1.setFocusPainted(false);
		btn1.setCursor(new Cursor(12));
		btn1.setPressedIcon(_envImgIcon);
		btn1.setContentAreaFilled(false);

		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				JPanel p = new EnvMonitor().view();
//				Content.cutPage(btn1.getName(),p);
			}
		});

		JPanel panel = new JPanel();
		panel.setBackground(new Color(39, 41, 45));
		panel.setPreferredSize(new Dimension(width, Frame.height));

		panel.add(logoBtn);
		panel.add(btn1);
		return panel;
	}
}
