package com.njztsm.app.ui;

import greendroid.widget.MyQuickAction;
import greendroid.widget.QuickActionGrid;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.RenderPriority;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery.LayoutParams;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.alibaba.fastjson.JSONObject;
import com.njztsm.app.AppContext;
import com.njztsm.app.AppException;
import com.njztsm.app.R;
import com.njztsm.app.adapter.ListViewNewsAdapter;
import com.njztsm.app.bean.LineChart;
import com.njztsm.app.bean.News;
import com.njztsm.app.bean.NewsList;
import com.njztsm.app.bean.Notice;
import com.njztsm.app.bean.Result;
import com.njztsm.app.common.DownloadTask;
import com.njztsm.app.common.GestureListener;
import com.njztsm.app.common.StringUtils;
import com.njztsm.app.common.UIHelper;
import com.njztsm.app.common.UpdateManager;
import com.njztsm.app.constant.Constants;
import com.njztsm.app.script.JSinterface;
import com.njztsm.app.widget.NewDataToast;
import com.njztsm.app.widget.PullToRefreshListView;
import com.njztsm.app.widget.ScrollLayout;

/**
 * 应用程序首页
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
@SuppressLint("HandlerLeak")
public class Main extends BaseActivity {

	public static final int QUICKACTION_LOGIN_OR_LOGOUT = 0;
	public static final int QUICKACTION_USERINFO = 1;
	public static final int QUICKACTION_USERREPORT = 2;
	public static final int QUICKACTION_SETTING = 3;
	public static final int QUICKACTION_EXIT = 4;

	private ScrollLayout mScrollLayout;
	private RadioButton[] mButtons;
	private int mViewCount;
	private int mCurSel;

	// private ImageView mHeadLogo;
	// private TextView mHeadTitle;
	// private ProgressBar mHeadProgress;

	private int[] imageIds = new int[] { R.drawable.a, R.drawable.b,
			R.drawable.c, R.drawable.d };
	private View[] views = new View[4];
	private ImageSwitcher imswitcher;
	private GestureDetector mGestureDetector;

	private int curNewsCatalog = Constants.News.CATALOG_LATEST;

	private PullToRefreshListView lvNews;

	private ListViewNewsAdapter lvNewsAdapter;

	private List<News> lvNewsData = new ArrayList<News>();

	private Handler lvNewsHandler;

	private int lvNewsSumData;

	private RadioButton fbNews, fbChart;
	private ImageView fbSetting;

	private Button framebtn_News_lastest;
	private Button framebtn_News_hot;
	private Button framebtn_News_recommend;

	private View lvNews_footer;

	private TextView lvNews_foot_more;

	private ProgressBar lvNews_foot_progress;

	private QuickActionWidget mGrid;// 快捷栏控件

	private boolean isClearNotice = false;
	private int curClearNoticeType = 0;

	private TweetReceiver tweetReceiver;// 动弹发布接收器
	private AppContext appContext;// 全局Context

	private WebView webView;
	private String url = "file:///android_asset/chart.html";
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
		setContentView(R.layout.main);

		// 注册广播接收器
		tweetReceiver = new TweetReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.njztsm.app.action.APP_TWEETPUB");
		registerReceiver(tweetReceiver, filter);

		appContext = (AppContext) getApplication();
		// 网络连接判断
		if (!appContext.isNetworkConnected())
			UIHelper.ToastMessage(this, R.string.network_not_connected);

		// 检查新版本
		if (appContext.isCheckUp()) {
			UpdateManager.getUpdateManager().checkAppUpdate(this, false);
		}

		this.initFootBar();
		this.initPageScroll();
		this.initFrameButton();
		this.initQuickActionGrid();
		this.initFrameListView();
		this.initImswitcher();
		this.initSearch() ;
		// 启动轮询通知信息
		// this.foreachUserNotice();

	}

	protected void initSearch() {
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

	@Override
	protected void onResume() {
		super.onResume();
		if (mViewCount == 0)
			mViewCount = 4;
		if (mCurSel == 0 && !fbNews.isChecked()) {
			fbNews.setChecked(true);
			fbChart.setChecked(false);
		}
		// 读取左右滑动配置
		mScrollLayout.setIsScroll(appContext.isScroll());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(tweetReceiver);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		if (intent.getBooleanExtra("NOTICE", false)) {
			// 查看最新信息
			mScrollLayout.scrollToScreen(3);
		}
	}

	@SuppressWarnings("unchecked")
	private void initImswitcher() {
		imswitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher1);

		imswitcher.setFactory(new ViewFactory() {
			@SuppressWarnings("deprecation")
			@Override
			public View makeView() {
				ImageView imageView = new ImageView(Main.this);
				imageView.setBackgroundColor(0xff0000);
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
				return imageView;
			}
		});
		imswitcher.setImageResource(R.drawable.a);
		View v1 = findViewById(R.id.view1);
		View v2 = findViewById(R.id.view2);
		View v3 = findViewById(R.id.view3);
		View v4 = findViewById(R.id.view4);
		views[0] = v1;
		views[1] = v2;
		views[2] = v3;
		views[3] = v4;

		mGestureDetector = new GestureDetector(this, new GestureListener(
				getApplicationContext(), imswitcher, imageIds, views));
		imswitcher.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mGestureDetector.onTouchEvent(event);
				return true;
			}
		});

		DownloadTask dTask = new DownloadTask(getApplicationContext(),
				imswitcher, imageIds, views);
		dTask.execute(100);
	}

	public class TweetReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(final Context context, Intent intent) {
			int what = intent.getIntExtra("MSG_WHAT", 0);
			if (what == 1) {
				Result res = (Result) intent.getSerializableExtra("RESULT");
				UIHelper.ToastMessage(context, res.getErrorMessage(), 1000);
				if (res.OK()) {
					// 发送通知广播
					if (res.getNotice() != null) {
						UIHelper.sendBroadCast(context, res.getNotice());
					}
				}
			}
		}
	}

	/**
	 * 初始化快捷栏
	 */
	private void initQuickActionGrid() {
		fbSetting = (ImageView) findViewById(R.id.main_footbar_setting);

		mGrid = new QuickActionGrid(this);
		mGrid.addQuickAction(new MyQuickAction(this,
				R.drawable.ic_menu_setting, R.string.main_menu_setting));
		mGrid.addQuickAction(new MyQuickAction(this, R.drawable.ic_menu_exit,
				R.string.main_menu_exit));

		mGrid.setOnQuickActionClickListener(mActionListener);
	}

	/**
	 * 快捷栏item点击事件
	 */
	private OnQuickActionClickListener mActionListener = new OnQuickActionClickListener() {
		public void onQuickActionClicked(QuickActionWidget widget, int position) {
			switch (position) {
			case Constants.CHART_QUICKACTION.SETTING:// 设置
				UIHelper.showChartSetting(Main.this);
				break;
			case Constants.CHART_QUICKACTION.EXIT:// 退出
				UIHelper.Exit(Main.this);
				break;
			}
		}
	};

	/**
	 * 初始化所有ListView
	 */
	private void initFrameListView() {
		// 初始化listview控件
		this.initNewsListView();
		// 加载listview数据
		this.initFrameListViewData();
	}

	/**
	 * 初始化所有ListView数据
	 */
	private void initFrameListViewData() {
		// 初始化Handler
		lvNewsHandler = this.getLvHandler(lvNews, lvNewsAdapter,
				lvNews_foot_more, lvNews_foot_progress, AppContext.PAGE_SIZE);

		// 加载资讯数据
		if (lvNewsData.isEmpty()) {
			loadLvNewsData(curNewsCatalog, 0, lvNewsHandler,
					UIHelper.LISTVIEW_ACTION_INIT);
		}
	}

	/**
	 * 初始化新闻列表
	 */
	private void initNewsListView() {
		lvNewsAdapter = new ListViewNewsAdapter(this, lvNewsData,
				R.layout.news_listitem);
		lvNews_footer = getLayoutInflater().inflate(R.layout.listview_footer,
				null);
		lvNews_foot_more = (TextView) lvNews_footer
				.findViewById(R.id.listview_foot_more);
		lvNews_foot_progress = (ProgressBar) lvNews_footer
				.findViewById(R.id.listview_foot_progress);
		lvNews = (PullToRefreshListView) findViewById(R.id.frame_listview_news);
		lvNews.addFooterView(lvNews_footer);// 添加底部视图 必须在setAdapter前
		lvNews.setAdapter(lvNewsAdapter);
		lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 点击头部、底部栏无效
				if (position == 0 || view == lvNews_footer)
					return;

				News news = null;
				// 判断是否是TextView
				if (view instanceof TextView) {
					news = (News) view.getTag();
				} else {
					TextView tv = (TextView) view
							.findViewById(R.id.news_listitem_title);
					news = (News) tv.getTag();
				}
				if (news == null)
					return;

				// 跳转到新闻详情
				UIHelper.showNewsRedirect(view.getContext(), news);
			}
		});
		lvNews.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvNews.onScrollStateChanged(view, scrollState);

				// 数据为空--不用继续下面代码了
				if (lvNewsData.isEmpty())
					return;

				// 判断是否滚动到底部
				boolean scrollEnd = false;
				try {
					if (view.getPositionForView(lvNews_footer) == view
							.getLastVisiblePosition())
						scrollEnd = true;
				} catch (Exception e) {
					scrollEnd = false;
				}

				int lvDataState = StringUtils.toInt(lvNews.getTag());
				if (scrollEnd && lvDataState == UIHelper.LISTVIEW_DATA_MORE) {
					lvNews.setTag(UIHelper.LISTVIEW_DATA_LOADING);
					lvNews_foot_more.setText(R.string.load_ing);
					lvNews_foot_progress.setVisibility(View.VISIBLE);
					// 当前pageIndex
					int pageIndex = lvNewsSumData / AppContext.PAGE_SIZE;
					loadLvNewsData(curNewsCatalog, pageIndex, lvNewsHandler,
							UIHelper.LISTVIEW_ACTION_SCROLL);
				}
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lvNews.onScroll(view, firstVisibleItem, visibleItemCount,
						totalItemCount);
			}
		});
		lvNews.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			public void onRefresh() {
				loadLvNewsData(curNewsCatalog, 0, lvNewsHandler,
						UIHelper.LISTVIEW_ACTION_REFRESH);
			}
		});
	}

	/**
	 * 初始化底部栏
	 */
	private void initFootBar() {
		fbNews = (RadioButton) findViewById(R.id.main_footbar_news);
		fbChart = (RadioButton) findViewById(R.id.main_footbar_chart);
		fbSetting = (ImageView) findViewById(R.id.main_footbar_setting);

		fbSetting.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mGrid.show(v);
			}
		});

		fbChart.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				UIHelper.showSearch(v.getContext());
			}
		});
	}

	/**
	 * 初始化水平滚动翻页
	 */
	private void initPageScroll() {
		mScrollLayout = (ScrollLayout) findViewById(R.id.main_scrolllayout);
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_linearlayout_footer);

		mViewCount = mScrollLayout.getChildCount();
		mButtons = new RadioButton[mViewCount];

		for (int i = 0; i < mViewCount; i++) {
			mButtons[i] = (RadioButton) linearLayout.getChildAt(i * 2);
			mButtons[i].setTag(i);
			mButtons[i].setChecked(false);
			mButtons[i].setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					int pos = (Integer) (v.getTag());
					// 点击当前项刷新
					if (mCurSel == pos) {
						switch (pos) {
						case 0:// 资讯+博客
							lvNews.clickRefresh();
							break;
						}
					}
					mScrollLayout.snapToScreen(pos);
				}
			});
		}

		// 设置第一显示屏
		mCurSel = 0;
		mButtons[mCurSel].setChecked(true);

		mScrollLayout
				.SetOnViewChangeListener(new ScrollLayout.OnViewChangeListener() {
					public void OnViewChange(int viewIndex) {
						// 切换列表视图-如果列表数据为空：加载数据
						switch (viewIndex) {
						case 0:// 资讯
							if (lvNewsData.isEmpty()) {
								loadLvNewsData(curNewsCatalog, 0,
										lvNewsHandler,
										UIHelper.LISTVIEW_ACTION_INIT);
							}
							break;

						}
						setCurPoint(viewIndex);
					}
				});
	}

	/**
	 * 设置底部栏当前焦点
	 * 
	 * @param index
	 */
	private void setCurPoint(int index) {
		if (index < 0 || index > mViewCount - 1 || mCurSel == index)
			return;

		mButtons[mCurSel].setChecked(false);
		mButtons[index].setChecked(true);
		// mHeadTitle.setText(mHeadTitles[index]);
		mCurSel = index;

		// 头部logo、发帖、发动弹按钮显示
		// if (index == 0) {
		// mHeadLogo.setImageResource(R.drawable.frame_logo_news);
		// }
	}

	/**
	 * 初始化各个主页的按钮(资讯、问答、动弹、动态、留言)
	 */
	private void initFrameButton() {
		// 初始化按钮控件
		framebtn_News_lastest = (Button) findViewById(R.id.frame_btn_news_lastest);
		framebtn_News_hot = (Button) findViewById(R.id.frame_btn_news_hot);
		framebtn_News_recommend = (Button) findViewById(R.id.frame_btn_news_recommend);

		// 设置首选择项
		framebtn_News_lastest.setEnabled(false);
		// 资讯
		framebtn_News_lastest.setOnClickListener(frameNewsBtnClick(
				framebtn_News_lastest, Constants.News.CATALOG_LATEST)); // 最新

		framebtn_News_hot.setOnClickListener(frameNewsBtnClick(
				framebtn_News_hot, Constants.News.CATALOG_HOT)); // 热门
		framebtn_News_recommend.setOnClickListener(frameNewsBtnClick(
				framebtn_News_recommend, Constants.News.CATALOG_RECOMMEND));// 推荐
	}

	private View.OnClickListener frameNewsBtnClick(final Button btn,
			final int catalog) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				if (btn == framebtn_News_lastest) {
					framebtn_News_lastest.setEnabled(false);
				} else {
					framebtn_News_lastest.setEnabled(true);
				}
				if (btn == framebtn_News_hot) {
					framebtn_News_hot.setEnabled(false);
				} else {
					framebtn_News_hot.setEnabled(true);
				}
				if (btn == framebtn_News_recommend) {
					framebtn_News_recommend.setEnabled(false);
				} else {
					framebtn_News_recommend.setEnabled(true);
				}

				curNewsCatalog = catalog;

				loadLvNewsData(curNewsCatalog, 0, lvNewsHandler,
						UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG);
			}
		};
	}

	/**
	 * 获取listview的初始化Handler
	 * 
	 * @param lv
	 * @param adapter
	 * @return
	 */
	private Handler getLvHandler(final PullToRefreshListView lv,
			final BaseAdapter adapter, final TextView more,
			final ProgressBar progress, final int pageSize) {
		return new Handler() {
			@SuppressWarnings("deprecation")
			public void handleMessage(Message msg) {
				if (msg.what >= 0) {
					// listview数据处理
					Notice notice = handleLvData(msg.what, msg.obj, msg.arg2,
							msg.arg1);

					if (msg.what < pageSize) {
						lv.setTag(UIHelper.LISTVIEW_DATA_FULL);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_full);
					} else if (msg.what == pageSize) {
						lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_more);

					}
					// 发送通知广播
					if (notice != null) {
						UIHelper.sendBroadCast(lv.getContext(), notice);
					}
					// 是否清除通知信息
					if (isClearNotice) {
						ClearNotice(curClearNoticeType);
						isClearNotice = false;// 重置
						curClearNoticeType = 0;
					}
				} else if (msg.what == -1) {
					// 有异常--显示加载出错 & 弹出错误消息
					lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
					more.setText(R.string.load_error);
					((AppException) msg.obj).makeToast(Main.this);
				}
				if (adapter.getCount() == 0) {
					lv.setTag(UIHelper.LISTVIEW_DATA_EMPTY);
					more.setText(R.string.load_empty);
				}
				progress.setVisibility(ProgressBar.GONE);
				// mHeadProgress.setVisibility(ProgressBar.GONE);
				if (msg.arg1 == UIHelper.LISTVIEW_ACTION_REFRESH) {
					lv.onRefreshComplete(getString(R.string.pull_to_refresh_update)
							+ new Date().toLocaleString());
					lv.setSelection(0);
				} else if (msg.arg1 == UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG) {
					lv.onRefreshComplete();
					lv.setSelection(0);
				}
			}
		};
	}

	/**
	 * listview数据处理
	 * 
	 * @param what
	 *            数量
	 * @param obj
	 *            数据
	 * @param objtype
	 *            数据类型
	 * @param actiontype
	 *            操作类型
	 * @return notice 通知信息
	 */
	private Notice handleLvData(int what, Object obj, int objtype,
			int actiontype) {
		Notice notice = null;
		switch (actiontype) {
		case UIHelper.LISTVIEW_ACTION_INIT:
		case UIHelper.LISTVIEW_ACTION_REFRESH:
		case UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG:
			int newdata = 0;// 新加载数据-只有刷新动作才会使用到
			switch (objtype) {
			case UIHelper.LISTVIEW_DATATYPE_NEWS:
				NewsList nlist = (NewsList) obj;
				notice = nlist.getNotice();
				lvNewsSumData = what;
				if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
					if (lvNewsData.size() > 0) {
						for (News news1 : nlist.getNewslist()) {
							boolean b = false;
							for (News news2 : lvNewsData) {
								if (news1.getId() == news2.getId()) {
									b = true;
									break;
								}
							}
							if (!b)
								newdata++;
						}
					} else {
						newdata = what;
					}
				}
				lvNewsData.clear();// 先清除原有数据
				lvNewsData.addAll(nlist.getNewslist());
				break;
			case UIHelper.LISTVIEW_DATATYPE_POST:
			}
			if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
				// 提示新加载数据
				if (newdata > 0) {
					NewDataToast
							.makeText(
									this,
									getString(R.string.new_data_toast_message,
											newdata), appContext.isAppSound())
							.show();
				} else {
					NewDataToast.makeText(this,
							getString(R.string.new_data_toast_none), false)
							.show();
				}
			}
			break;
		case UIHelper.LISTVIEW_ACTION_SCROLL:
			switch (objtype) {
			case UIHelper.LISTVIEW_DATATYPE_NEWS:
				NewsList list = (NewsList) obj;
				notice = list.getNotice();
				lvNewsSumData += what;
				if (lvNewsData.size() > 0) {
					for (News news1 : list.getNewslist()) {
						boolean b = false;
						for (News news2 : lvNewsData) {
							if (news1.getId() == news2.getId()) {
								b = true;
								break;
							}
						}
						if (!b)
							lvNewsData.add(news1);
					}
				} else {
					lvNewsData.addAll(list.getNewslist());
				}
				break;
			}
			break;
		}
		return notice;
	}

	/**
	 * 线程加载新闻数据
	 * 
	 * @param catalog
	 *            分类
	 * @param pageIndex
	 *            当前页数
	 * @param handler
	 *            处理器
	 * @param action
	 *            动作标识
	 */
	private void loadLvNewsData(final int catalog, final int pageIndex,
			final Handler handler, final int action) {
		// mHeadProgress.setVisibility(ProgressBar.VISIBLE);
		new Thread() {
			public void run() {
				Message msg = new Message();
				boolean isRefresh = false;
				if (action == UIHelper.LISTVIEW_ACTION_REFRESH
						|| action == UIHelper.LISTVIEW_ACTION_SCROLL)
					isRefresh = true;
				try {
					NewsList list = appContext.getNewsList(catalog, pageIndex,
							isRefresh);
					msg.what = list.getPageSize();
					msg.obj = list;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				msg.arg1 = action;
				msg.arg2 = UIHelper.LISTVIEW_DATATYPE_NEWS;
				if (curNewsCatalog == catalog)
					handler.sendMessage(msg);
			}
		}.start();
	}

	/**
	 * 通知信息处理
	 * 
	 * @param type
	 *            1:@我的信息 2:未读消息 3:评论个数 4:新粉丝个数
	 */
	private void ClearNotice(final int type) {
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1 && msg.obj != null) {
					Result res = (Result) msg.obj;
					if (res.OK() && res.getNotice() != null) {
						UIHelper.sendBroadCast(Main.this, res.getNotice());
					}
				} else {
					((AppException) msg.obj).makeToast(Main.this);
				}
			}
		};
		new Thread() {
			public void run() {
				Message msg = new Message();
				// try {
				// Result res = appContext.noticeClear(uid, type);
				// msg.what = 1;
				// msg.obj = res;
				// } catch (AppException e) {
				// e.printStackTrace();
				// msg.what = -1;
				// msg.obj = e;
				// }
				handler.sendMessage(msg);
			}
		}.start();
	}

	/**
	 * 菜单被显示之前的事件
	 */
	public boolean onPrepareOptionsMenu(Menu menu) {
		UIHelper.showMenuLoginOrLogout(this, menu);
		return true;
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

	/**
	 * 监听返回--是否退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean flag = true;
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 是否退出应用
			UIHelper.Exit(this);
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			// 展示快捷栏&判断是否登录
			mGrid.show(fbSetting, true);
		} else if (keyCode == KeyEvent.KEYCODE_SEARCH) {
			// 展示搜索页
			UIHelper.showSearch(Main.this);
		} else {
			flag = super.onKeyDown(keyCode, event);
		}
		return flag;
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
				try {
					lineChart = appContext.getLineChartList(day, phone);
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
