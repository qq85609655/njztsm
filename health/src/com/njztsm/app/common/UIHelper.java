package com.njztsm.app.common;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.njztsm.app.AppConfig;
import com.njztsm.app.AppContext;
import com.njztsm.app.AppException;
import com.njztsm.app.AppManager;
import com.njztsm.app.adapter.GridViewFaceAdapter;
import com.njztsm.app.api.ApiClient;
import com.njztsm.app.bean.AccessInfo;
import com.njztsm.app.bean.News;
import com.njztsm.app.bean.Notice;
import com.njztsm.app.bean.URLs;
import com.njztsm.app.constant.Constants;
import com.njztsm.app.ui.About;
import com.njztsm.app.ui.ChartSetting;
import com.njztsm.app.ui.ImageDialog;
import com.njztsm.app.ui.ImageZoomDialog;
import com.njztsm.app.ui.Main;
import com.njztsm.app.ui.Search;
import com.njztsm.app.ui.NewsDetail;
import com.njztsm.app.ui.Welcome;
import com.njztsm.app.ui.WelcomeDoor;

import greendroid.widget.MyQuickAction;
import greendroid.widget.QuickAction;
import com.njztsm.app.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 应用程序UI工具包：封装UI相关的一些操作
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
@TargetApi(Build.VERSION_CODES.ECLAIR)
public class UIHelper {

	public final static int LISTVIEW_ACTION_INIT = 0x01;
	public final static int LISTVIEW_ACTION_REFRESH = 0x02;
	public final static int LISTVIEW_ACTION_SCROLL = 0x03;
	public final static int LISTVIEW_ACTION_CHANGE_CATALOG = 0x04;

	public final static int LISTVIEW_DATA_MORE = 0x01;
	public final static int LISTVIEW_DATA_LOADING = 0x02;
	public final static int LISTVIEW_DATA_FULL = 0x03;
	public final static int LISTVIEW_DATA_EMPTY = 0x04;

	public final static int LISTVIEW_DATATYPE_NEWS = 0x01;
	public final static int LISTVIEW_DATATYPE_BLOG = 0x02;
	public final static int LISTVIEW_DATATYPE_POST = 0x03;

	public final static int REQUEST_CODE_FOR_RESULT = 0x01;
	public final static int REQUEST_CODE_FOR_REPLY = 0x02;

	/** 表情图片匹配 */
	private static Pattern facePattern = Pattern
			.compile("\\[{1}([0-9]\\d*)\\]{1}");

	/** 全局web样式 */
	public final static String WEB_STYLE = "<style>* {font-size:16px;line-height:20px;} p {color:#333;} a {color:#3E62A6;} img {max-width:310px;} "
			+ "img.alignleft {float:left;max-width:120px;margin:0 10px 5px 0;border:1px solid #ccc;background:#fff;padding:2px;} "
			+ "pre {font-size:9pt;line-height:12pt;font-family:Courier New,Arial;border:1px solid #ddd;border-left:5px solid #6CE26C;background:#f6f6f6;padding:5px;} "
			+ "a.tag {font-size:15px;text-decoration:none;background-color:#bbd6f3;border-bottom:2px solid #3E6D8E;border-right:2px solid #7F9FB6;color:#284a7b;margin:2px 2px 2px 0;padding:2px 4px;white-space:nowrap;}</style>";

	/**
	 * 显示首页
	 * 
	 * @param activity
	 */
	public static void showHome(Activity activity) {
		Intent intent = new Intent(activity, Main.class);
		activity.startActivity(intent);
		activity.finish();
	}

	/**
	 * 调用系统安装了的应用分享
	 * 
	 * @param context
	 * @param title
	 * @param url
	 */
	public static void showShareMore(Activity context, final String title,
			final String url) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "分享：" + title);
		intent.putExtra(Intent.EXTRA_TEXT, title + " " + url);
		context.startActivity(Intent.createChooser(intent, "选择分享"));
	}

	/**
	 * 分享到'新浪微博'或'腾讯微博'的对话框
	 * 
	 * @param context
	 *            当前Activity
	 * @param title
	 *            分享的标题
	 * @param url
	 *            分享的链接
	 */
	public static void showShareDialog(final Activity context,
			final String title, final String url) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(android.R.drawable.btn_star);
		builder.setTitle(context.getString(R.string.share));
		builder.setItems(R.array.app_share_items,
				new DialogInterface.OnClickListener() {
					AppConfig cfgHelper = AppConfig.getAppConfig(context);
					AccessInfo access = cfgHelper.getAccessInfo();

					public void onClick(DialogInterface arg0, int arg1) {
						switch (arg1) {
						case 0:// 新浪微博
								// 分享的内容
							final String shareMessage = title + " " + url;
							// 初始化微博
							if (SinaWeiboHelper.isWeiboNull()) {
								SinaWeiboHelper.initWeibo();
							}
							// 判断之前是否登陆过
							if (access != null) {
								SinaWeiboHelper.progressDialog = new ProgressDialog(
										context);
								SinaWeiboHelper.progressDialog
										.setProgressStyle(ProgressDialog.STYLE_SPINNER);
								SinaWeiboHelper.progressDialog
										.setMessage(context
												.getString(R.string.sharing));
								SinaWeiboHelper.progressDialog
										.setCancelable(true);
								SinaWeiboHelper.progressDialog.show();
								new Thread() {
									public void run() {
										SinaWeiboHelper.setAccessToken(
												access.getAccessToken(),
												access.getAccessSecret(),
												access.getExpiresIn());
										SinaWeiboHelper.shareMessage(context,
												shareMessage);
									}
								}.start();
							} else {
								SinaWeiboHelper
										.authorize(context, shareMessage);
							}
							break;
						case 1:// 腾讯微博
							QQWeiboHelper.shareToQQ(context, title, url);
							break;
						case 2:// 更多
							showShareMore(context, title, url);
							break;
						}
					}
				});
		builder.create().show();
	}

	/**
	 * 收藏操作选择框
	 * 
	 * @param context
	 * @param thread
	 */
	public static void showFavoriteOptionDialog(final Activity context,
			final Thread thread) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(R.drawable.ic_dialog_menu);
		builder.setTitle(context.getString(R.string.select));
		builder.setItems(R.array.favorite_options,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						switch (arg1) {
						case 0:// 删除
							thread.start();
							break;
						}
					}
				});
		builder.create().show();
	}

	/**
	 * 博客列表操作
	 * 
	 * @param context
	 * @param thread
	 */
	public static void showBlogOptionDialog(final Context context,
			final Thread thread) {
		new AlertDialog.Builder(context)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setTitle(context.getString(R.string.delete_blog))
				.setPositiveButton(R.string.sure,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								if (thread != null)
									thread.start();
								else
									ToastMessage(context,
											R.string.msg_noaccess_delete);
								dialog.dismiss();
							}
						})
				.setNegativeButton(R.string.cancle,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).create().show();
	}

	/**
	 * 动弹操作选择框
	 * 
	 * @param context
	 * @param thread
	 */
	public static void showTweetOptionDialog(final Context context,
			final Thread thread) {
		new AlertDialog.Builder(context)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setTitle(context.getString(R.string.delete_tweet))
				.setPositiveButton(R.string.sure,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								if (thread != null)
									thread.start();
								else
									ToastMessage(context,
											R.string.msg_noaccess_delete);
								dialog.dismiss();
							}
						})
				.setNegativeButton(R.string.cancle,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).create().show();
	}

	/**
	 * 显示图片对话框
	 * 
	 * @param context
	 * @param imgUrl
	 */
	public static void showImageDialog(Context context, String imgUrl) {
		Intent intent = new Intent(context, ImageDialog.class);
		intent.putExtra("img_url", imgUrl);
		context.startActivity(intent);
	}

	public static void showImageZoomDialog(Context context, String imgUrl) {
		Intent intent = new Intent(context, ImageZoomDialog.class);
		intent.putExtra("img_url", imgUrl);
		context.startActivity(intent);
	}

	/**
	 * 显示chart设置界面
	 * 
	 * @param context
	 */
	public static void showChartSetting(Context context) {
		Intent intent = new Intent(context, ChartSetting.class);
		context.startActivity(intent);
	}

	/**
	 * 加载显示图片
	 * 
	 * @param imgFace
	 * @param faceURL
	 * @param errMsg
	 */
	public static void showLoadImage(final ImageView imgView,
			final String imgURL, final String errMsg) {
		// 读取本地图片
		if (StringUtils.isEmpty(imgURL) || imgURL.endsWith("portrait.gif")) {
			Bitmap bmp = BitmapFactory.decodeResource(imgView.getResources(),
					R.drawable.widget_dface);
			imgView.setImageBitmap(bmp);
			return;
		}

		// 是否有缓存图片
		final String filename = FileUtils.getFileName(imgURL);
		// Environment.getExternalStorageDirectory();返回/sdcard
		String filepath = imgView.getContext().getFilesDir() + File.separator
				+ filename;
		File file = new File(filepath);
		if (file.exists()) {
			Bitmap bmp = ImageUtils.getBitmap(imgView.getContext(), filename);
			imgView.setImageBitmap(bmp);
			return;
		}

		// 从网络获取&写入图片缓存
		String _errMsg = imgView.getContext().getString(
				R.string.msg_load_image_fail);
		if (!StringUtils.isEmpty(errMsg))
			_errMsg = errMsg;
		final String ErrMsg = _errMsg;
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1 && msg.obj != null) {
					imgView.setImageBitmap((Bitmap) msg.obj);
					try {
						// 写图片缓存
						ImageUtils.saveImage(imgView.getContext(), filename,
								(Bitmap) msg.obj);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					ToastMessage(imgView.getContext(), ErrMsg);
				}
			}
		};
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					Bitmap bmp = ApiClient.getNetBitmap(imgURL);
					msg.what = 1;
					msg.obj = bmp;
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
	 * 打开浏览器
	 * 
	 * @param context
	 * @param url
	 */
	public static void openBrowser(Context context, String url) {
		try {
			Uri uri = Uri.parse(url);
			Intent it = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(it);
		} catch (Exception e) {
			e.printStackTrace();
			ToastMessage(context, "无法浏览此网页", 500);
		}
	}

	/**
	 * url跳转
	 * 
	 * @param context
	 * @param url
	 */
	public static void showUrlRedirect(Context context, String url) {
		openBrowser(context, url);
	}

	/**
	 * 获取webviewClient对象
	 * 
	 * @return
	 */
	public static WebViewClient getWebViewClient() {
		return new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				showUrlRedirect(view.getContext(), url);
				return true;
			}
		};
	}

	/**
	 * 获取TextWatcher对象
	 * 
	 * @param context
	 * @param tmlKey
	 * @return
	 */
	public static TextWatcher getTextWatcher(final Activity context,
			final String temlKey) {
		return new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// 保存当前EditText正在编辑的内容
				((AppContext) context.getApplication()).setProperty(temlKey,
						s.toString());
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		};
	}

	/**
	 * 编辑器显示保存的草稿
	 * 
	 * @param context
	 * @param editer
	 * @param temlKey
	 */
	public static void showTempEditContent(Activity context, EditText editer,
			String temlKey) {
		String tempContent = ((AppContext) context.getApplication())
				.getProperty(temlKey);
		if (!StringUtils.isEmpty(tempContent)) {
			SpannableStringBuilder builder = parseFaceByText(context,
					tempContent);
			editer.setText(builder);
			editer.setSelection(tempContent.length());// 设置光标位置
		}
	}

	/**
	 * 将[12]之类的字符串替换为表情
	 * 
	 * @param context
	 * @param content
	 */
	public static SpannableStringBuilder parseFaceByText(Context context,
			String content) {
		SpannableStringBuilder builder = new SpannableStringBuilder(content);
		Matcher matcher = facePattern.matcher(content);
		while (matcher.find()) {
			// 使用正则表达式找出其中的数字
			int position = StringUtils.toInt(matcher.group(1));
			int resId = 0;
			try {
				if (position > 65 && position < 102)
					position = position - 1;
				else if (position > 102)
					position = position - 2;
				resId = GridViewFaceAdapter.getImageIds()[position];
				Drawable d = context.getResources().getDrawable(resId);
				d.setBounds(0, 0, 35, 35);// 设置表情图片的显示大小
				ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
				builder.setSpan(span, matcher.start(), matcher.end(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			} catch (Exception e) {
			}
		}
		return builder;
	}

	/**
	 * 清除文字
	 * 
	 * @param cont
	 * @param editer
	 */
	public static void showClearWordsDialog(final Context cont,
			final EditText editer, final TextView numwords) {
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setTitle(R.string.clearwords);
		builder.setPositiveButton(R.string.sure,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 清除文字
						editer.setText("");
						numwords.setText("160");
					}
				});
		builder.setNegativeButton(R.string.cancle,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.show();
	}

	/**
	 * 发送通知广播
	 * 
	 * @param context
	 * @param notice
	 */
	public static void sendBroadCast(Context context, Notice notice) {
		if (!((AppContext) context.getApplicationContext()).isLogin()
				|| notice == null)
			return;
		Intent intent = new Intent("com.njztsm.app.action.APPWIDGET_UPDATE");
		intent.putExtra("atmeCount", notice.getAtmeCount());
		intent.putExtra("msgCount", notice.getMsgCount());
		intent.putExtra("reviewCount", notice.getReviewCount());
		intent.putExtra("newFansCount", notice.getNewFansCount());
		context.sendBroadcast(intent);
	}

	/**
	 * 组合动态的动作文本
	 * 
	 * @param objecttype
	 * @param objectcatalog
	 * @param objecttitle
	 * @return
	 */
	public static SpannableString parseActiveAction(String author,
			int objecttype, int objectcatalog, String objecttitle) {
		String title = "";
		int start = 0;
		int end = 0;
		if (objecttype == 32 && objectcatalog == 0) {
			title = "加入了开源中国";
		} else if (objecttype == 1 && objectcatalog == 0) {
			title = "添加了开源项目 " + objecttitle;
		} else if (objecttype == 2 && objectcatalog == 1) {
			title = "在讨论区提问：" + objecttitle;
		} else if (objecttype == 2 && objectcatalog == 2) {
			title = "发表了新话题：" + objecttitle;
		} else if (objecttype == 3 && objectcatalog == 0) {
			title = "发表了博客 " + objecttitle;
		} else if (objecttype == 4 && objectcatalog == 0) {
			title = "发表一篇新闻 " + objecttitle;
		} else if (objecttype == 5 && objectcatalog == 0) {
			title = "分享了一段代码 " + objecttitle;
		} else if (objecttype == 6 && objectcatalog == 0) {
			title = "发布了一个职位：" + objecttitle;
		} else if (objecttype == 16 && objectcatalog == 0) {
			title = "在新闻 " + objecttitle + " 发表评论";
		} else if (objecttype == 17 && objectcatalog == 1) {
			title = "回答了问题：" + objecttitle;
		} else if (objecttype == 17 && objectcatalog == 2) {
			title = "回复了话题：" + objecttitle;
		} else if (objecttype == 17 && objectcatalog == 3) {
			title = "在 " + objecttitle + " 对回帖发表评论";
		} else if (objecttype == 18 && objectcatalog == 0) {
			title = "在博客 " + objecttitle + " 发表评论";
		} else if (objecttype == 19 && objectcatalog == 0) {
			title = "在代码 " + objecttitle + " 发表评论";
		} else if (objecttype == 20 && objectcatalog == 0) {
			title = "在职位 " + objecttitle + " 发表评论";
		} else if (objecttype == 101 && objectcatalog == 0) {
			title = "回复了动态：" + objecttitle;
		} else if (objecttype == 100) {
			title = "更新了动态";
		}
		title = author + " " + title;
		SpannableString sp = new SpannableString(title);
		// 设置用户名字体大小、加粗、高亮
		sp.setSpan(new AbsoluteSizeSpan(14, true), 0, author.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
				author.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new ForegroundColorSpan(Color.parseColor("#0e5986")), 0,
				author.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		// 设置标题字体大小、高亮
		if (!StringUtils.isEmpty(objecttitle)) {
			start = title.indexOf(objecttitle);
			if (objecttitle.length() > 0 && start > 0) {
				end = start + objecttitle.length();
				sp.setSpan(new AbsoluteSizeSpan(14, true), start, end,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				sp.setSpan(
						new ForegroundColorSpan(Color.parseColor("#0e5986")),
						start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		return sp;
	}

	/**
	 * 组合动态的回复文本
	 * 
	 * @param name
	 * @param body
	 * @return
	 */
	public static SpannableString parseActiveReply(String name, String body) {
		SpannableString sp = new SpannableString(name + "：" + body);
		// 设置用户名字体加粗、高亮
		sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
				name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new ForegroundColorSpan(Color.parseColor("#0e5986")), 0,
				name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return sp;
	}

	/**
	 * 组合消息文本
	 * 
	 * @param name
	 * @param body
	 * @return
	 */
	public static SpannableString parseMessageSpan(String name, String body,
			String action) {
		SpannableString sp = null;
		int start = 0;
		int end = 0;
		if (StringUtils.isEmpty(action)) {
			sp = new SpannableString(name + "：" + body);
			end = name.length();
		} else {
			sp = new SpannableString(action + name + "：" + body);
			start = action.length();
			end = start + name.length();
		}
		// 设置用户名字体加粗、高亮
		sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), start, end,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new ForegroundColorSpan(Color.parseColor("#0e5986")), start,
				end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return sp;
	}

	/**
	 * 组合回复引用文本
	 * 
	 * @param name
	 * @param body
	 * @return
	 */
	public static SpannableString parseQuoteSpan(String name, String body) {
		SpannableString sp = new SpannableString("回复：" + name + "\n" + body);
		// 设置用户名字体加粗、高亮
		sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 3,
				3 + name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new ForegroundColorSpan(Color.parseColor("#0e5986")), 3,
				3 + name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return sp;
	}

	/**
	 * 弹出Toast消息
	 * 
	 * @param msg
	 */
	public static void ToastMessage(Context cont, String msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, int msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, String msg, int time) {
		Toast.makeText(cont, msg, time).show();
	}

	/**
	 * 点击返回监听事件
	 * 
	 * @param activity
	 * @return
	 */
	public static View.OnClickListener finish(final Activity activity) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				activity.finish();
			}
		};
	}

	/**
	 * 显示关于我们
	 * 
	 * @param context
	 */
	public static void showAbout(Context context) {
		Intent intent = new Intent(context, About.class);
		context.startActivity(intent);
	}

	/**
	 * 显示更新內容
	 * 
	 * @param context
	 */
	public static void showWelcome(Context context) {
		Intent intent = new Intent(context, Welcome.class);
		context.startActivity(intent);
	}

	/**
	 * 显示更新內容后跳转至主页
	 * 
	 * @param context
	 */
	public static void showWelcomeDoor(Context context) {
		Intent intent = new Intent(context, WelcomeDoor.class);
		context.startActivity(intent);
	}

	/**
	 * 菜单显示登录或登出
	 * 
	 * @param activity
	 * @param menu
	 */
	public static void showMenuLoginOrLogout(Activity activity, Menu menu) {
		if (((AppContext) activity.getApplication()).isLogin()) {
			menu.findItem(R.id.main_menu_user).setTitle(
					R.string.main_menu_logout);
			menu.findItem(R.id.main_menu_user).setIcon(
					R.drawable.ic_menu_logout);
		} else {
			menu.findItem(R.id.main_menu_user).setTitle(
					R.string.main_menu_login);
			menu.findItem(R.id.main_menu_user)
					.setIcon(R.drawable.ic_menu_login);
		}
	}

	/**
	 * 快捷栏显示登录与登出
	 * 
	 * @param activity
	 * @param qa
	 */
	public static void showSettingLoginOrLogout(Activity activity,
			QuickAction qa) {
		if (((AppContext) activity.getApplication()).isLogin()) {
			qa.setIcon(MyQuickAction.buildDrawable(activity,
					R.drawable.ic_menu_logout));
			qa.setTitle(activity.getString(R.string.main_menu_logout));
		} else {
			qa.setIcon(MyQuickAction.buildDrawable(activity,
					R.drawable.ic_menu_login));
			qa.setTitle(activity.getString(R.string.main_menu_login));
		}
	}

	/**
	 * 快捷栏是否显示文章图片
	 * 
	 * @param activity
	 * @param qa
	 */
	public static void showSettingIsLoadImage(Activity activity, QuickAction qa) {
		if (((AppContext) activity.getApplication()).isLoadImage()) {
			qa.setIcon(MyQuickAction.buildDrawable(activity,
					R.drawable.ic_menu_picnoshow));
			qa.setTitle(activity.getString(R.string.main_menu_picnoshow));
		} else {
			qa.setIcon(MyQuickAction.buildDrawable(activity,
					R.drawable.ic_menu_picshow));
			qa.setTitle(activity.getString(R.string.main_menu_picshow));
		}
	}

	/**
	 * 文章是否加载图片显示
	 * 
	 * @param activity
	 */
	public static void changeSettingIsLoadImage(Activity activity) {
		AppContext ac = (AppContext) activity.getApplication();
		if (ac.isLoadImage()) {
			ac.setConfigLoadimage(false);
			ToastMessage(activity, "已设置文章不加载图片");
		} else {
			ac.setConfigLoadimage(true);
			ToastMessage(activity, "已设置文章加载图片");
		}
	}

	public static void changeSettingIsLoadImage(Activity activity, boolean b) {
		AppContext ac = (AppContext) activity.getApplication();
		ac.setConfigLoadimage(b);
	}

	/**
	 * 清除app缓存
	 * 
	 * @param activity
	 */
	public static void clearAppCache(Activity activity) {
		final AppContext ac = (AppContext) activity.getApplication();
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					ToastMessage(ac, "缓存清除成功");
				} else {
					ToastMessage(ac, "缓存清除失败");
				}
			}
		};
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					ac.clearAppCache();
					msg.what = 1;
				} catch (Exception e) {
					e.printStackTrace();
					msg.what = -1;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	/**
	 * 发送App异常崩溃报告
	 * 
	 * @param cont
	 * @param crashReport
	 */
	public static void sendAppCrashReport(final Context cont,
			final String crashReport) {
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(R.string.app_error);
		builder.setMessage(R.string.app_error_message);
		builder.setPositiveButton(R.string.submit_report,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 发送异常报告
						Intent i = new Intent(Intent.ACTION_SEND);
						// i.setType("text/plain"); //模拟器
						i.setType("message/rfc822"); // 真机
						i.putExtra(Intent.EXTRA_EMAIL,
								new String[] { "1988fuyu@163.com" });
						i.putExtra(Intent.EXTRA_SUBJECT,
								"3G智能健康服务机Android客户端 - 错误报告");
						i.putExtra(Intent.EXTRA_TEXT, crashReport);
						cont.startActivity(Intent.createChooser(i, "发送错误报告"));
						// 退出
						AppManager.getAppManager().AppExit(cont);
					}
				});
		builder.setNegativeButton(R.string.sure,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 退出
						AppManager.getAppManager().AppExit(cont);
					}
				});
		builder.show();
	}

	/**
	 * 退出程序
	 * 
	 * @param cont
	 */
	public static void Exit(final Context cont) {
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(R.string.app_menu_surelogout);
		builder.setPositiveButton(R.string.sure,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 退出
						AppManager.getAppManager().AppExit(cont);
					}
				});
		builder.setNegativeButton(R.string.cancle,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.show();
	}

	/**
	 * 新闻超链接点击跳转
	 * 
	 * @param context
	 * @param newsId
	 * @param newsType
	 * @param objId
	 */
	public static void showNewsRedirect(Context context, News news) {
		String url = news.getUrl();
		// url为空-旧方法
		if (StringUtils.isEmpty(url)) {
			int newsId = news.getId();
			int newsType = news.getNewType().type;
			String objId = news.getNewType().attachment;

			showNewsDetail(context, newsId);
			// switch (newsType) {
			// case Constants.News.NEWSTYPE_NEWS:
			// showNewsDetail(context, newsId);
			// break;
			// }
		} else {
			showUrlRedirect(context, url);
		}
	}

	/**
	 * 显示新闻详情
	 * 
	 * @param context
	 * @param newsId
	 */
	public static void showNewsDetail(Context context, int newsId) {
		Intent intent = new Intent(context, NewsDetail.class);
		intent.putExtra("news_id", newsId);
		context.startActivity(intent);
	}

	/**
	 * 显示搜索界面
	 * 
	 * @param context
	 */
	public static void showSearch(Context context) {
		Intent intent = new Intent(context, Search.class);
		context.startActivity(intent);
	}
}
