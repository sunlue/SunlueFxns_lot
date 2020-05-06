package com.view.charts;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Vector;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;


/**
 * 
 * @author ccw
 * @date 2014-6-11
 *       <p>
 *       创建图表步骤：<br/>
 *       1：创建数据集合<br/>
 *       2：创建Chart：<br/>
 *       3:设置抗锯齿，防止字体显示不清楚<br/>
 *       4:对柱子进行渲染，<br/>
 *       5:对其他部分进行渲染<br/>
 *       6:使用chartPanel接收<br/>
 * 
 *       </p>
 */
public class TimeSeriesChart {
	public TimeSeriesChart() {
	}

	/**
	 * 创建数据集合
	 * 
	 * @return
	 */
	public TimeSeriesCollection createDataset() {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		String[] categories = { "股东总户数", "前十名持股比例" };
	
		Random random = new Random();
		for (int row = 0; row < categories.length; row++) {
			Vector<Object[]> dateValues = new Vector<Object[]>();
			for (int i = 0; i < 20; i++) {
				String date = (2000 + i) + "-0" + i + "-21";
				System.out.println(date);
				Object[] dateValue = { date, random.nextInt(10) };
				dateValues.add(dateValue);

			}
			TimeSeries timeSeries = Util.createTimeseries(categories[row], dateValues);
			dataset.addSeries(timeSeries);
		}
		return dataset;
	}

	public ChartPanel createChart() {
		// 2：创建Chart[创建不同图形]
		TimeSeriesCollection dataset = createDataset();
		JFreeChart chart = ChartFactory.createTimeSeriesChart("股东总户数", "", "", dataset);
		// 3:设置抗锯齿，防止字体显示不清楚
		// 抗锯齿
		Util.setAntiAlias(chart);
		// 4:对柱子进行渲染[创建不同图形]
		Util.setTimeSeriesRender(chart.getPlot(), true, true);
		// 5:对其他部分进行渲染
		XYPlot xyplot = (XYPlot) chart.getPlot();
		Util.setXyXaixs(xyplot);
		Util.setXyYaixs(xyplot);
		// 日期X坐标轴
		DateAxis domainAxis = (DateAxis) xyplot.getDomainAxis();
		domainAxis.setAutoTickUnitSelection(false);
		DateTickUnit dateTickUnit = null;
		if (dataset.getItemCount(0) < 16) {
			//刻度单位月,半年为间隔
			// 第二个参数是时间轴间距
			dateTickUnit = new DateTickUnit(DateTickUnitType.MONTH, 6, new SimpleDateFormat("yyyy-MM"));
		} else {// 数据过多,不显示数据
			XYLineAndShapeRenderer xyRenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
			xyRenderer.setDefaultItemLabelsVisible(false);
			// 第二个参数是时间轴间距
			dateTickUnit = new DateTickUnit(DateTickUnitType.YEAR, 1, new SimpleDateFormat("yyyy"));
		}
		// 设置时间单位
		domainAxis.setTickUnit(dateTickUnit);
		Util.setLegendEmptyBorder(chart);
		// 设置图例位置
		// 6:使用chartPanel接收
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}


}
