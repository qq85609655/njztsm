package com.njztsm.app.ui;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.njztsm.app.R;
import com.njztsm.app.chart.PmChartView;

/**
 * 用户资料
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class UserReport extends BaseActivity {
	RelativeLayout mRl_base;
	PmChartView pmChartView;
	GraphicalView graphicalView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_report);

		mRl_base = (RelativeLayout) findViewById(R.id.base_rl);

		String[] titles = new String[] { "低舒张压", "高舒张压", "健康指数" };// 显示名称

		List<double[]> xPoints = new ArrayList<double[]>();
		for (int i = 0; i < titles.length; i++) {
			xPoints.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 });
		}

		List<double[]> values = new ArrayList<double[]>();
		values.add(new double[] { 60, 52, 53, 76, 80, 94, 66, 56, 73, 80 });

		values.add(new double[] { 170, 162, 163, 186, 180, 174, 176, 166, 183,
				180 });

		values.add(new double[] { 120, 102, 110, 105, 100, 110, 120, 109, 130,
				120 });

		int[] colors = new int[] { Color.BLUE, Color.RED, Color.DKGRAY };
		PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE,
				PointStyle.CIRCLE, PointStyle.CIRCLE };

		pmChartView = new PmChartView("血压", "显示每月每天的体检变化情况");
		try {
			pmChartView.initDataset(titles, xPoints, values);
			XYMultipleSeriesRenderer renderers = pmChartView.initRenderer(
					colors, styles);

			pmChartView.setChartSettings(renderers, "血压值", "日", "舒张压", 1, 15,
					50, 190, Color.LTGRAY, Color.LTGRAY);
			// X轴上部分和Y轴的右部分
			renderers.setApplyBackgroundColor(true);
			renderers.setBackgroundColor(Color.WHITE);
			renderers.setMarginsColor(Color.WHITE);
			// 设置是否在图表中显示网格
			renderers.setShowGrid(true);
			// 设置X轴的最小值为1
			renderers.setXAxisMin(1);
			// 设置X轴的最大值为15
			renderers.setXAxisMax(15);
			// 设置Y轴的最小值为50
			renderers.setYAxisMin(50);
			// 设置Y轴最大值为190
			renderers.setYAxisMax(190);
			// renderers.setAxesColor(color.white);

			// 设置X坐标分成31份
			renderers.setXLabels(15);
			// 设置y坐标分成30份
			renderers.setYLabels(15);
			renderers.setXLabelsAlign(Align.CENTER);
			renderers.setYLabelsAlign(Align.CENTER);
			// renderers.setPointSize(10);

			// 如果我想让y轴不动该怎么办呀？
			// renderers.setZoomEnabled(true, false);
			// 一个设置拖动时Y轴不动，一个设置放大缩小时Y轴不放大缩小
			renderers.setPanLimits(new double[] { 1, 15, 50, 190 });
			renderers.setZoomLimits(new double[] { 1, 15, 50, 190 });
			renderers.setClickEnabled(true);
			renderers.setSelectableBuffer(30);
			int length = renderers.getSeriesRendererCount();
			for (int i = 0; i < length; i++) {
				((XYSeriesRenderer) renderers.getSeriesRendererAt(i))
						.setFillPoints(true);
			}
			graphicalView = pmChartView.executeForView(this);

			graphicalView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// handle the click event on the chart
					SeriesSelection seriesSelection = graphicalView
							.getCurrentSeriesAndPoint();
					if (seriesSelection == null) {
						Toast.makeText(UserReport.this, "没有点击",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(
								UserReport.this,
								"series index "
										+ seriesSelection.getSeriesIndex()
										+ " point index "
										+ seriesSelection.getPointIndex()
										+ "  X=" + seriesSelection.getXValue()
										+ ", Y=" + seriesSelection.getValue(),
								Toast.LENGTH_SHORT).show();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		ViewGroup.LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		mRl_base.addView(graphicalView, lp);

	}

}
