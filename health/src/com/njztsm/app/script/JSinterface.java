package com.njztsm.app.script;

import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.njztsm.app.AppContext;
import com.njztsm.app.AppException;
import com.njztsm.app.bean.LineChart;
import com.njztsm.app.common.UIHelper;

import android.os.Handler;
import android.webkit.WebView;
import android.widget.Toast;

public class JSinterface {

	private AppContext appContext;
	private Handler handler;
	private LineChart lineChart;
	private WebView webView;

	public JSinterface(AppContext appContext, Handler handler, WebView webView) {
		this.appContext = appContext;
		this.handler = handler;
		this.webView = webView;
	}

	public void search(final String phone) {
		// 通过handler来确保init方法的执行在主线程中
		handler.post(new Runnable() {
			public void run() {
				int day = 7;
				String type = "-";
				String time = null;
				try {
					lineChart = appContext.getLineChartList(day, phone, type,
							time);
				} catch (AppException e) {
					e.printStackTrace();
				}
				String msg = "亲,没发现体检数据!";
				if (null != lineChart) {
					String data = JSONObject.toJSONString(lineChart);
					webView.loadUrl("javascript:splitChart(" + data + ")");
					msg = "亲 ,体检数据加载成功!";
				}

				UIHelper.ToastMessage(appContext, msg);

			}
		});
	}

	public int getW() {
		return px2dip(appContext.getResources().getDisplayMetrics().widthPixels);
	}

	public int getH() {
		return px2dip(appContext.getResources().getDisplayMetrics().heightPixels);
	}

	public int px2dip(float pxValue) {
		final float scale = appContext.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public void setValue(String name, String value) {
		Toast.makeText(appContext, name + " " + value + "%", Toast.LENGTH_SHORT)
				.show();
	}

	public String getRandColorCode() {
		String r, g, b;
		Random random = new Random();
		r = Integer.toHexString(random.nextInt(256)).toUpperCase();
		g = Integer.toHexString(random.nextInt(256)).toUpperCase();
		b = Integer.toHexString(random.nextInt(256)).toUpperCase();

		r = r.length() == 1 ? "0" + r : r;
		g = g.length() == 1 ? "0" + g : g;
		b = b.length() == 1 ? "0" + b : b;

		return "#" + r + g + b;
	}

	public LineChart getLineChart() {
		return lineChart;
	}

	public void setLineChart(LineChart lineChart) {
		this.lineChart = lineChart;
	}

}