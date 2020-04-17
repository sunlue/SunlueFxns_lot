package com.view.hardware;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.util.CyFont;
import com.util.Util;
import com.view.Frame;
import com.view.Main;
import com.view.hardware.page.EnvMonitorView;
import com.view.hardware.page.TouristsNumberView;

public class Hardware extends JPanel {

	private static final long serialVersionUID = 1L;

	public static int width = Frame.width - com.view.Module.width;
	public static int height = Frame.height - Main.headerHeight;
	public static ArrayList<JButton> btnlist = new ArrayList<JButton>();
	public static JPanel container;
	public static String defaultView = Util.getIni().get("view", "default");

	public Hardware() {
		setPreferredSize(new Dimension(width, height));
		setLayout(new BorderLayout());
		add(MenuList(), BorderLayout.NORTH);
		switch (defaultView) {
			case "TouristsNumber":
				add(new TouristsNumberView(), BorderLayout.CENTER);
			break;
			case "EnvMonitor":
				add(new EnvMonitorView(), BorderLayout.CENTER);
				break;
			default:
				break;
		}
	}

	private JPanel MenuList() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(width, 60));
		panel.setBackground(new Color(245, 245, 245));
		panel.setLayout(null);
		panel.add(MenuItem("tourists.png", "客流设备", "TouristsNumber"));
		panel.add(MenuItem("env.png", "环境监测", "EnvMonitor"));
		panel.add(MenuItem("alarm.png", "报警设备", "Alarm"));
		return panel;
	}

	private JButton MenuItem(String img, String title, String name) {
		ImageIcon icon = new ImageIcon(Util.getResource(img));
		icon.setImage(icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		JButton button = new JButton(title, icon);
		button.setCursor(new Cursor(12));
		button.setForeground(new Color(51, 51, 51));
		button.setFont(CyFont.PuHuiTi(CyFont.Bold, 12));
		button.setBorder(null);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setVerticalAlignment(SwingConstants.CENTER);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setIconTextGap(0);
		button.setToolTipText(title);
		button.setBackground(null);
		button.setBounds(btnlist.size() * 84, 0, width / 10, 60);
		if (defaultView.equals(name)) {
			button.setContentAreaFilled(true);
			button.setBackground(new Color(220, 220, 220));
		} else {
			button.setContentAreaFilled(false);
		}
		btnlist.add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < btnlist.size(); i++) {
					if (btnlist.get(i) == button) {
						button.setContentAreaFilled(true);
						button.setBackground(new Color(220, 220, 220));
						cutPage(name);
					} else {
						btnlist.get(i).setBackground(new Color(245, 245, 245));
					}
				}
			}
		});
		return button;
	}

	protected void cutPage(String name) {
		if (name != null) {
			Component view = null;
			switch (name) {
			case "TouristsNumber":
				view = new TouristsNumberView();
				break;
			case "EnvMonitor":
				view = new EnvMonitorView();
				break;
			default:
				break;
			}
			remove(1);
			add(view);
			validate();
		}
	}
}
