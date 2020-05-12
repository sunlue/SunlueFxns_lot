package com.view.datav.weather;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.util.Util;
import com.view.charts.Echarts.EchartType;
import com.view.charts.weather.Hours;
import com.view.charts.weather.Humidity;
import com.view.charts.weather.Temperature;
import com.view.charts.weather.Weather;
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
		add(tRightPanel(width / 2, height / 2));
		add(weaterLeftPanel(width / 2, height / 2));
		add(weaterRightPanel(width / 2, height / 2));
	}

	private JPanel tLeftPanel(int width, int height) {

		/************ 温度 ***********/
		JPanel wenduMainPanel = new Temperature(EchartType.THERMOMETER, Util.random(20, 50)).size(120, height).handle();
		wenduMainPanel.setPreferredSize(new Dimension(80, height));
		wenduMainPanel.setOpaque(false);
		JPanel wenduPanel = new Cpanel(wenduMainPanel);

		/************ 湿度 ***********/
		JPanel shiduChat = new Humidity(EchartType.GAUGE, Util.random(80, 100)).size(248, 248).handle();
		JPanel shiduMainPanel = new JPanel();
		shiduMainPanel.setOpaque(false);
		shiduMainPanel.add(shiduChat);
		Cpanel shiduPanel = new Cpanel("当前湿度", "Current humidity (%)", shiduMainPanel);
		/************ 预警信息 ***********/
		JPanel warnMainPanel = new JPanel();
		warnMainPanel.setOpaque(false);
		warnMainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		warnMainPanel.setLayout(new BorderLayout(5, 5));

		JTextArea warnTypeArea = new JTextArea();
		warnTypeArea.setText("预警类型：大风预警");
		warnTypeArea.setEditable(false);
		warnTypeArea.setFont(new Font("微软雅黑", Font.BOLD, 14));
		warnTypeArea.setForeground(Color.WHITE);
		warnTypeArea.setOpaque(false);
		warnTypeArea.setWrapStyleWord(true);
		warnTypeArea.setLineWrap(true);

		JTextArea warnLevelArea = new JTextArea();
		warnLevelArea.setText("预警类型：Ⅲ级");
		warnLevelArea.setEditable(false);
		warnLevelArea.setFont(new Font("微软雅黑", Font.BOLD, 14));
		warnLevelArea.setForeground(Color.WHITE);
		warnLevelArea.setOpaque(false);
		warnLevelArea.setWrapStyleWord(true);
		warnLevelArea.setLineWrap(true);

		JTextArea warnInfoArea = new JTextArea();
		warnInfoArea.setText(
				"预警信息：晋中市气象台2020年5月10日10时55分发布大风蓝色预警信号，预警区域为：全市。 预计24小时内可能受大风影响，平均风力可达4-5级,阵风可达6-7级或以上，请有关单位和人员做好防范准备");
		warnInfoArea.setEditable(false);
		warnInfoArea.setFont(new Font("微软雅黑", Font.BOLD, 14));
		warnInfoArea.setForeground(Color.WHITE);
		warnInfoArea.setOpaque(false);
		warnInfoArea.setWrapStyleWord(true);
		warnInfoArea.setLineWrap(true);

		warnMainPanel.add(warnTypeArea, BorderLayout.NORTH);
		warnMainPanel.add(warnLevelArea, BorderLayout.CENTER);
		warnMainPanel.add(warnInfoArea, BorderLayout.SOUTH);

		JPanel warnPanel = new Cpanel("预警信息", "The early warning information", warnMainPanel);
		/************ 左面板 ***********/
		JPanel leftPanel = new JPanel();
		leftPanel.setOpaque(false);
		leftPanel.setLayout(new BorderLayout(5, 5));
		leftPanel.add(wenduPanel, BorderLayout.WEST);
		leftPanel.add(shiduPanel, BorderLayout.CENTER);
		leftPanel.add(warnPanel, BorderLayout.SOUTH);
		/************ 右面板 ***********/
		JPanel rightPanel = new Env(0, height);
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new GridLayout(1, 2, 5, 5));
		panel.add(leftPanel);
		panel.add(rightPanel);
		return panel;
	}

	private JPanel tRightPanel(int width, int height) {
		JPanel mainPanel = new Weather().size(width, height - 90).line().handle();

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.add(mainPanel);

		return new Cpanel("未来7天天气预报", "Weather forecast for the next 7 days", panel);
	}

	private JPanel weaterLeftPanel(int width, int height) {
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

	private JPanel weaterRightPanel(int width, int height) {

		JPanel mainPanel = new Hours().size(width, height - 90).line().handle();

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.add(mainPanel);
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
