package com.view.datav.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.plot.PlotOrientation;

import com.view.charts.Echarts;
import com.view.charts.Serie;
import com.view.datav.Cpanel;

/**
 * 游客来源
 * 
 * @author xiebing
 *
 */
public class TouristsFrom extends JPanel {
	private static final long serialVersionUID = 1L;

	public TouristsFrom(int width, int height) {
		JLabel title = new JLabel("2020年5月游客来源");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("微软雅黑", Font.BOLD, 18));
		title.setBounds(0, 0, width, 30);
		title.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));

		/**** 饼图 ****/
		String[] pieCategories = { "四川", "绵阳", "成都", "广元", "德阳", "巴中", "自贡", "内江", "资阳", };
		Object[] pieDatas = { 8, 3, 1, 6, 8, 4, 4, 1, 0 };
		JPanel chartPiePanel = new Echarts().size(160, 160).pie(pieCategories, pieDatas, false, false, false).handle();
		chartPiePanel.setBounds(width - 180, 10, 160, 160);

		/**** 柱状图 ****/
		String[] barCategories = { "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月", "十三月",
				"十四月", "十五月", "十六月", "十七月", "十八月", "十九月", "二十月", "二十一月", "二十二月", "而是桑月", "二十四月", "二十五月", "二十六月", "二十七月",
				"二十八月", "二十九月", "三十月", "三十一月" };
		Vector<Serie> barSeries = new Vector<Serie>();
		Double[] value = new Double[] { 49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4,
				49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4, 49.9, 71.5, 106.4,
				129.2, 144.0, 176.0, 135.6 };
		barSeries.add(new Serie("游客(人)", value));
		JPanel chartBarPanel = new Echarts()
				.bar("", "游客(人)", barCategories, barSeries, PlotOrientation.VERTICAL, false, false, false)
				.size(width, height - 100).handle();
		chartBarPanel.setBounds(0, 100, width, height - 100);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setOpaque(false);
		mainPanel.add(title);
		mainPanel.add(chartPiePanel);
		mainPanel.add(chartBarPanel);
		Cpanel cpanel = new Cpanel(mainPanel);
		setOpaque(false);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(width, height));
		add(cpanel, BorderLayout.CENTER);
	}

}
