package com.njztsm.app.ui;

import greendroid.widget.MyQuickAction;
import greendroid.widget.QuickActionGrid;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.njztsm.app.AppContext;
import com.njztsm.app.AppException;
import com.njztsm.app.adapter.ListViewNewsAdapter;
import com.njztsm.app.adapter.ListViewQuestionAdapter;
import com.njztsm.app.bean.News;
import com.njztsm.app.bean.NewsList;
import com.njztsm.app.bean.Notice;
import com.njztsm.app.bean.Post;
import com.njztsm.app.bean.PostList;
import com.njztsm.app.bean.Result;
import com.njztsm.app.common.StringUtils;
import com.njztsm.app.common.UIHelper;
import com.njztsm.app.common.UpdateManager;
import com.njztsm.app.constant.Constants;
import com.njztsm.app.widget.NewDataToast;
import com.njztsm.app.widget.PullToRefreshListView;
import com.njztsm.app.widget.ScrollLayout;

import com.njztsm.app.R;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

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
	private String[] mHeadTitles;
	private int mViewCount;
	private int mCurSel;

	private ImageView mHeadLogo;
	private TextView mHeadTitle;
	private ProgressBar mHeadProgress;
	private ImageButton mHeadPub_post;
	private ImageButton mHeadPub_tweet;

	private int curNewsCatalog = Constants.News.CATALOG_LATEST;
	private int curQuestionCatalog = Constants.Post.CATALOG_ASK;

	private PullToRefreshListView lvNews;
	private PullToRefreshListView lvQuestion;

	private ListViewNewsAdapter lvNewsAdapter;
	private ListViewQuestionAdapter lvQuestionAdapter;

	private List<News> lvNewsData = new ArrayList<News>();
	private List<Post> lvQuestionData = new ArrayList<Post>();

	private Handler lvNewsHandler;
	private Handler lvQuestionHandler;

	private int lvNewsSumData;
	private int lvQuestionSumData;

	private RadioButton fbNews;
	private RadioButton fbQuestion;
	private ImageView fbSetting;

	private Button framebtn_News_lastest;
	private Button framebtn_News_hot;
	private Button framebtn_News_recommend;
	private Button framebtn_Question_ask;
	private Button framebtn_Question_hot;
	private Button framebtn_Question_mine;

	private View lvNews_footer;
	private View lvQuestion_footer;

	private TextView lvNews_foot_more;
	private TextView lvQuestion_foot_more;

	private ProgressBar lvNews_foot_progress;
	private ProgressBar lvQuestion_foot_progress;

	private QuickActionWidget mGrid;// 快捷栏控件

	private boolean isClearNotice = false;
	private int curClearNoticeType = 0;

	private TweetReceiver tweetReceiver;// 动弹发布接收器
	private AppContext appContext;// 全局Context

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
		// 初始化登录
		appContext.initLoginInfo();

		this.initHeadView();
		this.initFootBar();
		this.initPageScroll();
		this.initFrameButton();
		this.initQuickActionGrid();
		this.initFrameListView();

		// 检查新版本
		if (appContext.isCheckUp()) {
			UpdateManager.getUpdateManager().checkAppUpdate(this, false);
		}

		// 启动轮询通知信息
		this.foreachUserNotice();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mViewCount == 0)
			mViewCount = 4;
		if (mCurSel == 0 && !fbNews.isChecked()) {
			fbNews.setChecked(true);
			fbQuestion.setChecked(false);
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
		mGrid = new QuickActionGrid(this);
		mGrid.addQuickAction(new MyQuickAction(this, R.drawable.ic_menu_login,
				R.string.main_menu_login));
		mGrid.addQuickAction(new MyQuickAction(this, R.drawable.ic_menu_myinfo,
				R.string.main_menu_myinfo));
		mGrid.addQuickAction(new MyQuickAction(this,
				R.drawable.ic_menu_picshow, R.string.main_menu_report));
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
			case QUICKACTION_LOGIN_OR_LOGOUT:// 用户登录-注销
				UIHelper.loginOrLogout(Main.this);
				break;
			case QUICKACTION_USERINFO:// 我的资料
				UIHelper.showUserInfo(Main.this);
				break;
			case QUICKACTION_USERREPORT:// 我的报告
				UIHelper.showUserReport(Main.this);
				break;
			case QUICKACTION_SETTING:// 设置
				UIHelper.showSetting(Main.this);
				break;
			case QUICKACTION_EXIT:// 退出
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
		this.initQuestionListView();
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
		lvQuestionHandler = this.getLvHandler(lvQuestion, lvQuestionAdapter,
				lvQuestion_foot_more, lvQuestion_foot_progress,
				AppContext.PAGE_SIZE);

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
	 * 初始化帖子列表
	 */
	private void initQuestionListView() {
		lvQuestionAdapter = new ListViewQuestionAdapter(this, lvQuestionData,
				R.layout.question_listitem);
		lvQuestion_footer = getLayoutInflater().inflate(
				R.layout.listview_footer, null);
		lvQuestion_foot_more = (TextView) lvQuestion_footer
				.findViewById(R.id.listview_foot_more);
		lvQuestion_foot_progress = (ProgressBar) lvQuestion_footer
				.findViewById(R.id.listview_foot_progress);
		lvQuestion = (PullToRefreshListView) findViewById(R.id.frame_listview_question);
		lvQuestion.addFooterView(lvQuestion_footer);// 添加底部视图 必须在setAdapter前
		lvQuestion.setAdapter(lvQuestionAdapter);
		lvQuestion
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// 点击头部、底部栏无效
						if (position == 0 || view == lvQuestion_footer)
							return;

						Post post = null;
						// 判断是否是TextView
						if (view instanceof TextView) {
							post = (Post) view.getTag();
						} else {
							TextView tv = (TextView) view
									.findViewById(R.id.question_listitem_title);
							post = (Post) tv.getTag();
						}
						if (post == null)
							return;

						// 跳转到问答详情
						UIHelper.showQuestionDetail(view.getContext(),
								post.getId());
					}
				});
		lvQuestion.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvQuestion.onScrollStateChanged(view, scrollState);

				// 数据为空--不用继续下面代码了
				if (lvQuestionData.isEmpty())
					return;

				// 判断是否滚动到底部
				boolean scrollEnd = false;
				try {
					if (view.getPositionForView(lvQuestion_footer) == view
							.getLastVisiblePosition())
						scrollEnd = true;
				} catch (Exception e) {
					scrollEnd = false;
				}

				int lvDataState = StringUtils.toInt(lvQuestion.getTag());
				if (scrollEnd && lvDataState == UIHelper.LISTVIEW_DATA_MORE) {
					lvQuestion.setTag(UIHelper.LISTVIEW_DATA_LOADING);
					lvQuestion_foot_more.setText(R.string.load_ing);
					lvQuestion_foot_progress.setVisibility(View.VISIBLE);
					// 当前pageIndex
					int pageIndex = lvQuestionSumData / AppContext.PAGE_SIZE;
					loadLvQuestionData(curQuestionCatalog, pageIndex,
							lvQuestionHandler, UIHelper.LISTVIEW_ACTION_SCROLL);
				}
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lvQuestion.onScroll(view, firstVisibleItem, visibleItemCount,
						totalItemCount);
			}
		});
		lvQuestion
				.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
					public void onRefresh() {
						loadLvQuestionData(curQuestionCatalog, 0,
								lvQuestionHandler,
								UIHelper.LISTVIEW_ACTION_REFRESH);
					}
				});
	}

	/**
	 * 初始化头部视图
	 */
	private void initHeadView() {
		mHeadLogo = (ImageView) findViewById(R.id.main_head_logo);
		mHeadTitle = (TextView) findViewById(R.id.main_head_title);
		mHeadProgress = (ProgressBar) findViewById(R.id.main_head_progress);
		mHeadPub_post = (ImageButton) findViewById(R.id.main_head_pub_post);
		mHeadPub_tweet = (ImageButton) findViewById(R.id.main_head_pub_tweet);

		mHeadPub_post.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				UIHelper.showQuestionPub(v.getContext());
			}
		});
		mHeadPub_tweet.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO UIHelper.showTweetPub(Main.this);
			}
		});
	}

	/**
	 * 初始化底部栏
	 */
	private void initFootBar() {
		fbNews = (RadioButton) findViewById(R.id.main_footbar_news);
		fbQuestion = (RadioButton) findViewById(R.id.main_footbar_question);

		fbSetting = (ImageView) findViewById(R.id.main_footbar_setting);
		fbSetting.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// 展示快捷栏&判断是否登录&是否加载文章图片
				UIHelper.showSettingLoginOrLogout(Main.this,
						mGrid.getQuickAction(0));
				mGrid.show(v);
			}
		});
	}

	/**
	 * 初始化水平滚动翻页
	 */
	private void initPageScroll() {
		mScrollLayout = (ScrollLayout) findViewById(R.id.main_scrolllayout);

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_linearlayout_footer);
		mHeadTitles = getResources().getStringArray(R.array.head_titles);
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
						case 1:// 问答
							lvQuestion.clickRefresh();
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
						case 1:// 问答
							if (lvQuestionData.isEmpty()) {
								loadLvQuestionData(curQuestionCatalog, 0,
										lvQuestionHandler,
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
		mHeadTitle.setText(mHeadTitles[index]);
		mCurSel = index;

		mHeadPub_post.setVisibility(View.GONE);
		mHeadPub_tweet.setVisibility(View.GONE);
		// 头部logo、发帖、发动弹按钮显示
		if (index == 0) {
			mHeadLogo.setImageResource(R.drawable.frame_logo_news);
		} else if (index == 1) {
			mHeadLogo.setImageResource(R.drawable.frame_logo_post);
			mHeadPub_post.setVisibility(View.VISIBLE);
		} else if (index == 2) {
			mHeadLogo.setImageResource(R.drawable.frame_logo_tweet);
			mHeadPub_tweet.setVisibility(View.VISIBLE);
		} else if (index == 3) {
			mHeadLogo.setImageResource(R.drawable.frame_logo_active);
			mHeadPub_tweet.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 初始化各个主页的按钮(资讯、问答、动弹、动态、留言)
	 */
	private void initFrameButton() {
		// 初始化按钮控件
		framebtn_News_lastest = (Button) findViewById(R.id.frame_btn_news_lastest);
		framebtn_News_hot = (Button) findViewById(R.id.frame_btn_news_blog);
		framebtn_News_recommend = (Button) findViewById(R.id.frame_btn_news_recommend);

		framebtn_Question_ask = (Button) findViewById(R.id.frame_btn_question_ask);
		framebtn_Question_hot = (Button) findViewById(R.id.frame_btn_question_hot);
		framebtn_Question_mine = (Button) findViewById(R.id.frame_btn_question_mine);
		// 设置首选择项
		framebtn_News_lastest.setEnabled(false);
		framebtn_Question_ask.setEnabled(false);
		// 资讯
		framebtn_News_lastest.setOnClickListener(frameNewsBtnClick(
				framebtn_News_lastest, Constants.News.CATALOG_LATEST)); // 最新

		framebtn_News_hot.setOnClickListener(frameNewsBtnClick(
				framebtn_News_hot, Constants.News.CATALOG_HOT)); // 热门
		framebtn_News_recommend.setOnClickListener(frameNewsBtnClick(
				framebtn_News_recommend, Constants.News.CATALOG_RECOMMEND));// 推荐
		// 问答
		framebtn_Question_ask.setOnClickListener(frameQuestionBtnClick(
				framebtn_Question_ask, Constants.Post.CATALOG_ASK));
		framebtn_Question_hot.setOnClickListener(frameQuestionBtnClick(
				framebtn_Question_hot, Constants.Post.CATALOG_SHARE));
		framebtn_Question_mine.setOnClickListener(frameQuestionBtnClick(
				framebtn_Question_mine, Constants.Post.CATALOG_OTHER));
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

	private View.OnClickListener frameQuestionBtnClick(final Button btn,
			final int catalog) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				if (btn == framebtn_Question_ask)
					framebtn_Question_ask.setEnabled(false);
				else
					framebtn_Question_ask.setEnabled(true);
				if (btn == framebtn_Question_hot)
					framebtn_Question_hot.setEnabled(false);
				else
					framebtn_Question_hot.setEnabled(true);
				if (btn == framebtn_Question_mine) {
					// 判断登录
					int uid = appContext.getLoginUid();
					if (uid == 0) {
						UIHelper.showLoginDialog(Main.this);
						return;
					}
					framebtn_Question_mine.setEnabled(false);
				} else
					framebtn_Question_mine.setEnabled(true);

				curQuestionCatalog = catalog;
				loadLvQuestionData(curQuestionCatalog, 0, lvQuestionHandler,
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
				mHeadProgress.setVisibility(ProgressBar.GONE);
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
				PostList plist = (PostList) obj;
				notice = plist.getNotice();
				lvQuestionSumData = what;
				if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
					if (lvQuestionData.size() > 0) {
						for (Post post1 : plist.getPostlist()) {
							boolean b = false;
							for (Post post2 : lvQuestionData) {
								if (post1.getId() == post2.getId()) {
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
				lvQuestionData.clear();// 先清除原有数据
				lvQuestionData.addAll(plist.getPostlist());
				break;
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
			case UIHelper.LISTVIEW_DATATYPE_POST:
				PostList plist = (PostList) obj;
				notice = plist.getNotice();
				lvQuestionSumData += what;
				if (lvQuestionData.size() > 0) {
					for (Post post1 : plist.getPostlist()) {
						boolean b = false;
						for (Post post2 : lvQuestionData) {
							if (post1.getId() == post2.getId()) {
								b = true;
								break;
							}
						}
						if (!b)
							lvQuestionData.add(post1);
					}
				} else {
					lvQuestionData.addAll(plist.getPostlist());
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
		mHeadProgress.setVisibility(ProgressBar.VISIBLE);
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
	 * 线程加载帖子数据
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
	private void loadLvQuestionData(final int catalog, final int pageIndex,
			final Handler handler, final int action) {
		mHeadProgress.setVisibility(ProgressBar.VISIBLE);
		new Thread() {
			public void run() {
				Message msg = new Message();
				boolean isRefresh = false;
				if (action == UIHelper.LISTVIEW_ACTION_REFRESH
						|| action == UIHelper.LISTVIEW_ACTION_SCROLL)
					isRefresh = true;
				try {
					PostList list = appContext.getPostList(catalog, pageIndex,
							isRefresh);
					msg.what = list.getPageSize();
					msg.obj = list;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				msg.arg1 = action;
				msg.arg2 = UIHelper.LISTVIEW_DATATYPE_POST;
				if (curQuestionCatalog == catalog)
					handler.sendMessage(msg);
			}
		}.start();
	}

	/**
	 * 轮询通知信息
	 */
	private void foreachUserNotice() {
		final int uid = appContext.getLoginUid();
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					UIHelper.sendBroadCast(Main.this, (Notice) msg.obj);
				}
				foreachUserNotice();// 回调
			}
		};
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					sleep(60 * 1000);
					if (uid > 0) {
						Notice notice = appContext.getUserNotice(uid);
						msg.what = 1;
						msg.obj = notice;
					} else {
						msg.what = 0;
					}
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = -1;
				} catch (Exception e) {
					e.printStackTrace();
					msg.what = -1;
				}
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
		final int uid = appContext.getLoginUid();
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
				try {
					Result res = appContext.noticeClear(uid, type);
					msg.what = 1;
					msg.obj = res;
				} catch (AppException e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
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
	public boolean onOptionsItemSelected(MenuItem item) {
		int item_id = item.getItemId();
		switch (item_id) {
		case R.id.main_menu_user:
			UIHelper.loginOrLogout(this);
			break;
		case R.id.main_menu_about:
			UIHelper.showAbout(this);
			break;
		case R.id.main_menu_setting:
			UIHelper.showSetting(this);
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean flag = true;
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 是否退出应用
			UIHelper.Exit(this);
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			// 展示快捷栏&判断是否登录
			UIHelper.showSettingLoginOrLogout(Main.this,
					mGrid.getQuickAction(0));
			mGrid.show(fbSetting, true);
		} else if (keyCode == KeyEvent.KEYCODE_SEARCH) {
			// 展示搜索页
		} else {
			flag = super.onKeyDown(keyCode, event);
		}
		return flag;
	}
}
