package com.view.charts.main;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.view.charts.Echarts;
import com.view.charts.Serie;

/**
 * 游客来源
 * 
 * @author xiebing
 *
 */
public class TouristsFrom extends Echarts {
	private int width;
	private int height;
	private String title;
	private JFreeChart chart;
	private PiePlot piePlot;

	/**
	 * 创建饼图
	 * 
	 * @param categories 类别
	 * @param datas      数值
	 * @return
	 */
	public TouristsFrom pie(String[] categories, Object[] datas) {
		pie(categories, datas, true, true, false);
		return this;
	}

	/**
	 * 创建饼图
	 * 
	 * @param categories 类别
	 * @param datas      数值
	 * @param legend     是否显示图例
	 * @param tooltips   是否生成工具提示
	 * @param urls       是否生成链接
	 * @return
	 */
	public TouristsFrom pie(String[] categories, Object[] datas, boolean legend, boolean tooltips, boolean urls) {
		DefaultPieDataset dataset = createDefaultPieDataset(categories, datas);
		chart = ChartFactory.createPieChart(title, dataset, legend, tooltips, urls);
		// 设置边界线条不可见
		chart.setBorderVisible(false);
		// 设置背景颜色
		chart.setBackgroundPaint(null);
		// 设置背景图片透明度
		chart.setBackgroundImageAlpha(0.0f);
		/****** 标注 ******/
		if (chart.getLegend() != null) {
			// 设置标注边框颜色
			chart.getLegend().setFrame(new BlockBorder(Color.RED));
			// 设置标注无边框
			chart.getLegend().setBorder(0, 0, 0, 0);
			// 设置标注背景色
			chart.getLegend().setBackgroundPaint(null);
			// 设置标注字体颜色
			chart.getLegend().setItemPaint(new Color(255, 255, 255));
			// 标注位于右侧
			chart.getLegend().setPosition(RectangleEdge.LEFT);
		}

		// 得到绘图区
		piePlot = (PiePlot) chart.getPlot();
		// 取出片区显示
//		piePlot.setExplodePercent("四川", 0.1);
		// 设置分类标签的字体颜色
		piePlot.setLabelPaint(Color.WHITE);
		// 设置分类标签的背景颜色
		piePlot.setLabelLinkPaint(Color.WHITE);
		/****** 数据区 ******/
		// 设置数据区的背景透明度，范围在0.0～1.0间
		piePlot.setBackgroundAlpha(0.0f);
		// 设置数据区的边界线条颜色
		piePlot.setOutlinePaint(null);
		// 设置抗锯齿,防止字体显示不清楚
		setAntiAlias(chart);
		// 对柱子进行渲染[创建不同图形]
		setPieRender(chart.getPlot());
		return this;
	}

	public TouristsFrom bar(String[] categories, Vector<Serie> series) {
		return bar("", "", categories, series, PlotOrientation.VERTICAL, true, true, true);
	}

	/**
	 * 创建柱状图
	 * 
	 * @param categoryAxisLabel X轴标签
	 * @param valueAxisLabel    Y轴标签
	 * @param categories        类别标签
	 * @param series            数据值
	 * @return
	 */
	public TouristsFrom bar(String categoryAxisLabel, String valueAxisLabel, String[] categories,
			Vector<Serie> series) {
		return bar(categoryAxisLabel, valueAxisLabel, categories, series, PlotOrientation.VERTICAL, true, true, true);
	}

	/**
	 * 
	 * @param categoryAxisLabel X轴文字
	 * @param valueAxisLabel    Y轴文字
	 * @param categories        类别标签
	 * @param series            数据值
	 * @param orientation       图表方向
	 * @param legend            是否显示图例标识
	 * @param tooltips          是否显示toolTips
	 * @param urls              是否生成超链接
	 * @return
	 */
	public TouristsFrom bar(String categoryAxisLabel, String valueAxisLabel, String[] categories, Vector<Serie> series,
			PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls) {

		// 创建数据集合
		DefaultCategoryDataset dataset = createDefaultCategoryDataset(series, categories);
		// 创建Chart
		chart = ChartFactory.createBarChart(title, categoryAxisLabel, valueAxisLabel, dataset, orientation, legend,
				tooltips, urls);
		// 设置图表背景颜色
		chart.setBackgroundPaint(null);
		// 设置图表背景图片透明度
		chart.setBackgroundImageAlpha(0.0f);

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
		plot.setRangeGridlinesVisible(false);
		// 设置绘图区边框不可见
		plot.setOutlineVisible(false);
		// 设置抗锯齿,防止字体显示不清楚
		setAntiAlias(chart);
		// 对柱子进行渲染
		setBarRenderer(chart.getCategoryPlot(), false);
		// 对其他部分进行渲染
		// X坐标轴渲染
		CategoryAxis xAxis = plot.getDomainAxis();
		// X坐标轴颜色
		xAxis.setAxisLinePaint(Color.WHITE);
		// X坐标轴标记|竖线颜色
		xAxis.setTickMarkPaint(Color.WHITE);
		xAxis.setLabelPaint(Color.WHITE);
		xAxis.setTickLabelPaint(Color.WHITE);
		xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		// 数据轴左边距
		xAxis.setLowerMargin(0.0);
		// 数据轴右边距
		xAxis.setUpperMargin(0.0);
		// Y坐标轴渲染
		ValueAxis yAxis = plot.getRangeAxis();
		yAxis.setTickLabelPaint(Color.WHITE);
		// Y坐标轴颜色
		yAxis.setAxisLinePaint(Color.WHITE);
		// Y坐标轴标记|竖线颜色
		yAxis.setTickMarkPaint(Color.WHITE);
		// 是否显示Y刻度线
		yAxis.setAxisLineVisible(true);
		// 是否显示Y刻度
		yAxis.setTickMarksVisible(true);
		yAxis.setLabelPaint(Color.WHITE);
		// 设置顶部Y坐标轴间距,防止数据无法显示
		plot.getRangeAxis().setUpperMargin(0.0);
		// 设置底部Y坐标轴间距
		plot.getRangeAxis().setLowerMargin(0.0);
		// 设置分类轴标记线的颜色
		plot.setDomainGridlinePaint(Color.white);
		// 设置数据轴标记线的颜色
		plot.setRangeGridlinePaint(Color.white);
		// 设置数据轴的绘制位置
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDefaultItemLabelsVisible(true);

		return this;
	}

	/**
	 * 显示图表
	 * 
	 * @return
	 */
	public JPanel handle() {
		chart.setBackgroundPaint(null);
		chart.setBorderPaint(null);
		ChartPanel chartPanel = new ChartPanel(chart, width, height, width, height, width, height, true, false, false,
				false, false, true, false);
		chartPanel.setPreferredSize(new Dimension(width, height));
		chartPanel.setOpaque(false);
		return chartPanel;
	}

	/**
	 * 设置图表标题
	 * 
	 * @param title
	 * @return
	 */
	public TouristsFrom title(String title) {
		this.title = title;
		return this;
	}

	/**
	 * 设置图表大小
	 * 
	 * @param title
	 * @return
	 */
	public TouristsFrom size(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}
}
