package com.view.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;

/**
 * charts基类
 * 
 * @author xiebing
 *
 */

public class Echarts {

	private String title = "";
	private int width = 200;
	private int height = 200;
	private static JFreeChart chart = null;
	private static PiePlot piePlot = null;

	/******************** start 饼图 *********************/
	/**
	 * 创建饼图
	 * 
	 * @param categories 类别
	 * @param datas      数值
	 * @return
	 */
	public Echarts pie(String[] categories, Object[] datas) {
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
	public Echarts pie(String[] categories, Object[] datas, boolean legend, boolean tooltips, boolean urls) {
		DefaultPieDataset dataset = Util.createDefaultPieDataset(categories, datas);
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
		Util.setAntiAlias(chart);
		// 对柱子进行渲染[创建不同图形]
		Util.setPieRender(chart.getPlot());
		return this;
	}

	/**
	 * 获取饼图绘图区
	 * 
	 * @return
	 */
	public static PiePlot getPiePlot() {
		return piePlot;
	}

	/**
	 * 设置饼图数据
	 * 
	 * @param categories
	 * @param datas
	 */
	public static void setPieData(String[] categories, Object[] datas) {
		DefaultPieDataset dataset = Util.createDefaultPieDataset(categories, datas);
		piePlot.setDataset(dataset);
	}

	/******************** end 饼图 *********************/
	/******************** start 柱状图 *********************/
	public Echarts bar(String[] categories, Vector<Serie> series) {
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
	public Echarts bar(String categoryAxisLabel, String valueAxisLabel, String[] categories, Vector<Serie> series) {
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
	public Echarts bar(String categoryAxisLabel, String valueAxisLabel, String[] categories, Vector<Serie> series,
			PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls) {

		// 创建数据集合
		DefaultCategoryDataset dataset = Util.createDefaultCategoryDataset(series, categories);
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
		Util.setAntiAlias(chart);
		// 对柱子进行渲染
		Util.setBarRenderer(chart.getCategoryPlot(), false);
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

	/******************** end 柱状图 *********************/

	/**
	 * 设置图表标题
	 * 
	 * @param title
	 * @return
	 */
	public Echarts title(String title) {
		this.title = title;
		return this;
	}

	/**
	 * 设置图表大小
	 * 
	 * @param title
	 * @return
	 */
	public Echarts size(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}

	/**
	 * 显示图表
	 * 
	 * @return
	 */
	public JPanel handle() {
		ChartPanel chartPanel = new ChartPanel(chart, width, height, width, height, width, height, true, false, false,
				false, false, true, false);
		chartPanel.setPreferredSize(new Dimension(width, height));
		chartPanel.setOpaque(false);
		return chartPanel;
	}

	public static JFreeChart getChart() {
		return chart;
	}
}

class Util {
	private static String NO_DATA_MSG = "数据加载失败";
	private static Font FONT = new Font("宋体", Font.PLAIN, 12);
	public static Color[] CHART_COLORS = { new Color(2, 114, 252), new Color(235, 97, 95), new Color(255, 170, 1),
			new Color(7, 219, 225), new Color(136, 217, 4), new Color(255, 102, 0), new Color(255, 51, 153),
			new Color(131, 191, 246) };

	static {
		setChartTheme();
	}

	public Util() {
	}

	/**
	 * 中文主题样式 解决乱码
	 */
	public static void setChartTheme() {
		// 设置中文主题样式 解决乱码
		StandardChartTheme chartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		chartTheme.setExtraLargeFont(FONT);
		// 设置图例的字体
		chartTheme.setRegularFont(FONT);
		// 设置轴向的字体
		chartTheme.setLargeFont(FONT);
		chartTheme.setSmallFont(FONT);
		// 标题字体颜色
		chartTheme.setTitlePaint(new Color(255, 255, 255));
		// 副标题字体颜色
		chartTheme.setSubtitlePaint(new Color(255, 255, 255, 80));
		// 设置标注背景
		chartTheme.setLegendBackgroundPaint(Color.WHITE);
		// 设置标注字体颜色
		chartTheme.setLegendItemPaint(Color.WHITE);
		// 设置图表背景色
		chartTheme.setChartBackgroundPaint(Color.WHITE);
		// 绘制颜色绘制颜色.轮廓供应商
		// paintSequence,outlinePaintSequence,strokeSequence,outlineStrokeSequence,shapeSequence
		// 外边框线条颜色
		Paint[] outlinePaintSequence = new Paint[] { Color.WHITE };
		// 绘制器颜色源
		DefaultDrawingSupplier drawingSupplier = new DefaultDrawingSupplier(CHART_COLORS, CHART_COLORS,
				outlinePaintSequence, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
				DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE);
		// 设置绘制颜色、线条、外边框供应商
		chartTheme.setDrawingSupplier(drawingSupplier);
		// 绘制区域背景色
		chartTheme.setPlotBackgroundPaint(Color.WHITE);
		// 绘制区域外边框
		chartTheme.setPlotOutlinePaint(Color.WHITE);
		// 链接标签颜色
		chartTheme.setLabelLinkPaint(new Color(8, 55, 114));
		// 链接标签样式
		chartTheme.setLabelLinkStyle(PieLabelLinkStyle.CUBIC_CURVE);
		// 设置X-Y坐标轴偏移量
		chartTheme.setAxisOffset(new RectangleInsets(5, 12, 5, 12));
		// X坐标轴垂直网格颜色
		chartTheme.setDomainGridlinePaint(new Color(192, 208, 224));
		// Y坐标轴水平网格颜色
		chartTheme.setRangeGridlinePaint(new Color(192, 192, 192));

		chartTheme.setBaselinePaint(Color.WHITE);
		// 不确定含义
		chartTheme.setCrosshairPaint(Color.BLUE);
		// 坐标轴标题文字颜色
		chartTheme.setAxisLabelPaint(new Color(51, 51, 51));
		// 刻度数字
		chartTheme.setTickLabelPaint(new Color(67, 67, 72));
		// 设置柱状图渲染
		chartTheme.setBarPainter(new StandardBarPainter());
		// XYBar渲染
		chartTheme.setXYBarPainter(new StandardXYBarPainter());
		// 数据标签的字体颜色
		chartTheme.setItemLabelPaint(Color.white);
		// 温度计
		chartTheme.setThermometerPaint(Color.white);

		ChartFactory.setChartTheme(chartTheme);
	}

	/**
	 * 必须设置文本抗锯齿
	 */
	public static void setAntiAlias(JFreeChart chart) {
		chart.setTextAntiAlias(false);
	}

	/**
	 * 设置图例无边框，默认黑色边框
	 */
	public static void setLegendEmptyBorder(JFreeChart chart) {
		chart.getLegend().setFrame(new BlockBorder(Color.WHITE));
	}

	/**
	 * 创建类别数据集合
	 */
	public static DefaultCategoryDataset createDefaultCategoryDataset(Vector<Serie> series, String[] categories) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Serie serie : series) {
			String name = serie.getName();
			Vector<Object> data = serie.getData();
			if (data != null && categories != null && data.size() == categories.length) {
				for (int index = 0; index < data.size(); index++) {
					String value = data.get(index) == null ? "" : data.get(index).toString();
					if (isPercent(value)) {
						value = value.substring(0, value.length() - 1);
					}
					if (isNumber(value)) {
						dataset.setValue(Double.parseDouble(value), name, categories[index]);
					}
				}
			}

		}
		return dataset;

	}

	/**
	 * 创建饼图数据集合
	 */
	public static DefaultPieDataset createDefaultPieDataset(String[] categories, Object[] datas) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (int i = 0; i < categories.length && categories != null; i++) {
			String value = datas[i].toString();
			if (isPercent(value)) {
				value = value.substring(0, value.length() - 1);
			}
			if (isNumber(value)) {
				dataset.setValue(categories[i], Double.valueOf(value));
			}
		}
		return dataset;

	}

	/**
	 * 创建时间序列数据
	 *
	 * @param category   类别
	 * @param dateValues 日期-值 数组
	 * @param xAxisTitle X坐标轴标题
	 * @return
	 */
	public static TimeSeries createTimeseries(String category, Vector<Object[]> dateValues) {
		TimeSeries timeseries = new TimeSeries(category);

		if (dateValues != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			for (Object[] objects : dateValues) {
				Date date = null;
				try {
					date = dateFormat.parse(objects[0].toString());
				} catch (ParseException e) {
				}
				String sValue = objects[1].toString();
				double dValue = 0;
				if (date != null && isNumber(sValue)) {
					dValue = Double.parseDouble(sValue);
					timeseries.add(new Day(date), dValue);
				}
			}
		}

		return timeseries;
	}

	/**
	 * 设置 折线图样式
	 *
	 * @param plot
	 * @param isShowDataLabels 是否显示数据标签 默认不显示节点形状
	 */
	public static void setLineRender(CategoryPlot plot, boolean isShowDataLabels) {
		setLineRender(plot, isShowDataLabels, false);
	}

	/**
	 * 设置折线图样式
	 *
	 * @param plot
	 * @param isShowDataLabels 是否显示数据标签
	 */
	public static void setLineRender(CategoryPlot plot, boolean isShowDataLabels, boolean isShapesVisible) {
		plot.setNoDataMessage(NO_DATA_MSG);
		plot.setInsets(new RectangleInsets(10, 10, 0, 10), false);
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();

		renderer.setDefaultStroke(new BasicStroke(1.5F));
		if (isShowDataLabels) {
			renderer.setDefaultItemLabelsVisible(true);
			renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator(
					StandardCategoryItemLabelGenerator.DEFAULT_LABEL_FORMAT_STRING, NumberFormat.getInstance()));
			renderer.setDefaultPositiveItemLabelPosition(
					new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.BOTTOM_CENTER));
		}
		// 数据点绘制形状
		renderer.setDefaultShapesVisible(isShapesVisible);
		setXaixs(plot);
		setYaixs(plot);

	}

	/**
	 * 设置时间序列图样式
	 *
	 * @param plot
	 * @param isShowData      是否显示数据
	 * @param isShapesVisible 是否显示数据节点形状
	 */
	public static void setTimeSeriesRender(Plot plot, boolean isShowData, boolean isShapesVisible) {

		XYPlot xyplot = (XYPlot) plot;
		xyplot.setNoDataMessage(NO_DATA_MSG);
		xyplot.setInsets(new RectangleInsets(10, 10, 5, 10));

		XYLineAndShapeRenderer xyRenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();

		xyRenderer.setDefaultItemLabelGenerator(new StandardXYItemLabelGenerator());
		xyRenderer.setDefaultShapesVisible(false);
		if (isShowData) {
			xyRenderer.setDefaultItemLabelsVisible(true);
			xyRenderer.setDefaultItemLabelGenerator(new StandardXYItemLabelGenerator());
			// 位置
			xyRenderer.setDefaultPositiveItemLabelPosition(
					new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.BOTTOM_CENTER));
		}
		// 数据点绘制形状
		xyRenderer.setDefaultShapesVisible(isShapesVisible);

		DateAxis domainAxis = (DateAxis) xyplot.getDomainAxis();
		domainAxis.setAutoTickUnitSelection(false);
		// 第二个参数是时间轴间距
		DateTickUnit dateTickUnit = new DateTickUnit(DateTickUnitType.YEAR, 1, new SimpleDateFormat("yyyy-MM"));
		domainAxis.setTickUnit(dateTickUnit);

		StandardXYToolTipGenerator xyTooltipGenerator = new StandardXYToolTipGenerator("{1}:{2}",
				new SimpleDateFormat("yyyy-MM-dd"), new DecimalFormat("0"));
		xyRenderer.setDefaultToolTipGenerator(xyTooltipGenerator);

		setXyXaixs(xyplot);
		setXyYaixs(xyplot);

	}

	/**
	 * 设置时间序列图样式 -默认不显示数据节点形状
	 *
	 * @param plot
	 * @param isShowData 是否显示数据
	 */

	public static void setTimeSeriesRender(Plot plot, boolean isShowData) {
		setTimeSeriesRender(plot, isShowData, false);
	}

	/**
	 * 设置时间序列图渲染：但是存在一个问题：如果timeseries里面的日期是按照天组织， 那么柱子的宽度会非常小，和直线一样粗细
	 *
	 * @param plot
	 * @param isShowDataLabels
	 */

	public static void setTimeSeriesBarRender(Plot plot, boolean isShowDataLabels) {

		XYPlot xyplot = (XYPlot) plot;
		xyplot.setNoDataMessage(NO_DATA_MSG);

		XYBarRenderer xyRenderer = new XYBarRenderer(0.1D);
		xyRenderer.setDefaultItemLabelGenerator(new StandardXYItemLabelGenerator());

		if (isShowDataLabels) {
			xyRenderer.setDefaultItemLabelsVisible(true);
			xyRenderer.setDefaultItemLabelGenerator(new StandardXYItemLabelGenerator());
		}

		StandardXYToolTipGenerator xyTooltipGenerator = new StandardXYToolTipGenerator("{1}:{2}",
				new SimpleDateFormat("yyyy-MM-dd"), new DecimalFormat("0"));
		xyRenderer.setDefaultToolTipGenerator(xyTooltipGenerator);
		setXyXaixs(xyplot);
		setXyYaixs(xyplot);

	}

	/**
	 * 设置柱状图渲染
	 *
	 * @param plot
	 * @param isShowDataLabels
	 */
	public static void setBarRenderer(CategoryPlot plot, boolean isShowDataLabels) {
		plot.setNoDataMessage(NO_DATA_MSG);
		plot.setInsets(new RectangleInsets(10, 10, 5, 10));
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		// 设置柱子最大宽度
		renderer.setMaximumBarWidth(0.075);
		if (isShowDataLabels) {
			renderer.setDefaultItemLabelsVisible(true);
		}
		setXaixs(plot);
		setYaixs(plot);
	}

	/**
	 * 设置堆积柱状图渲染
	 *
	 * @param plot
	 */

	public static void setStackBarRender(CategoryPlot plot) {
		plot.setNoDataMessage(NO_DATA_MSG);
		plot.setInsets(new RectangleInsets(10, 10, 5, 10));
		StackedBarRenderer renderer = (StackedBarRenderer) plot.getRenderer();
		renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		plot.setRenderer(renderer);
	}

	/**
	 * 设置类别图表(CategoryPlot) X坐标轴线条颜色和样式
	 */
	public static void setXaixs(CategoryPlot plot) {
		CategoryAxis x = plot.getDomainAxis();
		// X坐标轴颜色
		x.setAxisLinePaint(Color.WHITE);
		// X坐标轴标记|竖线颜色
		x.setTickMarkPaint(Color.WHITE);
		x.setLabelPaint(Color.WHITE);
		x.setTickLabelPaint(Color.WHITE);
		x.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		plot.setDomainAxis(x);
	}

	/**
	 * 设置类别图表(CategoryPlot) Y坐标轴线条颜色和样式 同时防止数据无法显示
	 *
	 */
	public static void setYaixs(CategoryPlot plot) {
		ValueAxis axis = plot.getRangeAxis();
		axis.setTickLabelPaint(Color.WHITE);
		// Y坐标轴颜色
		axis.setAxisLinePaint(Color.WHITE);
		// Y坐标轴标记|竖线颜色
		axis.setTickMarkPaint(Color.WHITE);
		// 是否显示Y刻度线
		axis.setAxisLineVisible(true);
		// 是否显示Y刻度
		axis.setTickMarksVisible(true);
		// 设置顶部Y坐标轴间距,防止数据无法显示
		plot.getRangeAxis().setUpperMargin(0.1);
		// 设置底部Y坐标轴间距
		plot.getRangeAxis().setLowerMargin(0.1);
	}

	/**
	 * 设置XY图表(XYPlot) X坐标轴线条颜色和样式
	 *
	 */
	public static void setXyXaixs(XYPlot plot) {
		Color lineColor = new Color(31, 121, 170);
		// X坐标轴颜色
		plot.getDomainAxis().setAxisLinePaint(lineColor);
		// X坐标轴标记|竖线颜色
		plot.getDomainAxis().setTickMarkPaint(lineColor);

	}

	/**
	 * 设置XY图表(XYPlot) Y坐标轴线条颜色和样式 同时防止数据无法显示
	 *
	 */
	public static void setXyYaixs(XYPlot plot) {
		Color lineColor = new Color(192, 208, 224);
		ValueAxis axis = plot.getRangeAxis();
		// X坐标轴颜色
		axis.setAxisLinePaint(lineColor);
		// X坐标轴标记|竖线颜色
		axis.setTickMarkPaint(lineColor);
		// 隐藏Y刻度
		axis.setAxisLineVisible(false);
		axis.setTickMarksVisible(false);
		// Y轴网格线条
		plot.setRangeGridlinePaint(new Color(192, 192, 192));
		plot.setRangeGridlineStroke(new BasicStroke(1));
		plot.setDomainGridlinesVisible(false);
		// 设置顶部Y坐标轴间距,防止数据无法显示
		plot.getRangeAxis().setUpperMargin(0.12);
		// 设置底部Y坐标轴间距
		plot.getRangeAxis().setLowerMargin(0.12);

	}

	/**
	 * 设置饼状图渲染
	 */
	public static void setPieRender(Plot plot) {
		plot.setNoDataMessage(NO_DATA_MSG);
		plot.setInsets(new RectangleInsets(10, 10, 5, 10));
		PiePlot piePlot = (PiePlot) plot;
		piePlot.setInsets(new RectangleInsets(0, 0, 0, 0));
		// 圆形
		piePlot.setCircular(true);
		// 简单标签
		piePlot.setSimpleLabels(true);
		piePlot.setLabelGap(0.1);
		piePlot.setInteriorGap(0.05D);
		// 图例形状
		piePlot.setLegendItemShape(new Rectangle(10, 10));
		piePlot.setIgnoreNullValues(true);
		// 去掉背景色
		piePlot.setLabelBackgroundPaint(null);
		// 去掉阴影
		piePlot.setLabelShadowPaint(null);
		// 去掉边框
		piePlot.setLabelOutlinePaint(null);
		piePlot.setShadowPaint(null);
		// 0:category 1:value:2 :percentage
		// 显示标签数据
		piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}({2})"));
	}

	/**
	 * 是不是一个%形式的百分比
	 *
	 * @param str
	 * @return
	 */
	public static boolean isPercent(String str) {
		return str != null ? str.endsWith("%") && isNumber(str.substring(0, str.length() - 1)) : false;
	}

	/**
	 * 是不是一个数字
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		return str != null ? str.matches("^[-+]?(([0-9]+)((([.]{0})([0-9]*))|(([.]{1})([0-9]+))))$") : false;
	}

}
