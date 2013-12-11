package com.njztsm.app.ui;

import com.njztsm.app.AppContext;
import com.njztsm.app.AppException;
import com.njztsm.app.bean.News;
import com.njztsm.app.bean.Notice;
import com.njztsm.app.bean.Relative;
import com.njztsm.app.bean.URLs;
import com.njztsm.app.common.StringUtils;
import com.njztsm.app.common.UIHelper;

import com.njztsm.app.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 新闻详情
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
@SuppressLint("HandlerLeak")
public class NewsDetail extends BaseActivity {

	private FrameLayout mHeader;
	private ImageView mHome;
	private ImageView mRefresh;
	private ProgressBar mProgressbar;
	private ScrollView mScrollView;
	private AppContext appContext;

	private TextView mTitle;
	private TextView mAuthor;
	private TextView mPubDate;
	private TextView mCommentCount;
	private TextView vshare;
	private ImageView mShare;

	private WebView mWebView;
	private Handler mHandler;
	private News newsDetail;
	private int newsId;

	private final static int DATA_LOAD_ING = 0x001;
	private final static int DATA_LOAD_COMPLETE = 0x002;
	private final static int DATA_LOAD_FAIL = 0x003;

	private GestureDetector gd;
	private boolean isFullScreen;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_detail);

		this.initView();
		this.initData();

		// 注册双击全屏事件
		this.regOnDoubleEvent();
	}

	// 初始化视图控件
	private void initView() {
		newsId = getIntent().getIntExtra("news_id", 0);

		appContext = (AppContext) getApplication();
		mHeader = (FrameLayout) findViewById(R.id.news_detail_header);
		mHome = (ImageView) findViewById(R.id.news_detail_home);
		mShare = (ImageView) findViewById(R.id.news_detail_share);
		mRefresh = (ImageView) findViewById(R.id.news_detail_refresh);
		mProgressbar = (ProgressBar) findViewById(R.id.news_detail_head_progress);
		mScrollView = (ScrollView) findViewById(R.id.news_detail_scrollview);

		mTitle = (TextView) findViewById(R.id.news_detail_title);
		mAuthor = (TextView) findViewById(R.id.news_detail_author);
		mPubDate = (TextView) findViewById(R.id.news_detail_date);
		mCommentCount = (TextView) findViewById(R.id.news_detail_commentcount);
		vshare = (TextView) findViewById(R.id.news_detail_vshare);

		mWebView = (WebView) findViewById(R.id.news_detail_webview);
		mWebView.getSettings().setJavaScriptEnabled(false);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setDefaultFontSize(15);

		mHome.setOnClickListener(homeClickListener);
		mRefresh.setOnClickListener(refreshClickListener);
		mAuthor.setOnClickListener(authorClickListener);
		mShare.setOnClickListener(shareClickListener);
		vshare.setOnClickListener(shareClickListener);

	}

	// 初始化控件数据
	private void initData() {
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					headButtonSwitch(DATA_LOAD_COMPLETE);

					mTitle.setText(newsDetail.getTitle());
					mAuthor.setText(newsDetail.getAuthor());
					mPubDate.setText(StringUtils.friendly_time(newsDetail
							.getPubDate()));
					mCommentCount.setText(String.valueOf(newsDetail
							.getCommentCount()));

					String body = UIHelper.WEB_STYLE + newsDetail.getBody();
					// 读取用户设置：是否加载文章图片--默认有wifi下始终加载图片
					boolean isLoadImage;

					if (AppContext.NETTYPE_WIFI == appContext.getNetworkType()) {
						isLoadImage = true;
					} else {
						isLoadImage = appContext.isLoadImage();
					}
					if (isLoadImage) {
						// 过滤掉 img标签的width,height属性
						body = body.replaceAll(
								"(<img[^>]*?)\\s+width\\s*=\\s*\\S+", "$1");
						body = body.replaceAll(
								"(<img[^>]*?)\\s+height\\s*=\\s*\\S+", "$1");
					} else {
						// 过滤掉 img标签
						body = body.replaceAll("<\\s*img\\s+([^>]*)\\s*>", "");
					}

					// 更多关于***软件的信息
					String softwareName = newsDetail.getSoftwareName();
					String softwareLink = newsDetail.getSoftwareLink();
					if (!StringUtils.isEmpty(softwareName)
							&& !StringUtils.isEmpty(softwareLink))
						body += String
								.format("<div id='oschina_software' style='margin-top:8px;color:#FF0000;font-weight:bold'>更多关于:&nbsp;<a href='%s'>%s</a>&nbsp;的详细信息</div>",
										softwareLink, softwareName);

					// 相关新闻 TODO
					if (newsDetail.getRelatives().size() > 0) {
						String strRelative = "";
						for (Relative relative : newsDetail.getRelatives()) {
							strRelative += String
									.format("<a href='%s' style='text-decoration:none'>%s</a><p/>",
											relative.url, relative.title);
						}
						body += String.format(
								"<p/><hr/><b>相关资讯</b><div><p/>%s</div>",
								strRelative);
					}

					body += "<div style='margin-bottom: 80px'/>";

					mWebView.loadDataWithBaseURL(null, body, "text/html",
							"utf-8", null);
					mWebView.setWebViewClient(UIHelper.getWebViewClient());

					// 发送通知广播
					if (msg.obj != null) {
						UIHelper.sendBroadCast(NewsDetail.this,
								(Notice) msg.obj);
					}
				} else if (msg.what == 0) {
					headButtonSwitch(DATA_LOAD_FAIL);

					UIHelper.ToastMessage(NewsDetail.this,
							R.string.msg_load_is_null);
				} else if (msg.what == -1 && msg.obj != null) {
					headButtonSwitch(DATA_LOAD_FAIL);

					((AppException) msg.obj).makeToast(NewsDetail.this);
				}
			}
		};

		initData(newsId, false);
	}

	private void initData(final int news_id, final boolean isRefresh) {
		headButtonSwitch(DATA_LOAD_ING);

		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					newsDetail = appContext.getNews(news_id, isRefresh);
					msg.what = (newsDetail != null && newsDetail.getId() > 0) ? 1
							: 0;
					msg.obj = (newsDetail != null) ? newsDetail.getNotice()
							: null;// 通知信息
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				mHandler.sendMessage(msg);
			}
		}.start();
	}

	/**
	 * 头部按钮展示
	 * 
	 * @param type
	 */
	private void headButtonSwitch(int type) {
		switch (type) {
		case DATA_LOAD_ING:
			mScrollView.setVisibility(View.GONE);
			mProgressbar.setVisibility(View.VISIBLE);
			mRefresh.setVisibility(View.GONE);
			break;
		case DATA_LOAD_COMPLETE:
			mScrollView.setVisibility(View.VISIBLE);
			mProgressbar.setVisibility(View.GONE);
			mRefresh.setVisibility(View.VISIBLE);
			break;
		case DATA_LOAD_FAIL:
			mScrollView.setVisibility(View.GONE);
			mProgressbar.setVisibility(View.GONE);
			mRefresh.setVisibility(View.VISIBLE);
			break;
		}
	}

	private View.OnClickListener homeClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			UIHelper.showHome(NewsDetail.this);
		}
	};

	private View.OnClickListener refreshClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			initData(newsId, true);
			// loadLvCommentData(curId, curCatalog, 0, mCommentHandler,
			// UIHelper.LISTVIEW_ACTION_REFRESH);
		}
	};

	private View.OnClickListener authorClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			// TODO UIHelper.showUserCenter(v.getContext(),
			// newsDetail.getAuthorId(), newsDetail.getAuthor());
		}
	};

	private View.OnClickListener shareClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			if (newsDetail == null) {
				UIHelper.ToastMessage(v.getContext(),
						R.string.msg_read_detail_fail);
				return;
			}
			String url = newsDetail.getUrl();
			url = StringUtils.isEmpty(url) ? URLs.BASE_API_HOST : url;

			// 分享到
			UIHelper.showShareDialog(NewsDetail.this, newsDetail.getTitle(),
					url);
		}
	};

	/**
	 * 注册双击全屏事件
	 */
	private void regOnDoubleEvent() {
		gd = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onDoubleTap(MotionEvent e) {
						isFullScreen = !isFullScreen;
						if (!isFullScreen) {
							WindowManager.LayoutParams params = getWindow()
									.getAttributes();
							params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
							getWindow().setAttributes(params);
							getWindow()
									.clearFlags(
											WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
							mHeader.setVisibility(View.VISIBLE);
						} else {
							WindowManager.LayoutParams params = getWindow()
									.getAttributes();
							params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
							getWindow().setAttributes(params);
							getWindow()
									.addFlags(
											WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
							mHeader.setVisibility(View.GONE);
						}
						return true;
					}
				});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		gd.onTouchEvent(event);
		return super.dispatchTouchEvent(event);
	}
}
