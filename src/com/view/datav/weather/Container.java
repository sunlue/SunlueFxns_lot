package com.view.datav.weather;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.util.Util;
import com.view.datav.Cpanel;

public class Container extends JPanel {

	private static final long serialVersionUID = 1L;

	public Container() {
		setBackground(new Color(7, 10, 85));
		setLayout(new GridLayout(2, 2, 5, 5));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add(tLeftPanel(400, 400));
		add(tRightPanel());
		add(weaterLeftPanel());
		add(weaterRightPanel());
	}

	private JPanel tLeftPanel(int width, int height) {

		JPanel rightPanel = new JPanel();
		rightPanel.setOpaque(false);
		rightPanel.setPreferredSize(new Dimension(808 / 3 * 2, height));
		rightPanel.setLayout(new GridLayout(4, 1));

		ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		Map<String, Object> pm25 = new HashMap<String, Object>(5);
		pm25.put("txt", "<html><body><p>PM2.5</p><p>细颗粒</p></body></html>");
		pm25.put("txtColor", new Color(255, 255, 255));
		pm25.put("num", "114");
		pm25.put("numColor", new Color(7, 219, 255));
		pm25.put("bottomColor", new Color(255, 189, 0));
		data.add(pm25);

		Map<String, Object> pm10 = new HashMap<String, Object>(5);
		pm10.put("txt", "<html><body><p>PM10</p><p>可吸入颗粒</p></body></html>");
		pm10.put("txtColor", new Color(255, 255, 255));
		pm10.put("num", "0");
		pm10.put("numColor", new Color(7, 219, 255));
		pm10.put("bottomColor", new Color(255, 237, 47));
		data.add(pm10);

		Map<String, Object> No2 = new HashMap<String, Object>(5);
		No2.put("txt", "<html><body><p>NO2</p><p>二氧化硫</p></body></html>");
		No2.put("txtColor", new Color(255, 255, 255));
		No2.put("num", "58");
		No2.put("numColor", new Color(7, 219, 255));
		No2.put("bottomColor", new Color(43, 238, 155));
		data.add(No2);

		Map<String, Object> So2 = new HashMap<String, Object>(5);
		So2.put("txt", "<html><body><p>SO2</p><p>二氧化碳</p></body></html>");
		So2.put("txtColor", new Color(255, 255, 255));
		So2.put("num", "20");
		So2.put("numColor", new Color(7, 219, 255));
		So2.put("bottomColor", new Color(121, 1, 204));
		data.add(So2);

		for (int i = 0; i < data.size(); i++) {
			Map<String, Object> item = data.get(i);
			JLabel txtLabel = new JLabel(item.get("txt").toString());
			txtLabel.setForeground(Color.white);
			txtLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
			JLabel numLabel = new JLabel(item.get("num").toString());
			numLabel.setForeground((Color) item.get("numColor"));
			numLabel.setFont(new Font("微软雅黑", Font.BOLD, 36));
			JPanel linePanel = new JPanel();
			linePanel.setBackground((Color) item.get("bottomColor"));
			linePanel.setPreferredSize(new Dimension(width, 3));

			JPanel cellPan = new JPanel();
			cellPan.setOpaque(false);
			cellPan.setLayout(new BorderLayout());
			cellPan.add(txtLabel, BorderLayout.WEST);
			cellPan.add(numLabel, BorderLayout.EAST);
			cellPan.add(linePanel, BorderLayout.SOUTH);

			new ChangeNumEnv(numLabel).start();

			rightPanel.add(cellPan);
		}

		JPanel leftPanel = new JPanel();
		leftPanel.setOpaque(false);
		leftPanel.setPreferredSize(new Dimension(808 / 3, 400));

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		panel.add(new Cpanel(leftPanel), BorderLayout.WEST);
		panel.add(new Cpanel(rightPanel), BorderLayout.EAST);
		return panel;
	}

	private JPanel tRightPanel() {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.add(new JLabel("tRightPanel"));
		return new Cpanel(panel);
	}

	private JPanel weaterLeftPanel() {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.add(new JLabel("weaterLeftPanel"));
		return new Cpanel(panel);
	}

	private JPanel weaterRightPanel() {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.add(new JLabel("weaterLeftPanel"));
		return new Cpanel(panel);
	}
}

class ChangeNumEnv extends Thread {
	private JLabel numLabel;

	public ChangeNumEnv(JLabel numLabel) {
		this.numLabel = numLabel;
	}

	@Override
	public void run() {
		new Timer(3000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numLabel.setText(Integer.toString(Util.random(80, 120)));
			}
		}).start();
	}
}
