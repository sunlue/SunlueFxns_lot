package com.sunlue.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sunlue.gui.page.EnvMonitor;
import com.sunlue.util.Util;

public class Content {
	public static int x;
	public static int y;
	public static int tempX;
	public static int tempY;
	public static int winX;
	public static int winY;
	public static int oldX;
	public static int oldY;
	public static JFrame jf;
	public static JPanel contentPanel;
	public static int width = Frame.width - 200;

	public static JPanel init(JFrame jf) {
		Content.jf = jf;
		return init();
	}

	private static JPanel init() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(width, Frame.height));
		panel.setLayout(new BorderLayout());
		panel.add(header(), BorderLayout.NORTH);
		panel.add(content(), BorderLayout.CENTER);
		return panel;
	}

	private static JPanel content() {
		contentPanel = new JPanel();
		contentPanel.setBackground(Color.white);
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(new EnvMonitor().view(), BorderLayout.CENTER);
		return contentPanel;
	}

	private static Panel header() {
		ImageIcon closeIcon = new ImageIcon(Util.getResource("close.png"));
		closeIcon.setImage(closeIcon.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
		JButton closeBtn = new JButton(closeIcon);
		closeBtn.setContentAreaFilled(false);
		closeBtn.setBorderPainted(false);
		closeBtn.setBounds(width - 26, 10, 16, 16);
		closeBtn.setFocusPainted(false);
		closeBtn.setCursor(new Cursor(12));
		closeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		Panel header = new Panel();
		header.setSize(width, 40);
		header.setBackground(new Color(51, 51, 51));
		header.setCursor(new Cursor(12));
		header.setLayout(null);
		header.add(closeBtn);
		header.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
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
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		header.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
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
		return header;
	}

	public static void page(JPanel p) {
		contentPanel.add(p);
	}

}
