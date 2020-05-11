package com.view.datav.weather;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ThermometerPlot;
import org.jfree.data.general.DefaultValueDataset;

import com.util.Util;
import com.view.charts.Echarts;
import com.view.datav.Cpanel;
import com.view.datav.main.Env;

/**
 * 环境天气
 * 
 * @author xiebing
 *
 */
public class Container extends JPanel {

	private static final long serialVersionUID = 1L;

	public Container() {
		int width = Toolkit.getDefaultToolkit().getScreenSize().width - 95;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height - 75;
		setBackground(new Color(7, 10, 85));
		setLayout(new GridLayout(2, 2, 5, 5));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add(tLeftPanel(width / 2, height / 2));
		add(tRightPanel());
		add(weaterLeftPanel());
		add(weaterRightPanel());
	}

	private JPanel tLeftPanel(int width, int height) {

		DefaultValueDataset defaultvaluedataset = new DefaultValueDataset(41.5D);

		ThermometerPlot thermometerplot = new ThermometerPlot(defaultvaluedataset);
		thermometerplot.setNoDataMessage("没有可用的数据");
		thermometerplot.setThermometerStroke(new BasicStroke(2.0F));
		thermometerplot.setThermometerPaint(Color.lightGray);
		thermometerplot.setColumnRadius(10);
		thermometerplot.setBulbRadius(25);
		thermometerplot.setRange(-20.D, 50.0D);
		thermometerplot.setValuePaint(Color.RED);
		thermometerplot.setUseSubrangePaint(true);
		thermometerplot.setUnits(ThermometerPlot.UNITS_CELCIUS);
		thermometerplot.setSubrange(1, 1, 5);
		thermometerplot.setSubrange(35, 1, 5);
		JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, thermometerplot, false);
		chart.setBackgroundPaint(null);

		JPanel leftMainPanel = new JPanel();
		leftMainPanel.setOpaque(false);
		Cpanel leftWenduPanel = new Cpanel("当前温度", "Current temperature (℃)", leftMainPanel);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(width, height));
		chartPanel.setOpaque(false);

//		leftWenduPanel.add(chartPanel);

		leftWenduPanel.setBounds(0, 0, width / 24 * 10, height / 2 - 5);

		Cpanel leftShiduPanel = new Cpanel("当前湿度", "Current humidity (%)", leftMainPanel);
		leftShiduPanel.setBounds(0, height / 2, width / 24 * 10, height / 2);
		leftShiduPanel.setLayout(null);
		JPanel WenduChat = new Echarts().size(height / 2, height / 2).gauge().handle();
		WenduChat.setBounds((leftShiduPanel.getWidth() - height / 2) / 2, 0, height / 2, height / 2);
		leftShiduPanel.add(WenduChat);

		JPanel rightPanel = new Env(width / 24 * 14, height);
		rightPanel.setPreferredSize(new Dimension(width / 24 * 14 - 5, height));
		rightPanel.setBounds(width / 24 * 10 + 5, 0, width / 24 * 14 - 5, height);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(null);
		panel.add(leftWenduPanel);
		panel.add(leftShiduPanel);
		panel.add(rightPanel);
		return panel;
	}

	private JPanel tRightPanel() {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		return new Cpanel("未来7天天气预报", "Weather forecast for the next 7 days", panel);
	}

	private JPanel weaterLeftPanel() {
		final int hours = 24;
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new GridLayout(4, 6, 5, 5));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		for (int i = 0; i < hours; i++) {
			JLabel itemLabel = new JLabel("<html><body><p>08时 小雨 20℃</p><p>无持续风向 <3级</p></body></html>");
			itemLabel.setIcon(Util.getImageIcon("weather_yu.png", 48, 48));
			itemLabel.setOpaque(true);
			itemLabel.setBorder(BorderFactory.createLineBorder(new Color(7, 219, 255), 1, true));
			itemLabel.setForeground(new Color(0, 213, 255));
			itemLabel.setBackground(new Color(23, 46, 107));
			itemLabel.setHorizontalAlignment(SwingConstants.CENTER);
			itemLabel.setVerticalAlignment(SwingConstants.CENTER);
			itemLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			itemLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
			panel.add(itemLabel);
		}
		return new Cpanel("24小时天气", "24-hour weather", panel);
	}

	private JPanel weaterRightPanel() {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.add(new JLabel("weaterLeftPanel"));
		return new Cpanel("当日24小时温度", "24 hour temperature", panel);
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
