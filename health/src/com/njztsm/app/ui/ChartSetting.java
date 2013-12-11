package com.njztsm.app.ui;

import java.io.File;

import com.njztsm.app.AppContext;
import com.njztsm.app.AppManager;
import com.njztsm.app.common.FileUtils;
import com.njztsm.app.common.MethodsCompat;
import com.njztsm.app.common.UIHelper;
import com.njztsm.app.common.UpdateManager;

import com.njztsm.app.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ChartSetting extends PreferenceActivity {

	SharedPreferences chart_Preferences;
	Preference chart_cache;
	Preference chart_update;
	Preference chart_about;
	CheckBoxPreference chart_checkup;
	CheckBoxPreference chart_voice;
	CheckBoxPreference chart_loadimage;
	CheckBoxPreference chart_scroll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final AppContext ac = (AppContext) getApplication();
		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);

		// 设置显示Preferences
		addPreferencesFromResource(R.xml.chart_preferences);
		// 获得SharedPreferences
		chart_Preferences = PreferenceManager.getDefaultSharedPreferences(this);

		ListView localListView = getListView();
		localListView.setBackgroundColor(0);
		localListView.setCacheColorHint(0);

		((ViewGroup) localListView.getParent()).removeView(localListView);
		ViewGroup localViewGroup = (ViewGroup) getLayoutInflater().inflate(
				R.layout.chart_setting, null);

		((ViewGroup) localViewGroup.findViewById(R.id.chart_setting_content))
				.addView(localListView, -1, -1);
		setContentView(localViewGroup);

		// 加载图片loadimage
		chart_loadimage = (CheckBoxPreference) findPreference("chart_loadimage");
		chart_loadimage.setChecked(ac.isLoadImage());
		if (ac.isLoadImage()) {
			chart_loadimage.setSummary("页面加载图片 (默认在WIFI网络下加载图片)");
		} else {
			chart_loadimage.setSummary("页面不加载图片 (默认在WIFI网络下加载图片)");
		}
		chart_loadimage
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						UIHelper.changeSettingIsLoadImage(ChartSetting.this,
								chart_loadimage.isChecked());
						if (chart_loadimage.isChecked()) {
							chart_loadimage
									.setSummary("页面加载图片 (默认在WIFI网络下加载图片)");
						} else {
							chart_loadimage
									.setSummary("页面不加载图片 (默认在WIFI网络下加载图片)");
						}
						return true;
					}
				});

		// 左右滑动
		chart_scroll = (CheckBoxPreference) findPreference("chart_scroll");
		chart_scroll.setChecked(ac.isScroll());
		if (ac.isScroll()) {
			chart_scroll.setSummary("已启用左右滑动");
		} else {
			chart_scroll.setSummary("已关闭左右滑动");
		}
		chart_scroll
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						ac.setConfigScroll(chart_scroll.isChecked());
						if (chart_scroll.isChecked()) {
							chart_scroll.setSummary("已启用左右滑动");
						} else {
							chart_scroll.setSummary("已关闭左右滑动");
						}
						return true;
					}
				});

		// 提示声音
		chart_voice = (CheckBoxPreference) findPreference("chart_voice");
		chart_voice.setChecked(ac.isVoice());
		if (ac.isVoice()) {
			chart_voice.setSummary("已开启提示声音");
		} else {
			chart_voice.setSummary("已关闭提示声音");
		}
		chart_voice
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						ac.setConfigVoice(chart_voice.isChecked());
						if (chart_voice.isChecked()) {
							chart_voice.setSummary("已开启提示声音");
						} else {
							chart_voice.setSummary("已关闭提示声音");
						}
						return true;
					}
				});

		// 启动检查更新
		chart_checkup = (CheckBoxPreference) findPreference("chart_checkup");
		chart_checkup.setChecked(ac.isCheckUp());
		chart_checkup
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						ac.setConfigCheckUp(chart_checkup.isChecked());
						return true;
					}
				});

		// 计算缓存大小
		long fileSize = 0;
		String cacheSize = "0KB";
		File filesDir = getFilesDir();
		File cacheDir = getCacheDir();

		fileSize += FileUtils.getDirSize(filesDir);
		fileSize += FileUtils.getDirSize(cacheDir);
		// 2.2版本才有将应用缓存转移到sd卡的功能
		if (AppContext.isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
			File externalCacheDir = MethodsCompat.getExternalCacheDir(this);
			fileSize += FileUtils.getDirSize(externalCacheDir);
		}
		if (fileSize > 0)
			cacheSize = FileUtils.formatFileSize(fileSize);

		// 清除缓存
		chart_cache = (Preference) findPreference("chart_cache");
		chart_cache.setSummary(cacheSize);
		chart_cache
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						UIHelper.clearAppCache(ChartSetting.this);
						chart_cache.setSummary("0KB");
						return true;
					}
				});

		// 版本更新
		chart_update = (Preference) findPreference("chart_update");
		chart_update
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						UpdateManager.getUpdateManager().checkAppUpdate(
								ChartSetting.this, true);
						return true;
					}
				});

		// 关于我们
		chart_about = (Preference) findPreference("chart_about");
		chart_about
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						UIHelper.showAbout(ChartSetting.this);
						return true;
					}
				});

	}

	public void back(View paramView) {
		finish();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}
}
