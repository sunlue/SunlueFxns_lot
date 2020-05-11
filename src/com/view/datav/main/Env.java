package com.view.datav.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.util.Util;
import com.view.datav.Cpanel;

/**
 * @author xiebing
 */
public class Env extends JPanel {
	private static final long serialVersionUID = 1L;

	public Env(Dimension preferredSize) {
		int width = (int) (preferredSize.getWidth());
		int height = (int) (preferredSize.getHeight());
		handle(width, height, true);
	}

	public Env(int width, int height) {
		handle(width, height, true);
	}

	public Env(int width, int height, boolean tips) {
		handle(width, height, tips);
	}

	private void handle(int width, int height, boolean tips) {
		/** 实时环境监测 **/
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(width, height));
		mainPanel.setOpaque(false);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(leftPanel(), BorderLayout.WEST);
		mainPanel.add(rightPanel(), BorderLayout.CENTER);
		if (tips) {
			mainPanel.add(bottomPanel(), BorderLayout.SOUTH);
		}
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 16, 10));

		Cpanel panel = new Cpanel("实时环境监测", "environmental monitoring", mainPanel);

		setOpaque(false);
		setPreferredSize(new Dimension(width, height));
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
	}

	private Component bottomPanel() {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		panel.setLayout(new GridLayout(1, 1));
		JTextArea textArea = new JTextArea();
		textArea.setText("空气好，可以外出活动，除极少数对污染物特别敏感的人群以外，对公众没有危害！");
		textArea.setEditable(false);
		textArea.setFont(new Font("微软雅黑", Font.BOLD, 14));
		textArea.setForeground(Color.WHITE);
		textArea.setOpaque(false);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		panel.add(textArea);
		return panel;
	}

	private Component rightPanel() {
		JPanel rightPanel = new JPanel();
		rightPanel.setOpaque(false);
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
//			linePanel.setPreferredSize(new Dimension(width, 3));

			JPanel cellPan = new JPanel();
			cellPan.setOpaque(false);
			cellPan.setLayout(new BorderLayout());
			cellPan.add(txtLabel, BorderLayout.WEST);
			cellPan.add(numLabel, BorderLayout.EAST);
			cellPan.add(linePanel, BorderLayout.SOUTH);

			new ChangeNumEnv(numLabel).start();

			rightPanel.add(cellPan);
		}

		return rightPanel;
	}

	private Component leftPanel() {

		JPanel leftPanel = new JPanel();
		leftPanel.setOpaque(false);
		leftPanel.setPreferredSize(new Dimension(200, 0));
		leftPanel.setLayout(new GridLayout(2, 1));

		JLabel aqiNumLabel = new JLabel("96");
		aqiNumLabel.setForeground(new Color(7, 219, 255));
		aqiNumLabel.setFont(new Font("微软雅黑", Font.BOLD, 60));
		aqiNumLabel.setVerticalAlignment(SwingConstants.CENTER);
		aqiNumLabel.setHorizontalAlignment(SwingConstants.CENTER);

		new ChangeAqiEnv(aqiNumLabel).start();

		JLabel aqiTxtLabel = new JLabel("AQI 轻度污染");
		aqiTxtLabel.setForeground(Color.white);
		aqiTxtLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
		aqiTxtLabel.setVerticalAlignment(SwingConstants.CENTER);
		aqiTxtLabel.setHorizontalAlignment(SwingConstants.CENTER);
		JPanel topPanel = new JPanel();
		topPanel.setOpaque(false);
		topPanel.setLayout(new GridLayout(2, 1));
		topPanel.add(aqiNumLabel);
		topPanel.add(aqiTxtLabel);

		JLabel fengxiangLabel = new JLabel("北风1级");
		fengxiangLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
		fengxiangLabel.setForeground(Color.white);
		JLabel shiduLabel = new JLabel("湿度67%");
		shiduLabel.setForeground(Color.white);
		shiduLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
		JLabel qiyaLabel = new JLabel("气压972hpa");
		qiyaLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
		qiyaLabel.setForeground(Color.white);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(3, 1));
		bottomPanel.setOpaque(false);
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
		bottomPanel.add(fengxiangLabel);
		bottomPanel.add(shiduLabel);
		bottomPanel.add(qiyaLabel);

		leftPanel.add(topPanel, BorderLayout.NORTH);
		leftPanel.add(bottomPanel, BorderLayout.SOUTH);
		return leftPanel;
	}

}

class ChangeAqiEnv extends Thread {
	private JLabel aqiNumLabel;

	public ChangeAqiEnv(JLabel aqiNumLabel) {
		this.aqiNumLabel = aqiNumLabel;
	}

	@Override
	public void run() {
		new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aqiNumLabel.setText(Integer.toString(Util.random(80, 120)));
			}
		}).start();
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
