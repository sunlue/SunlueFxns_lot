package com.view.datav.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.util.Util;
import com.view.datav.Cpanel;

/**
 * 实时车流量
 * 
 * @author xiebing
 *
 */
public class Traffic extends JPanel {

	private static final long serialVersionUID = 1L;

	public Traffic(int width, int height) {

		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(width, height));
		mainPanel.setOpaque(false);
		mainPanel.setLayout(new GridLayout(1, 2, 10, 0));
		mainPanel.add(leftPanel());
		mainPanel.add(rightPanel());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		Cpanel panel = new Cpanel("当日车流量", "Daily traffic flow", mainPanel);

		setOpaque(false);
		setPreferredSize(new Dimension(width, height));
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
	}

	private Component leftPanel() {
		JLabel icon = new JLabel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				ImageIcon icon = Util.getImageIcon("Traffic_visit.png");
				int width = icon.getIconWidth() * getHeight() / icon.getIconHeight();
				g.drawImage(icon.getImage(), (getWidth() - width) / 2, 0, width, getHeight(), icon.getImageObserver());
			}
		};

		JLabel label = new JLabel();
		label.setText("今日到访：" + Util.random(0, 2020));
		label.setForeground(Color.WHITE);
		label.setIconTextGap(10);
		label.setFont(new Font("微软雅黑", Font.BOLD, 16));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		label.setOpaque(false);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		panel.add(icon, BorderLayout.CENTER);
		panel.add(label, BorderLayout.SOUTH);

		return panel;
	}

	private Component rightPanel() {
		JLabel icon = new JLabel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				ImageIcon icon = Util.getImageIcon("Traffic_return.png");
				int width = icon.getIconWidth() * getHeight() / icon.getIconHeight();
				g.drawImage(icon.getImage(), (getWidth() - width) / 2, 0, width, getHeight(), icon.getImageObserver());
			}
		};

		JLabel label = new JLabel();
		label.setText("今日返程：" + Util.random(0, 2020));
		label.setForeground(Color.WHITE);
		label.setIconTextGap(10);
		label.setFont(new Font("微软雅黑", Font.BOLD, 16));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		label.setOpaque(false);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		panel.add(icon, BorderLayout.CENTER);
		panel.add(label, BorderLayout.SOUTH);

		return panel;
	}

}
