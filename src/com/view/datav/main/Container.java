package com.view.datav.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.jfree.data.general.DefaultPieDataset;

import com.util.CyFont;
import com.util.Util;
import com.view.charts.Charts;
import com.view.datav.DataV;

public class Container extends JPanel {

	private static final long serialVersionUID = 1L;

	public Container() {
		setBackground(new Color(7, 10, 85));
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(left(), BorderLayout.WEST);
//		add(center(), BorderLayout.CENTER);
//		add(right(), BorderLayout.EAST);
	}

	private JPanel left() {
		int width = 460;
		int height = ((int) DataV.screenSize.getHeight()) / 3;
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(460, getHeight()));
		panel.setOpaque(false);
		panel.add(new Env(width, height));
		return panel;
	}

	private JPanel center() {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		panel.add(new RealTimeTourists("2580"));
		DefaultPieDataset data = new DefaultPieDataset();
		data.setValue("key", 10);
		JPanel chartPanel = new Charts().title("MAP").pie(data, true, true, false).size(460, 460).handle();
//		panel.add(chartPanel);
		return panel;
	}

	private JPanel right() {

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(460, 200));
		panel.setOpaque(false);
		DefaultPieDataset data = new DefaultPieDataset();
		data.setValue("key", 10);
		JPanel chartPanel = new Charts().title("MAP").pie(data, true, true, false).size(460, 460).handle();
//		panel.add(chartPanel);

		return panel;
	}

	public void paintComponent(Graphics g) {
		int x = 0, y = 0;
		g.drawImage(Util.getImageIcon("bg.png"), x, y, getSize().width, getSize().height, this);// 图片会自动缩放
	}
}

class RealTimeTourists extends JPanel {
	private static final long serialVersionUID = 1L;

	public RealTimeTourists(String number) {
		String[] numArr = calculate(number);
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		for (int i = 0; i < numArr.length; i++) {
			JLabel label = new JLabel();
			label.setForeground(Color.white);
			label.setText(numArr[i]);
			if (!Character.isDigit(numArr[i].charAt(0))) {
				label.setPreferredSize(new Dimension(20, 80));
				label.setOpaque(false);
				label.setFont(CyFont.PuHuiTi(CyFont.Medium, 48));
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setVerticalAlignment(SwingConstants.BOTTOM);
			} else {
				label.setPreferredSize(new Dimension(66, 80));
				label.setOpaque(true);
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setVerticalAlignment(SwingConstants.CENTER);
				label.setFont(CyFont.PuHuiTi(CyFont.Medium, 66));
				label.setBackground(new Color(250, 250, 250, 20));
			}

			add(label);
		}
		JLabel unit = new JLabel("人");
		unit.setForeground(Color.white);
		unit.setFont(CyFont.PuHuiTi(CyFont.Medium, 14));
		unit.setHorizontalAlignment(SwingConstants.CENTER);
		unit.setVerticalAlignment(SwingConstants.BOTTOM);
		unit.setPreferredSize(new Dimension(20, 80));

		add(unit);
	}

	private String[] calculate(String num) {
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
