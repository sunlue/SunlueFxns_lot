package com.view.charts.weather;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.category.DefaultCategoryDataset;

import com.view.charts.Echarts;
import com.view.charts.Serie;

public class Hours extends Echarts {
	private JFreeChart chart;
	private String title;
	private int width;
	private int height;

	/******************** start 折线图 *********************/
	public Hours line() {
		line("", "");
		return this;
	}

	public Hours line(String categoryAxisLabel, String valueAxisLabel) {
		String[] categories = { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };
		Vector<Serie> series = new Vector<Serie>();
		// 柱子名称:柱子所有的值集合
		series.add(new Serie("低温", new Double[] { 13.0, 16.0, 16.0, 20.0, 21.0, 20.0, 18.0 }));
		series.add(new Serie("高温", new Double[] { 25.0, 26.0, 27.0, 29.0, 30.0, 29.0, 31.6 }));
		// 创建数据集合
		DefaultCategoryDataset dataset = createDefaultCategoryDataset(series, categories);

		// 创建Chart[创建不同图形]
		chart = ChartFactory.createLineChart(title, categoryAxisLabel, valueAxisLabel, dataset);

		/****** 标注 ******/
		if (chart.getLegend() != null) {
			// 设置标注无边框
			chart.getLegend().setBorder(0, 0, 0, 0);
			// 设置标注背景色
			chart.getLegend().setBackgroundPaint(null);
			// 设置标注字体颜色
			chart.getLegend().setItemPaint(new Color(255, 255, 255));
			// 标注位于右侧
			chart.getLegend().setPosition(RectangleEdge.TOP);
		}

		// 获取绘图区
		CategoryPlot plot = chart.getCategoryPlot();
		// 设置绘图区透明背景
		plot.setBackgroundAlpha(0.0f);
		// 设置不显示网格线
//		plot.setRangeGridlinesVisible(false);
		// 设置绘图区边框不可见
		plot.setOutlineVisible(false);

		// 设置抗锯齿，防止字体显示不清楚
		// 抗锯齿
		setAntiAlias(chart);
		// 对柱子进行渲染
		setLineRender(plot, true, true);
		// 对其他部分进行渲染
		// X坐标轴渲染
		CategoryAxis x = plot.getDomainAxis();
		// X坐标轴颜色
		x.setAxisLinePaint(Color.WHITE);
		// X坐标轴标记|竖线颜色
		x.setTickMarkPaint(Color.WHITE);
		x.setLabelPaint(Color.WHITE);
		x.setTickLabelPaint(Color.WHITE);
		x.setTickLabelFont(new Font("微软雅黑", Font.BOLD, 14));
		plot.setDomainAxis(x);

		// Y坐标轴渲染
		setYaixs(chart.getCategoryPlot());

		return this;
	}

	public Hours size(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}

	public JPanel handle() {
		chart.setBackgroundPaint(null);
		chart.setBorderPaint(null);
		ChartPanel chartPanel = new ChartPanel(chart, width, height, width, height, width, height, true, false, false,
				false, false, true, false);
		chartPanel.setPreferredSize(new Dimension(width, height));
		chartPanel.setOpaque(false);
		return chartPanel;
	}

	/******************** end 折线图 *********************/
}
