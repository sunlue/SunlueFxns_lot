package com.sunlue.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sunlue.gui.page.EnvMonitor;
import com.sunlue.gui.page.TouristrFlow;
import com.sunlue.util.Util;

public class Menu {
	public static JFrame jf;
	public static int width = 200;

	public static JPanel init(JFrame jf) {
		Header.jf = jf;
		return init();
	}

	private static JPanel init() {
		ImageIcon logo = new ImageIcon(Util.getResource("sunlue_logo.png"));
		logo.setImage(logo.getImage().getScaledInstance(167, 35, Image.SCALE_DEFAULT));
		JButton logoBtn = new JButton(logo);
		logoBtn.setContentAreaFilled(false);
		logoBtn.setBorderPainted(false);
		logoBtn.setBounds(10, 10, 167, 35);
		logoBtn.setFocusPainted(false);
		logoBtn.setCursor(new Cursor(12));

		JButton btn1 = new JButton("环境监测");
		btn1.setBorderPainted(false);
		btn1.setBackground(Color.white);
		btn1.setForeground(new Color(230, 61, 45));
		btn1.setPreferredSize(new Dimension(width,40));
		btn1.setFont(new Font("宋体", 4, 14));
		btn1.setFocusPainted(false);
		btn1.setCursor(new Cursor(12));
		btn1.setHorizontalAlignment(JButton.LEFT);
		btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel p = new EnvMonitor().view();
				Content.page(p);
				System.out.println("点击了环境监测");
			}
		});
		
		JButton btn2 = new JButton("客流监测");
		btn2.setBorderPainted(false);
		btn2.setBackground(new Color(51, 51, 51));
		btn2.setForeground(Color.white);
		btn2.setPreferredSize(new Dimension(width,40));
		btn2.setFont(new Font("宋体", 4, 14));
		btn2.setFocusPainted(false);
		btn2.setCursor(new Cursor(12));
		btn2.setHorizontalAlignment(JButton.LEFT);
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel p = new TouristrFlow().view();
				Content.page(p);
				System.out.println("点击了客流监测");
			}
		});

		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 51, 51));
		panel.setPreferredSize(new Dimension(width, Frame.height));

		panel.add(logoBtn);
		panel.add(btn1);
		panel.add(btn2);
		return panel;
	}
}
