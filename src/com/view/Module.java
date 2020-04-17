package com.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.util.Util;

public class Module {
	public static int width = 60;
	public static ArrayList<JButton> buttonItem = new ArrayList<JButton>();

	public static JPanel init() {
		ImageIcon logo = Util.getImageIcon("logo_32_32.png", 32, 32);
		JButton logoBtn = new JButton(logo);
		logoBtn.setContentAreaFilled(false);
		logoBtn.setBorderPainted(false);
		logoBtn.setBounds(14, 10, 32, 32);
		logoBtn.setFocusPainted(false);
		logoBtn.setCursor(new Cursor(12));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(39, 41, 45));
		panel.setPreferredSize(new Dimension(width, Frame.height));
		panel.add(logoBtn);
		panel.add(button("硬件", "hardware.png"));
		return panel;
	}

	protected static JButton button(String name, String icon) {

		ImageIcon ImgIcon = Util.getImageIcon(icon, 32, 32);
		ImageIcon _ImgIcon = Util.getImageIcon("_" + icon, 32, 32);

		JButton button = new JButton(ImgIcon);
		button.setBorderPainted(false);
		button.setPreferredSize(new Dimension(width, width));
		button.setFocusPainted(false);
		button.setCursor(new Cursor(12));
		button.setPressedIcon(_ImgIcon);
		button.setContentAreaFilled(false);
		button.setToolTipText(name);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Container("hardware");
			}
		});

		buttonItem.add(button);

		return button;
	}
}
