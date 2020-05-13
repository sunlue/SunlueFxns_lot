package com.view.datav.parking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.util.Util;
import com.view.datav.main.Map;
import com.view.datav.main.TouristsFrom;

public class Container extends JPanel {
	private static final long serialVersionUID = 1L;

	int leftWidth = 460;
	int rightWidth = 460;
	int centerWidth = Toolkit.getDefaultToolkit().getScreenSize().width - rightWidth - leftWidth - 100;
	int height = Toolkit.getDefaultToolkit().getScreenSize().height - 70;

	public Container() {
		setBackground(new Color(7, 10, 85));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(5, 5));
		add(left(leftWidth, height), BorderLayout.WEST);
		add(center(centerWidth, height), BorderLayout.CENTER);
		add(right(rightWidth, height), BorderLayout.EAST);
	}

	private JPanel left(int width, int height) {

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
		panel.setPreferredSize(new Dimension(width, height));
		panel.setBackground(Color.RED);
		panel.add(new TotalVehicleFlow(width, 96));
		panel.add(new Parking(width, 210, "停车场使用情况", "Car park usage"));
		panel.add(new RegionVehicle(width, height - 454));

		return panel;
	}

	private JPanel center(int width, int height) {
		JPanel flop = new RealTimeCar(Integer.toString(Util.random(10000, 99999))).handle();
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout(0, 5));
		panel.add(flop, BorderLayout.NORTH);
		panel.add(new Map(width, height - 420), BorderLayout.CENTER);
		panel.add(new TouristsFrom(width, 420), BorderLayout.SOUTH);
		new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String number = Integer.toString(Util.random(10000, 99999));
				new ChangeTourists(flop, number).start();
			}
		}).start();
		return panel;
	}

	private JPanel right(int width, int height) {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(width, height));
		panel.setOpaque(false);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
		panel.add(new Traffic(width, 220));
		panel.add(new Parking(width, 210));
		panel.add(new Monitor(width, 220, "视频监控", "Daily traffic flow"));
		return panel;
	}
}
