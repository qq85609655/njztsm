package com.njztsm.app.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSONObject;
import com.njztsm.app.AppContext;
import com.njztsm.app.AppException;
import com.njztsm.app.R;
import com.njztsm.app.bean.LineChart;
import com.njztsm.app.common.StringUtils;
import com.njztsm.app.common.UIHelper;
import com.njztsm.app.script.JSinterface;
import com.njztsm.app.widget.NewDataToast;

/**
 * 应用程序首页
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
@SuppressLint("HandlerLeak")
public class Search extends BaseActivity {

	private WebView webView;
	private String url = "file:///android_asset/chart.html";
	private AppContext appContext;// 全局Context

	private Button search;
	private EditText editText;
	private Handler webViewHandler;
	private boolean isWebViewShow = false;
	private ProgressBar progress;
	private LineChart lineChart;
	private int width;
	private int height;
	private InputMethodManager imm;
	private int day = 7;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		webViewHandler = new Handler();

		search = (Button) findViewById(R.id.main_search);
		progress = (ProgressBar) findViewById(R.id.main_head_progress);
		editText = (EditText) findViewById(R.id.search_phone);
		webView = (WebView) findViewById(R.id.webViewChart);
		progress = (ProgressBar) findViewById(R.id.main_head_progress);
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

		appContext = (AppContext) getApplication();
		// 网络连接判断
		if (!appContext.isNetworkConnected())
			UIHelper.ToastMessage(this, R.string.network_not_connected);

		search.setOnClickListener(searchClickListener);
		width = getW();
		height = getH();

		editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					imm.showSoftInput(v, 0);
				} else {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
		});

		editText.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER
						|| keyCode == KeyEvent.KEYCODE_SEARCH) {
					if (v.getTag() == null) {
						v.setTag(1);
						editText.clearFocus();
						String phone = editText.getText().toString();
						initWebView(day, phone);
					} else {
						v.setTag(null);
					}
					return true;
				}
				return false;
			}
		});

	}

	private void initWebView(int day, final String phone) {

		if (StringUtils.isEmpty(phone)) {
			UIHelper.ToastMessage(appContext, "亲，您得输入手机号码才能检索哦！");
			return;
		}

		if (!StringUtils.isPhone(phone)) {
			UIHelper.ToastMessage(appContext, "亲，您要输入正确的手机号码才行哦！");
			return;
		}

		progress.setVisibility(View.VISIBLE);
		search.setEnabled(false);
		webViewHandler.post(new Runnable() {
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
				if (null == lineChart) {
					progress.setVisibility(View.GONE);
					search.setEnabled(true);
					UIHelper.ToastMessage(appContext, msg);

					return;
				}
				UIHelper.ToastMessage(appContext, "正在加载数据，请稍候！");
				if (isWebViewShow) {
					webViewCallJS();
					return;
				}
				setSupportJavaScript();
				setInterface4JavaScript();
				setWebViewClient();
				setWebViewChromeClient();
				webView.loadUrl(url);
			}

		});
	}

	private void webViewCallJS() {
		String data = JSONObject.toJSONString(lineChart);
		webView.loadUrl("javascript:execute(" + data + "," + width + ","
				+ height + ")");
		progress.setVisibility(View.GONE);
		search.setEnabled(true);
		// getString(R.string.new_data_toast_complete, newdata)
		NewDataToast.makeText(webView.getContext(),
				getString(R.string.new_data_toast_complete),
				appContext.isAppSound()).show();
	}

	/**
	 * 设置WebView对象支持javascript
	 */
	private void setSupportJavaScript() {
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDefaultTextEncodingName("UTF-8");
		settings.setPluginsEnabled(true);
		// // 开启JavaScript支持
		/** 设置WebView可触摸放大缩小： */
		// settings.setSupportZoom(true);
		// settings.setBuiltInZoomControls(true);
		settings.setRenderPriority(RenderPriority.HIGH);
		// settings.setBlockNetworkImage(true);
	}

	/**
	 * 添加java方法的调用接口，允许javascript脚本调用java方法
	 */
	private void setInterface4JavaScript() {
		webView.addJavascriptInterface(new JSinterface(appContext,
				webViewHandler, webView), "njztsm");
	}

	/**
	 * WebView中链接的跳转 WebViewClient主要帮助WebView处理各种通知、请求事件
	 */
	private void setWebViewClient() {
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				UIHelper.ToastMessage(view.getContext(), url);
				// 直接在当前WebView中加载url
				view.loadUrl(url);
				return true;
				// return super.shouldOverrideUrlLoading(view, url);
			}

			// 当页面加载完成时调用Javascript方法
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				webViewCallJS();
				isWebViewShow = true;
			}
		});
	}

	/**
	 * 处理WebView加载网页弹出的对话框
	 */
	private void setWebViewChromeClient() {
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				UIHelper.ToastMessage(view.getContext(), message);

				final AlertDialog.Builder builder = new AlertDialog.Builder(
						view.getContext());

				builder.setTitle("对话框").setMessage(message)
						.setPositiveButton("确定", null);

				// 不需要绑定按键事件
				// 屏蔽keycode等于84之类的按键
				builder.setOnKeyListener(new OnKeyListener() {
					public boolean onKey(DialogInterface dialog, int keyCode,
							KeyEvent event) {
						Log.v("onJsAlert", "keyCode==" + keyCode + "event="
								+ event);
						return true;
					}
				});
				// 禁止响应按back键的事件
				builder.setCancelable(false);
				AlertDialog dialog = builder.create();
				dialog.show();
				result.confirm();// 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容。
				return true;

				// 此处应该调用super的onJsAlert，否则内容无法加载，页面将显示空白
				// return super.onJsAlert(view, url, message, result);
			}
		});
	}

	private View.OnClickListener searchClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			editText.clearFocus();
			String phone = editText.getText().toString();
			initWebView(day, phone);
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}

	/**
	 * 处理menu的事件
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int item_id = item.getItemId();
		switch (item_id) {
		case R.id.main_menu_setting:
			UIHelper.showChartSetting(this);
			break;
		case R.id.main_menu_exit:
			UIHelper.Exit(this);
			break;
		}
		return true;
	}

	private int getW() {
		return px2dip(webView.getResources().getDisplayMetrics().widthPixels);
	}

	private int getH() {
		return px2dip(webView.getResources().getDisplayMetrics().heightPixels);
	}

	private int px2dip(float pxValue) {
		final float scale = webView.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}
