package com.view.datav.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.view.datav.CPanel;

public class Env extends JPanel {
	private static final long serialVersionUID = 1L;

	public Env(int width, int height) {
		/** 实时环境监测 **/

		JPanel envMain = new JPanel();
		int envMainWidth = width - 20;
		int envMainHeight = 295;
		envMain.setBounds(10, 54, envMainWidth, envMainHeight);
		envMain.setOpaque(false);
		envMain.setLayout(new BorderLayout());

		JPanel leftPanel = new JPanel();
//		leftPanel.setOpaque(false);
		leftPanel.setBackground(Color.RED);
		leftPanel.setPreferredSize(new Dimension(envMain.getWidth() / 24 * 8, envMain.getHeight()));
		JPanel aqiPanel=new JPanel();
		aqiPanel.setOpaque(false);
		aqiPanel.setLayout(new GridLayout(2,1));
		JLabel aqiNumLabel = new JLabel("114");
		aqiNumLabel.setForeground(new Color(7, 219, 255));
		aqiNumLabel.setFont(new Font("微软雅黑", Font.BOLD, 60));
		JLabel aqiTxtLabel = new JLabel("AQI 轻度污染");
		aqiTxtLabel.setForeground(Color.white);
		aqiTxtLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));

		aqiPanel.add(aqiNumLabel);
		aqiPanel.add(aqiTxtLabel);
		
		leftPanel.add(aqiPanel,BorderLayout.CENTER);

		JPanel leftPanelUl = new JPanel();
		leftPanelUl.setLayout(new GridLayout(3, 1));

		leftPanelUl.add(new JLabel("北风1级"));
		leftPanelUl.add(new JLabel("湿度67%"));
		leftPanelUl.add(new JLabel("气压972hpa"));
		leftPanel.add(leftPanelUl,BorderLayout.SOUTH);

		envMain.add(leftPanel, BorderLayout.WEST);

		
		
		
		
		JPanel rightPanel = new JPanel();
		rightPanel.setOpaque(false);
		rightPanel.setPreferredSize(new Dimension(envMain.getWidth() / 24 * 16, envMain.getHeight()));
		rightPanel.setLayout(new GridLayout(4, 1));
		JPanel envPm25Pan = new JPanel();
		envPm25Pan.add(new JLabel("<html><body><p>PM2.5</p><p>细颗粒</p></body></html>"));
		envPm25Pan.add(new JLabel("114"));
		envPm25Pan.setOpaque(false);

		rightPanel.add(envPm25Pan);
		rightPanel.add(envPm25Pan);
		rightPanel.add(envPm25Pan);
		rightPanel.add(envPm25Pan);
		envMain.add(rightPanel, BorderLayout.CENTER);
		
		
		CPanel envPanel = new CPanel("实时环境监测", "environmental monitoring", envMain);

		setOpaque(false);
		add(envPanel);

	}
}
