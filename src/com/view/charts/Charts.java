package com.view.charts;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.general.DefaultPieDataset;

import com.util.CyFont;

public class Charts {

	private String title = "";
	private int width = 200;
	private int height = 200;
	private JFreeChart chart = null;
	private StandardChartTheme standardChartTheme;

	public Charts() {
		standardChartTheme = new StandardChartTheme("CN");
		standardChartTheme.setExtraLargeFont(CyFont.puHuiTi(CyFont.Medium, 20));
		standardChartTheme.setRegularFont(CyFont.puHuiTi(CyFont.Medium, 15));
		standardChartTheme.setLargeFont(CyFont.puHuiTi(CyFont.Medium, 15));
		ChartFactory.setChartTheme(standardChartTheme);
	}

	public Charts pie(DefaultPieDataset data) {
		pie(data, true, true, false);
		return this;
	}

	public Charts pie(DefaultPieDataset data, boolean legend, boolean tooltips, boolean urls) {
		chart = ChartFactory.createPieChart(title, data, legend, tooltips, urls);
//		chart.setBorderVisible(false);
//		chart.setBackgroundPaint(null);
		chart.setBackgroundImageAlpha(0.0f);
		chart.getPlot().setBackgroundAlpha(0.0f);
		chart.getPlot().setOutlinePaint(null);
		return this;
	}

	public Charts title(String title) {
		this.title = title;
		return this;
	}

	public Charts size(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}

	public JPanel handle() {
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(width, height));
		chartPanel.setOpaque(false);
		return chartPanel;
	}

}
