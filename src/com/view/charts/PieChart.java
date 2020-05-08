package com.view.charts;

import java.awt.Color;
import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.general.DefaultPieDataset;

/**
 * 饼图
 * 
 * @author xiebing
 */
public class PieChart {
	public PieChart() {
	}

	public DefaultPieDataset createDataset() {
		String[] categories = { "Bananas", "Kiwi", "Mixed nuts", "Oranges", "Apples", "Pears", "Clementines",
				"Reddish (bag)", "Grapes (bunch)", };
		Object[] datas = { 8, 3, 1, 6, 8, 4, 4, 1, 1 };
		DefaultPieDataset dataset = Util.createDefaultPieDataset(categories, datas);
		return dataset;
	}

	public ChartPanel createChart() {
		// 2：创建Chart[创建不同图形]
		JFreeChart chart = ChartFactory.createPieChart("Contents of Highsoft's weekly fruit delivery", createDataset());
		// 3:设置抗锯齿，防止字体显示不清楚
		// 抗锯齿
		Util.setAntiAlias(chart);
		// 4:对柱子进行渲染[创建不同图形]
		Util.setPieRender(chart.getPlot());
		chart.setBorderVisible(false);
		chart.setBackgroundPaint(null);
		chart.setBackgroundImageAlpha(0.0f);

		Plot plot = chart.getPlot();

		// 饼图的透明度
		plot.setForegroundAlpha(0.5f);
		// 饼图的背景全透明
		plot.setBackgroundAlpha(0.0f);
		// 去除背景边框线
		plot.setOutlinePaint(null);

		/**
		 * 可以注释测试
		 */
		// plot.setSimpleLabels(true);//简单标签,不绘制线条
		// plot.setLabelGenerator(null);//不显示数字
		// 设置标注无边框
		chart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		// 标注位于右侧
		chart.getLegend().setPosition(RectangleEdge.RIGHT);
		// 6:使用chartPanel接收
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(460, 460));
		chartPanel.setOpaque(false);
		chartPanel.setBackground(Color.RED);
		return chartPanel;
	}

}
