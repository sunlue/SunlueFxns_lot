package com.view.charts.weather;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer.Pointer;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialRange;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultValueDataset;

import com.view.charts.Echarts;

/**
 * 湿度图表
 * 
 * @author xiebing
 *
 */
public class Humidity extends Echarts {

	private EchartType EchartType;
	private int width = 200;
	private int height = 200;
	private double value;

	public Humidity(EchartType echartType) {
		this.EchartType = echartType;
	}

	public Humidity(EchartType echartType, double value) {
		this.EchartType = echartType;
		this.value = value;
	}

	/******************** start 仪表盘 *********************/

	private JFreeChart gauge(double value) {
		/**
		 * 数据集合对象 此处为DefaultValueDataset
		 */
		DefaultValueDataset dataset = new DefaultValueDataset(value);
		/**
		 * 获取图表区域对象
		 */
		DialPlot dialplot = new DialPlot();
		dialplot.setView(0.0D, 0.0D, 1.0D, 1.0D);
		dialplot.setDataset(0, dataset);
		/**
		 * 开始设置显示框架结构 B. setDailFrame(DailFrame dailFrame); 设置表盘的底层面板图像，通常表盘是整个仪表的最底层。
		 */

		StandardDialFrame dialFrame = new StandardDialFrame();
		dialFrame.setBackgroundPaint(new Color(7, 10, 85));
		dialFrame.setForegroundPaint(new Color(7, 10, 85));
		dialplot.setDialFrame(dialFrame);
		// 设置显示在表盘中央位置的信息
		DialTextAnnotation dialtextannotation = new DialTextAnnotation("湿度(%)");
		dialtextannotation.setFont(new Font("微软雅黑", 1, 14));
		dialtextannotation.setRadius(0.65D);
		dialtextannotation.setPaint(Color.WHITE);
		dialplot.addLayer(dialtextannotation);

		/**
		 * 指针指向的数据,用文本显示出来,并指向一个数据集
		 */
		DialValueIndicator dvi = new DialValueIndicator(0);
		dvi.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		dvi.setAngle(86.0);
		dvi.setRadius(-0.5);
		dvi.setPaint(Color.WHITE);
		dvi.setBackgroundPaint(new Color(7, 10, 85));
		dvi.setOutlinePaint(new Color(7, 10, 85));
		dialplot.addLayer(dvi);

		// 对应pointer
		StandardDialScale dialScale = new StandardDialScale();
		// 最底表盘刻度
		dialScale.setLowerBound(0D);
		// 最高表盘刻度
		dialScale.setUpperBound(100D);
		// 刚好与人的正常视觉对齐,弧度为120
		dialScale.setStartAngle(-120D);
		// 刚好与人的正常视觉对齐,弧度为300
		dialScale.setExtent(-300D);
		// 值越大,与刻度盘框架边缘越近
		dialScale.setTickRadius(0.88D);
		// 值越大,与刻度盘刻度越远0.14999999999999999D
		dialScale.setTickLabelOffset(0.14999999999999999D);
		// 刻度盘刻度字体
		dialScale.setTickLabelFont(new Font("微软雅黑", 0, 14));
		// 刻度盘刻度字体颜色
		dialScale.setTickLabelPaint(Color.WHITE);
		// 设置刻度线的颜色
		dialScale.setMajorTickPaint(new Color(30, 144, 255));
		// 设置刻度线增量
		dialScale.setMajorTickIncrement(10.0);
		dialplot.addScale(0, dialScale);

		// 设置刻度范围
		StandardDialRange standarddialrange = new StandardDialRange(0D, 100D, new Color(30, 144, 255));
		standarddialrange.setInnerRadius(0.9D);
		standarddialrange.setOuterRadius(0.9D);
		dialplot.addLayer(standarddialrange);

		/**
		 * 设置指针 G. addPointer(DailPointer dailPointer);
		 * 用于设定表盘使用的指针样式，JFreeChart中有很多可供选择指针样式， 用户可以根据使用需要，采用不同的DailPoint的实现类来调用该方法
		 */
		// 指针一
		Pointer pointer = new Pointer(0);
		pointer.setOutlinePaint(new Color(66, 228, 251));
		pointer.setWidthRadius(0.04D);
		pointer.setFillPaint(new Color(66, 228, 251));
		pointer.setRadius(0.6D);
		dialplot.addPointer(pointer);
		dialplot.mapDatasetToScale(0, 0);

		DialCap dialcap = new DialCap();
		dialcap.setRadius(0.06D);
		dialplot.setCap(dialcap);

		JFreeChart chart = new JFreeChart(dialplot);
		chart.setBackgroundPaint(null);
		return chart;
	}

	/******************** end 仪表盘 *********************/

	public JPanel handle() {
		JFreeChart chart = null;
		if (this.EchartType.equals(com.view.charts.Echarts.EchartType.GAUGE)) {
			chart = gauge(value);
		}
		chart.setBackgroundPaint(null);
		chart.setBorderPaint(null);
		ChartPanel chartPanel = new ChartPanel(chart, width, height, width, height, width, height, true, false, false,
				false, false, true, false);
		chartPanel.setPreferredSize(new Dimension(width, height));
		chartPanel.setOpaque(false);
		return chartPanel;
	}

	public Humidity value(double value) {
		this.value = value;
		return this;
	}

	public Humidity size(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}
}
