package com.sunlue.gui;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sunlue.util.Util;

public class Header {
	public static int width = Frame.width;
	public static int x;
	public static int y;
	public static int tempX;
	public static int tempY;
	public static int winX;
	public static int winY;
	public static int oldX;
	public static int oldY;
	public static JFrame jf;

	public static JPanel init(JFrame jf) {
		Header.jf = jf;
		return init();
	}

	public static JPanel init() {
		ImageIcon backgroundImage = new ImageIcon(Util.getResource("loginBg.jpg"));
		backgroundImage.setImage(backgroundImage.getImage().getScaledInstance(width, 60, Image.SCALE_DEFAULT));
		JLabel background = new JLabel(backgroundImage);
		background.setBounds(0, 0, width, 60);

		ImageIcon logo = new ImageIcon(Util.getResource("sunlue_logo.png"));
		logo.setImage(logo.getImage().getScaledInstance(167, 35, Image.SCALE_DEFAULT));
		JButton logoBtn = new JButton(logo);
		logoBtn.setContentAreaFilled(false);
		logoBtn.setBorderPainted(false);
		logoBtn.setBounds(10, 10, 167, 35);
		logoBtn.setFocusPainted(false);
		logoBtn.setCursor(new Cursor(12));

		ImageIcon closeIcon = new ImageIcon(Util.getResource("close.png"));
		closeIcon.setImage(closeIcon.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
		JButton closeBtn = new JButton(closeIcon);
		closeBtn.setContentAreaFilled(false);
		closeBtn.setBorderPainted(false);
		closeBtn.setBounds(width - 26, 10, 16, 16);
		closeBtn.setFocusPainted(false);
		closeBtn.setCursor(new Cursor(12));

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(width, 60));
		panel.add(closeBtn);
		panel.add(logoBtn);
		panel.add(background);

		panel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				tempX = (int) point.getX();
				tempY = (int) point.getY();
				oldX = (int) point.getX();
				oldY = (int) point.getY();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		panel.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDragged(MouseEvent e) {

				Point point = e.getPoint();
				Rectangle rec = jf.getBounds();

				winX = (int) rec.getX();
				winY = (int) rec.getY();
				x = (int) point.getX();
				y = (int) point.getY();
				tempX = x - oldX;
				tempY = y - oldY;

				jf.setLocation((int) (winX + tempX), (int) (winY + tempY));

			}
		});

		logoBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.browse(URI.create("http://www.sunlue.com"));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		closeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		return panel;
	}
}
