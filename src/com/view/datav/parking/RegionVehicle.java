package com.view.datav.parking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
 * 地区实时车辆数
 * 
 * @author xiebing
 *
 */
public class RegionVehicle extends JPanel {

	private static final long serialVersionUID = 1L;

	public RegionVehicle(int width, int height) {
		this(width, height, "地区实时车辆数", "District real-time vehicle count");
	}

	/**
	 * 地区实时车辆数
	 * 
	 * @param width
	 * @param height
	 * @param tit
	 * @param subTit
	 */
	public RegionVehicle(int width, int height, String tit, String subTit) {
		JPanel mainPanel = new JPanel();
		mainPanel.setOpaque(false);
		mainPanel.setLayout(new GridLayout(6, 6, 5, 5));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		for (int i = 0; i < 34; i++) {
			mainPanel.add(item());
		}
		Cpanel panel = new Cpanel(tit, subTit, mainPanel);
		setOpaque(false);
		setPreferredSize(new Dimension(width, height + 64));
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
	}

	private Component item() {
		JPanel itemPanel = new JPanel();
		itemPanel.setBackground(new Color(23, 46, 107));
		itemPanel.setBorder(BorderFactory.createLineBorder(new Color(50, 100, 254), 1, true));
		itemPanel.setLayout(new GridLayout(2, 1, 5, 0));

		JLabel title = new JLabel("四川");
		title.setForeground(new Color(0, 213, 255));
		title.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setVerticalAlignment(SwingConstants.BOTTOM);
		JLabel number = new JLabel(String.valueOf(Util.random(10, 80)));
		number.setForeground(new Color(0, 213, 255));
		number.setFont(new Font("微软雅黑", Font.BOLD, 16));
		number.setHorizontalAlignment(SwingConstants.CENTER);
		number.setVerticalAlignment(SwingConstants.TOP);

		itemPanel.add(title);
		itemPanel.add(number);

		return itemPanel;
	}

}
