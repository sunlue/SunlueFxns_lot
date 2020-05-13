package com.view.datav.parking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.util.Util;
import com.view.datav.Cpanel;

/**
 * 累计车流量
 * 
 * @author xiebing
 *
 */
public class TotalVehicleFlow extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * 累计车流量
	 * 
	 * @param width
	 */
	public TotalVehicleFlow(int width) {
		this(width, 96, "累计车流量", "total vehicle flow");
	}

	public TotalVehicleFlow(int width, int height) {
		this(width, height, "累计车流量", "total vehicle flow");
	}

	public TotalVehicleFlow(int width, int height, String tit, String subTit) {
		JPanel mainPanel = new JPanel();
		mainPanel.setOpaque(false);
		mainPanel.setLayout(new GridLayout(1, 3, 5, 5));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		for (int i = 0; i < 3; i++) {
			mainPanel.add(item());
		}
		Cpanel panel = new Cpanel(tit, subTit, mainPanel);
		setOpaque(false);
		setPreferredSize(new Dimension(width, height + 64));
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
	}

	private JPanel item() {

		JLabel title = new JLabel("今日累计");
		title.setFont(new Font("微软雅黑", Font.BOLD, 14));
		title.setForeground(Color.WHITE);
		title.setVerticalAlignment(SwingConstants.BOTTOM);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

		JPanel strong = new JPanel();
		strong.setOpaque(false);

		JLabel number = new JLabel(String.valueOf(Util.random(10, 90)));
		number.setFont(new Font("微软雅黑", Font.BOLD, 20));
		number.setForeground(new Color(149, 234, 255));
		number.setVerticalAlignment(SwingConstants.TOP);
		number.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel unit = new JLabel("辆");
		unit.setForeground(Color.WHITE);
		unit.setFont(new Font("微软雅黑", Font.BOLD, 14));
		unit.setVerticalAlignment(SwingConstants.BOTTOM);

		strong.add(number);
		strong.add(unit);

		JPanel itemPanel = new JPanel();
		itemPanel.setBackground(new Color(250, 250, 250, 20));
		itemPanel.setLayout(new GridLayout(2, 1));
		itemPanel.setPreferredSize(new Dimension(0, 96));
		itemPanel.add(title);
		itemPanel.add(strong);
		return itemPanel;
	}
}
