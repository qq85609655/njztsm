/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.njztsm.app.chart;

import java.util.Date;
import java.util.List;

import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

/**
 * An abstract class for the demo charts to extend. It contains some methods for
 * building datasets and renderers.
 * 
 */
//TODO step2封装了构建DataSet和renderer的方法,目的快速构建我们的dataset和renderer

//TODO step3其他的以*Chat结尾的类大都继承自AbstractDemoChart这个类实现了接口IDemoChart.
//我们可以重点看一下execute(context)方法,分析之后发现这个方法中构建Intent的步骤大同小异,
//第一步构建dataset,第二步构建renderer,第三步调用ChartFactory.get***Intent()方法或ChartFactory.get***View()方法,
public abstract class AbstractDemoChart implements IDemoChart {

  /**
   * Builds an XY multiple dataset using the provided values.
   * 
   * @param titles the series titles
   * @param xValues the values for the X axis
   * @param yValues the values for the Y axis
   * @return the XY multiple dataset
   */
  protected XYMultipleSeriesDataset buildDataset(String[] titles, List<double[]> xValues,
      List<double[]> yValues) {
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    addXYSeries(dataset, titles, xValues, yValues, 0);
    return dataset;
  }
/**
 * 向DataSet中添加序列. 
 * @param dataset
 * @param titles
 * @param xValues
 * @param yValues
 * @param scale
 */
  public void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles, List<double[]> xValues,
      List<double[]> yValues, int scale) {
    int length = titles.length;
    for (int i = 0; i < length; i++) {
      XYSeries series = new XYSeries(titles[i], scale);// //这里注意与TimeSeries区别.
      double[] xV = xValues.get(i);
      double[] yV = yValues.get(i);
      int seriesLength = xV.length;
      for (int k = 0; k < seriesLength; k++) {
        series.add(xV[k], yV[k]);
      }
      dataset.addSeries(series);
    }
  }

  /**
   * 构建XYMultipleSeriesRenderer. 
   * Builds an XY multiple series renderer.
   * 
   * @param colors the series rendering colors 每个序列的颜色
   * @param styles the series point styles 每个序列点的类型(可设置三角,圆点,菱形,方块等多种)
   * @return the XY multiple series renderers
   */
  protected XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles) {
    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    setRenderer(renderer, colors, styles);
    return renderer;
  }
/**
 *
 * @param renderer
 * @param colors
 * @param styles
 */
  protected void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors, PointStyle[] styles) {
    // 整个图表属性设置
    renderer.setAxisTitleTextSize(16);//设置轴标题文字的大小 
    renderer.setChartTitleTextSize(20);//设置整个图表标题文字的大小
    renderer.setLabelsTextSize(15);//设置轴刻度文字的大小
    renderer.setLegendTextSize(15);//设置图例文字大小
    renderer.setPointSize(5f);//设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)  
    renderer.setMargins(new int[] { 20, 30, 15, 20 });//设置图表的外边框(上/左/下/右) 
    
    //以下代码设置每个个序列的颜色.
    int length = colors.length;
    for (int i = 0; i < length; i++) {
      XYSeriesRenderer r = new XYSeriesRenderer();
      r.setColor(colors[i]);
      r.setPointStyle(styles[i]);
      renderer.addSeriesRenderer(r);
    }
  }

  /**
   * Sets a few of the series renderer settings.设置renderer的一些属性. 
   * 
   * @param renderer the renderer to set the properties to要设置的renderer
   * @param title the chart title 图表标题
   * @param xTitle the title for the X axis X轴标题
   * @param yTitle the title for the Y axis y轴标题
   * @param xMin the minimum value on the X axis X轴最小值
   * @param xMax the maximum value on the X axis  X轴最大值
   * @param yMin the minimum value on the Y axis Y轴最小值
   * @param yMax the maximum value on the Y axis Y轴最大值
   * @param axesColor the axes color X轴颜色
   * @param labelsColor the labels color y轴颜色
   */
  public void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
      String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor,
      int labelsColor) {
    renderer.setChartTitle(title);
    renderer.setXTitle(xTitle);
    renderer.setYTitle(yTitle);
    renderer.setXAxisMin(xMin);
    renderer.setXAxisMax(xMax);
    renderer.setYAxisMin(yMin);
    renderer.setYAxisMax(yMax);
    renderer.setAxesColor(axesColor);
    renderer.setLabelsColor(labelsColor);
  }

  /**
   * Builds an XY multiple time dataset using the provided values.
   * 构建和时间有关的XYMultipleSeriesDataset,这个方法与buildDataset在参数上区别是需要List<Date[]>作参数.
   * @param titles the series titles
   * @param xValues the values for the X axis
   * @param yValues the values for the Y axis
   * @return the XY multiple time dataset
   */
  protected XYMultipleSeriesDataset buildDateDataset(String[] titles, List<Date[]> xValues,
      List<double[]> yValues) {
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    int length = titles.length;
    for (int i = 0; i < length; i++) {
      TimeSeries series = new TimeSeries(titles[i]);//构建时间序列TimeSeries,
      Date[] xV = xValues.get(i);
      double[] yV = yValues.get(i);
      int seriesLength = xV.length;
      for (int k = 0; k < seriesLength; k++) {
        series.add(xV[k], yV[k]);
      }
      dataset.addSeries(series);
    }
    return dataset;
  }

  /**
   * Builds a category series using the provided values.
   * 构建单个CategorySeries,可用于生成饼图,注意与buildMultipleCategoryDataset(构建圆环图)相区别. 
   * @param titles the series titles
   * @param values the values
   * @return the category series
   */
  protected CategorySeries buildCategoryDataset(String title, double[] values) {
    CategorySeries series = new CategorySeries(title);
    int k = 0;
    for (double value : values) {
      series.add("Project " + ++k, value);
    }

    return series;
  }

  /**
   * Builds a multiple category series using the provided values.
   * 构建MultipleCategorySeries,可用于构建圆环图(每个环是一个序列)
   * @param titles the series titles
   * @param values the values
   * @return the category series
   */
  protected MultipleCategorySeries buildMultipleCategoryDataset(String title,
      List<String[]> titles, List<double[]> values) {
    MultipleCategorySeries series = new MultipleCategorySeries(title);
    int k = 0;
    for (double[] value : values) {
      series.add(2007 + k + "", titles.get(k), value);
      k++;
    }
    return series;
  }

  /**
   * 构建DefaultRenderer. 
   *  
   * Builds a category renderer to use the provided colors.
   * 
   * @param colors the colors 每个序列的颜色
   * @return the category renderer
   */
  protected DefaultRenderer buildCategoryRenderer(int[] colors) {
    DefaultRenderer renderer = new DefaultRenderer();
    renderer.setLabelsTextSize(15);
    renderer.setLegendTextSize(15);
    renderer.setMargins(new int[] { 20, 30, 15, 0 });
    for (int color : colors) {
      SimpleSeriesRenderer r = new SimpleSeriesRenderer();
      r.setColor(color);
      renderer.addSeriesRenderer(r);
    }
    return renderer;
  }

  /**
   * Builds a bar multiple series dataset using the provided values.
   * 构建XYMultipleSeriesDataset,适用于柱状图. 
   * @param titles the series titles 每中柱子序列的图列
   * @param values the values 柱子的高度值
   * @return the XY multiple bar dataset
   */
  protected XYMultipleSeriesDataset buildBarDataset(String[] titles, List<double[]> values) {
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    int length = titles.length;
    for (int i = 0; i < length; i++) {
      CategorySeries series = new CategorySeries(titles[i]);
      double[] v = values.get(i);
      int seriesLength = v.length;
      for (int k = 0; k < seriesLength; k++) {
        series.add(v[k]);
      }
      dataset.addSeries(series.toXYSeries());
    }
    return dataset;
  }

  /**
   * Builds a bar multiple series renderer to use the provided colors.
   * 
   * @param colors the series renderers colors
   * @return the bar multiple series renderer
   */
  protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    renderer.setAxisTitleTextSize(16);
    renderer.setChartTitleTextSize(20);
    renderer.setLabelsTextSize(15);
    renderer.setLegendTextSize(15);
    int length = colors.length;
    for (int i = 0; i < length; i++) {
      XYSeriesRenderer r = new XYSeriesRenderer();
      r.setColor(colors[i]);
      renderer.addSeriesRenderer(r);
    }
    return renderer;
  }

}
