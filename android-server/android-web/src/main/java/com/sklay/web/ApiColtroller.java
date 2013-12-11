package com.sklay.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.sklay.core.util.Constants;
import com.sklay.enums.AuditStatus;
import com.sklay.model.News;
import com.sklay.model.NewsCategory;
import com.sklay.model.NewsType;
import com.sklay.service.NewsCategoryService;
import com.sklay.service.NewsService;
import com.sklay.util.Convert;
import com.sklay.view.DataView;
import com.sklay.view.NewsList;
import com.sklay.view.Notice;
import com.sklay.view.Update;

@Controller
@RequestMapping("/api")
public class ApiColtroller {

	@Autowired
	private NewsCategoryService newsCategoryService;

	@Autowired
	private NewsService newsService;

	@RequestMapping("")
	public String welcome(ModelMap modelMap) {
		modelMap.addAttribute("copyright", "copyright");
		return "mobile.android.welocme";
	}

	@RequestMapping("/news/list")
	@ResponseBody
	public DataView newsList(String cacheKey, Long newsTypeId,
			@PageableDefaults(value = 20) Pageable pageable) {
		DataView dataView = new DataView();

		AuditStatus status = AuditStatus.PASS;
		String keyword = null;
		Page<NewsCategory> page = newsCategoryService.newsCategorypage(keyword,
				newsTypeId, status, pageable);
		NewsList newsList = null;
		if (null != page) {
			int catalog = null == newsTypeId ? 0 : newsTypeId.intValue();
			int pageSize = pageable.getPageSize();
			int newsCount = page.getSize();
			List<NewsCategory> list = page.getContent();
			List<com.sklay.view.News> news_list = Lists.newArrayList();
			boolean hasBody = false;
			if (CollectionUtils.isNotEmpty(list)) {
				for (NewsCategory nc : list) {
					Notice notice = null;
					com.sklay.view.News news = Convert.toApiNews(nc
							.getNewsCategory().getNews(), notice, nc
							.getNewsCategory().getNewsType(), hasBody);

					news_list.add(news);
				}
				newsList = new NewsList(catalog, pageSize, newsCount, news_list);
				String data = JSONObject.toJSONString(newsList);
				dataView.setCode(Constants.STATE.SUCCESS);
				dataView.setData(data);
			}

		}

		return dataView;
	}

	@RequestMapping("/news/detail")
	@ResponseBody
	public DataView newsDetail(Long newsId) {
		DataView dataView = new DataView();

		News news = newsService.get(newsId);
		boolean hasBody = true;
		if (null != news) {
			Notice notice = null;
			NewsType newsType = null;

			news.setCommentCount(news.getCommentCount() + 1);

			com.sklay.view.News api_news = Convert.toApiNews(news, notice,
					newsType, hasBody);

			String data = JSONObject.toJSONString(api_news);
			dataView.setCode(Constants.STATE.SUCCESS);
			dataView.setData(data);
			newsService.create(news);
		}

		return dataView;
	}

	@RequestMapping("/version")
	@ResponseBody
	public DataView lastVersion() {

		// 版本描述字符串
		// String versionName = "1.7.6.3";
		// // 新版本更新下载地址
		// String downloadUrl =
		// "http://10.0.2.2/android/download/njzt-1.7.6.3.apk";
		// // 更新描述信息
		// String updateLog = "版本信息：3G智能健康服务机 for Android v"
		// + versionName
		// +
		// " 更新日志：1、修复点击评论内容中链接时程序崩溃问题。请点击确定在线升级，如果升级失败，请到http://www.njztsm.net下载最新版本安装包大小：2.0MB";
		// // 版本号
		// int versionCode = 22;

		String versionName = "1.7.4";
		// 新版本更新下载地址
		String downloadUrl = "http://test.njztsm.net/android/download/njzt-1.7.6.3.apk";
		// 更新描述信息
		String updateLog = "版本信息：3G智能健康服务机 for Android v"
				+ versionName
				+ " 更新日志：1、修复点击评论内容中链接时程序崩溃问题。请点击确定在线升级，如果升级失败，请到http://www.njztsm.net下载最新版本安装包大小：2.0MB";
		// 版本号
		int versionCode = 18;

		Update data = new Update(versionCode, versionName, downloadUrl,
				updateLog);
		DataView dataView = new DataView(1, "success",
				JSONObject.toJSONString(data));

		return dataView;
	}

	@RequestMapping("/download/{ver}")
	public void download(@PathVariable String ver, HttpServletRequest request,
			HttpServletResponse response) {
		String strDirPath = request.getSession().getServletContext()
				.getRealPath("/");
		String name = "njzt-1.7.6.3.apk";
		String path = prePath(strDirPath) + "WEB-INF/version/" + name;
		File file = new File(path);

		response.reset();
		response.setHeader("Accept-Ranges", "bytes");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ name + "\"");
		response.addHeader("Content-Length", "" + file.length());
		response.setContentType("application/octet-stream; charset=UTF-8");

		String range = request.getHeader("Range");
		long start = 0;
		long size = file.length();
		if (range != null && range.startsWith("bytes=")) {
			int minus = range.indexOf('-');
			if (minus > -1)
				range = range.substring(6, minus);
			try {
				start = Integer.parseInt(range);
			} catch (NumberFormatException ignored) {
			}
		}
		if (start > 0) {
			response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
			StringBuilder sb = new StringBuilder("bytes ");
			sb.append(start).append("-").append(size - 1).append("/")
					.append(size);
			response.setHeader("Content-Range", sb.toString());
		}
		response.setContentLength((int) (size - start));

		try {
			transferTo(file, start, size - start,
					Channels.newChannel(response.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void transferTo(File file, long position, long count,
			WritableByteChannel target) throws IOException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			fis.getChannel().transferTo(position, count, target);
		} catch (IOException e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(fis);
		}
	}

	private String prePath(String path) {
		if (path.endsWith("/"))
			return path;
		else
			return path + "/";
	}

	public static String getRandColorCode() {
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

}
