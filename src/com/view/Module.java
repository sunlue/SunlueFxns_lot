package com.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.util.Util;
import com.view.monitor.Monitor;
/**
 * @author xiebing
 */
public class Module {
	public static int width = 60;

	public static JPanel init() {
		ImageIcon logo = Util.getImageIcon("logo_32_32.png", 32, 32);
		JButton logoBtn = new JButton(logo);
		logoBtn.setContentAreaFilled(false);
		logoBtn.setBorderPainted(false);
		logoBtn.setBounds(14, 10, 32, 32);
		logoBtn.setFocusPainted(false);
		logoBtn.setCursor(new Cursor(12));

		JPanel footerPanel = new JPanel();
		footerPanel.setPreferredSize(new Dimension(width, 100));
		footerPanel.setOpaque(false);
		footerPanel.add(setting("设置", "setting.png"));

		JPanel menuPanel = new JPanel();
		int footerHeight = (int) footerPanel.getPreferredSize().getHeight();
		menuPanel.setPreferredSize(new Dimension(width, Frame.height - logoBtn.getHeight() - footerHeight));
		menuPanel.setOpaque(false);
		menuPanel.add(button("硬件", "hardware.png", "cut", "hardware"));
		menuPanel.add(button("视频监控", "video_monitor.png", "open", "video_monitor"));

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(width, Frame.height));
		panel.setBackground(new Color(39, 41, 45));
		panel.add(logoBtn);
		panel.add(menuPanel);
		panel.add(footerPanel);

		return panel;
	}

	protected static JButton button(String name, String icon, String handle, String page) {

		ImageIcon imgIcon = Util.getImageIcon(icon, 32, 32);
		ImageIcon sImgIcon = Util.getImageIcon("_" + icon, 32, 32);

		JButton button = new JButton(imgIcon);
		button.setBorderPainted(false);
		button.setPreferredSize(new Dimension(width, width));
		button.setFocusPainted(false);
		button.setCursor(new Cursor(12));
		button.setPressedIcon(sImgIcon);
		button.setContentAreaFilled(false);
		button.setToolTipText(name);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (handle == "cut") {
					Main main = new Main();
					main.cutPage(page);
				} else if (handle == "open") {
					if (page != null) {
						switch (page) {
						case "video_monitor":
							new Monitor();
							break;
						default:
							break;
						}
						Frame.jf.setExtendedState(JFrame.ICONIFIED);
					}
				}
			}
		});

		return button;
	}

	protected static JButton setting(String name, String icon) {
		ImageIcon imgIcon = Util.getImageIcon(icon, 32, 32);
		JButton button = new JButton(imgIcon);
		button.setBorderPainted(false);
		button.setPreferredSize(new Dimension(width, width));
		button.setFocusPainted(false);
		button.setCursor(new Cursor(12));
		button.setContentAreaFilled(false);
		button.setToolTipText(name);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main main = new Main();
				main.cutPage("setting");
			}
		});

		return button;
	}
}
