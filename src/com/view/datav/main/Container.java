package com.view.datav.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.util.CyFont;
import com.util.Util;
import com.view.datav.DataV;

/**
 * @author xiebing
 */
public class Container extends JPanel {

	private static final long serialVersionUID = 1L;

	public Container() {
		setBackground(new Color(7, 10, 85));
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add(left(), BorderLayout.WEST);
		add(center(), BorderLayout.CENTER);
		add(right(), BorderLayout.EAST);
	}

	private JPanel left() {
		int width = 460;
		int height = ((int) DataV.screenSize.getHeight()) - 70;
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(width, height));
		panel.setOpaque(false);
		panel.setLayout(new GridLayout(3, 1, 0, 5));
		panel.add(new Env(width, height / 3));
		panel.add(new FeedBack(width, height / 3));
		panel.add(new Access(width, height / 3));
		return panel;
	}

	private JPanel center() {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		panel.add(new RealTimeTourists("2580").handle());
		new Thread(new Runnable() {
			@Override
			public void run() {
				new Timer(3000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						RealTimeTourists.change(Integer.toString(Util.random(10000, 99999)));
					}
				}).start();
			}
		}).start();

		return panel;
	}

	private JPanel right() {
		int width = 460;
		int height = ((int) DataV.screenSize.getHeight()) - 70;
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(width, height));
		panel.setOpaque(false);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
		panel.add(new Traffic(width, 220));
		panel.add(new Parking(width, 220));
		panel.add(new Event(width, height - 680));
		panel.add(new Monitor(width, 220));
		return panel;
	}

	@Override
	public void paintComponent(Graphics g) {
		int x = 0, y = 0;
		g.drawImage(Util.getImage("bg.png"), x, y, getSize().width, getSize().height, this);
	}
}

class RealTimeTourists {
	private static String[] numArr;
	private static JPanel panel= new JPanel();

	public RealTimeTourists(String number) {
		numArr = calculate(number);
	}

	public Component handle() {
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		for (int i = 0; i < numArr.length; i++) {
			JLabel label = new JLabel();
			label.setForeground(Color.white);
			label.setText(numArr[i]);
			if (!Character.isDigit(numArr[i].charAt(0))) {
				label.setPreferredSize(new Dimension(20, 80));
				label.setOpaque(false);
				label.setFont(CyFont.puHuiTi(CyFont.Medium, 48));
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setVerticalAlignment(SwingConstants.BOTTOM);
			} else {
				label.setPreferredSize(new Dimension(66, 80));
				label.setOpaque(true);
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setVerticalAlignment(SwingConstants.CENTER);
				label.setFont(CyFont.puHuiTi(CyFont.Medium, 66));
				label.setBackground(new Color(250, 250, 250, 20));
			}

			panel.add(label);
		}
		JLabel unit = new JLabel("人");
		unit.setForeground(Color.white);
		unit.setFont(CyFont.puHuiTi(CyFont.Medium, 14));
		unit.setHorizontalAlignment(SwingConstants.CENTER);
		unit.setVerticalAlignment(SwingConstants.BOTTOM);
		unit.setPreferredSize(new Dimension(20, 80));
		panel.add(unit);
		return panel;
	}

	public static void change(String num) {
		numArr = calculate(num);
		for (int i = 0; i < numArr.length; i++) {
			JLabel label=(JLabel) panel.getComponent(i);
			label.setText(numArr[i]);
		}
	}

	private static String[] calculate(String num) {
		if (num.length() < 6) {
			int size = 6 - num.length();
			for (int i = 0; i < size; i++) {
				num = "0" + num;
			}
		}
		num = num.substring(0, 3) + "," + num.substring(3, 6);
		return num.split("");
	}

}
