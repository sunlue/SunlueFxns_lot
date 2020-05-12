package com.view.charts.weather;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ThermometerPlot;
import org.jfree.data.general.DefaultValueDataset;

import com.view.charts.Echarts;

public class Temperature extends Echarts {

	private int width;
	private int height;
	private EchartType EchartType;
	private double value;

	public Temperature(EchartType echartType) {
		this.EchartType = echartType;
	}

	public Temperature(EchartType echartType, double value) {
		this.EchartType = echartType;
		this.value = value;
	}

	/******************** start 温度计 *********************/

	private JFreeChart thermometer(double value) {
		DefaultValueDataset defaultvaluedataset = new DefaultValueDataset(value);
		ThermometerPlot thermometerplot = new ThermometerPlot(defaultvaluedataset);
		thermometerplot.setNoDataMessage("没有可用的数据");
		// 设置用于绘制温度计轮廓的笔划
		thermometerplot.setThermometerStroke(new BasicStroke(0.5F));
		// 设置用于绘制温度计轮廓的背景
		thermometerplot.setThermometerPaint(Color.lightGray);
		// 设置列半径
		thermometerplot.setColumnRadius(10);
		// 设置灯泡半径
		thermometerplot.setBulbRadius(30);
		// 设置温度计的上下限
		thermometerplot.setRange(-10.D, 50.0D);
		// 设置用于显示当前值的颜色
		thermometerplot.setValuePaint(Color.WHITE);
		// 设置用于显示当前值的字体
		thermometerplot.setValueFont(new Font("微软雅黑", Font.PLAIN, 20));
		// 设置范围颜色更改选项。
		thermometerplot.setUseSubrangePaint(true);
		// 设置要在温度计中显示的单位。
		thermometerplot.setUnits(ThermometerPlot.UNITS_CELCIUS);
		// 设置绘图区域背景透明度
		thermometerplot.setBackgroundAlpha(0.0f);
		// 设置用于控制是否绘制绘图轮廓的标志
		thermometerplot.setOutlineVisible(false);
		// 设置刻度颜色
		thermometerplot.setSubrange(ThermometerPlot.NORMAL, 27.0, 32.0);
		thermometerplot.setSubrange(ThermometerPlot.WARNING, 32.0, 41.0);
		thermometerplot.setSubrange(ThermometerPlot.CRITICAL, 41.0, 54.0);
		JFreeChart chart = new JFreeChart(thermometerplot);
		chart.setBackgroundPaint(null);
		return chart;
	}

	public JPanel handle() {
		JFreeChart chart = null;
		if (this.EchartType.equals(com.view.charts.Echarts.EchartType.THERMOMETER)) {
			chart = thermometer(value);
		}
		chart.setBackgroundPaint(null);
		chart.setBorderPaint(null);
		ChartPanel chartPanel = new ChartPanel(chart, width, height, width, height, width, height, true, false, false,
				false, false, true, false);
		chartPanel.setPreferredSize(new Dimension(width, height));
		chartPanel.setOpaque(false);
		return chartPanel;
	}

	public Temperature value(double value) {
		this.value = value;
		return this;
	}

	public Temperature size(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}

}
