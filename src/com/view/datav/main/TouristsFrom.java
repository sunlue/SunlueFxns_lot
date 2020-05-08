package com.view.datav.main;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.view.charts.Echarts;
import com.view.datav.Cpanel;

/**
 * 游客来源
 * 
 * @author xiebing
 *
 */
public class TouristsFrom extends JPanel {
	private static final long serialVersionUID = 1L;

	public TouristsFrom() {
		String[] categories = { "四川", "绵阳", "成都", "广元", "德阳", "巴中", "自贡", "内江", "资阳", };
		Object[] datas = { 8, 3, 1, 6, 8, 4, 4, 1, 1 };
		JPanel chartPanel = new Echarts().pie(categories, datas).size(300, 300).handle();

		JPanel mainPanel = new JPanel();
		mainPanel.setOpaque(false);
		mainPanel.add(chartPanel);
		Cpanel cpanel = new Cpanel(mainPanel);
		setOpaque(false);
		setPreferredSize(new Dimension(452, 420));
		setLayout(new BorderLayout());
		add(cpanel, BorderLayout.CENTER);
	}
}
