package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
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
import com.view.page.TouristsNumberView;

public class Container extends JPanel {

	public static int width = Frame.width - Module.width;
	public static int height = Frame.height - Main.headerHeight;
	public static ArrayList<JButton> btnlist = new ArrayList<JButton>();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Container() {
		setPreferredSize(new Dimension(width, height));
		setLayout(new BorderLayout());
		add(MenuList(), BorderLayout.NORTH);
		add(new TouristsNumberView(), BorderLayout.CENTER);
	}

	private JPanel MenuList() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(width, 60));
		panel.setBackground(new Color(236, 233, 231));
		panel.setLayout(null);
		panel.add(MenuItem("env.png", "环境监测", 0, true));
		panel.add(MenuItem("alarm.png", "报警设备", 84, false));
		panel.add(MenuItem("tourists.png", "客流设备", 168, false));
		return panel;
	}

	private JButton MenuItem(String img, String title, int x, boolean active) {
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
		button.setBounds(x, 0, width / 10, 60);
		if (active) {
			button.setContentAreaFilled(true);
			button.setBackground(new Color(196, 196, 196));
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
						button.setBackground(new Color(196, 196, 196));
					} else {
						btnlist.get(i).setBackground(new Color(236, 233, 231));
					}
				}
			}
		});
		return button;
	}

}
