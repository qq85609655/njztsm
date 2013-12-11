/*
 * Project:  any
 * Module:   sklay-web
 * File:     ImageController.java
 * Modifier: zhouning
 * Modified: 2012-12-9 下午7:18:01 
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.sklay.web;

import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
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
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sklay.core.io.StaticResourceMappingManager;
import com.sklay.core.io.image.CommonImage;
import com.sklay.core.io.image.ImgConfig;
import com.sklay.core.io.image.ImgSize;
import com.sklay.core.util.StringUtils;
import com.sklay.enums.AuditStatus;
import com.sklay.enums.DelStatus;
import com.sklay.model.News;
import com.sklay.model.NewsCategory;
import com.sklay.model.NewsCategoryPK;
import com.sklay.model.NewsType;
import com.sklay.model.User;
import com.sklay.service.NewsCategoryService;
import com.sklay.service.NewsService;
import com.sklay.service.NewsTypeService;
import com.sklay.util.LoginUserHelper;
import com.sklay.view.DataView;

/**
 * 
 * @author hero
 * 
 */
@Controller
@RequestMapping("/supervise")
public class ManagerController {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ManagerController.class);

	@Autowired
	private StaticResourceMappingManager resourceMappingManager;

	@Autowired
	private NewsCategoryService newsCategoryService;

	@Autowired
	private NewsTypeService newsTypeService;

	@Autowired
	private NewsService newsService;

	@Value("#{config['base.url']}")
	private String baseUrl;

	private static String BIZ_NEWSIMG = "news";

	@RequestMapping("")
	@RequiresUser
	public String index(@PageableDefaults(value = 20) Pageable pageable,
			ModelMap model) {

		List<NewsType> typeList = newsTypeService.list();
		String keyword = null;
		Map<Long, List<NewsType>> types = Maps.newHashMap();
		List<NewsCategory> list = Lists.newArrayList();

		Page<News> page = newsCategoryService.newsPage(keyword, pageable);

		List<News> newsList = page.getContent();
		if (CollectionUtils.isNotEmpty(newsList)) {
			NewsType newsType = new NewsType();
			newsType.setId(0L);

			for (News news : newsList) {
				NewsCategory newsCategory = new NewsCategory();
				NewsCategoryPK newsCategoryPk = new NewsCategoryPK();

				newsCategoryPk.setNews(news);
				newsCategoryPk.setNewsType(newsType);
				newsCategory.setNewsCategory(newsCategoryPk);

				list.add(newsCategory);
			}
			types = findNewsTypes(list);
		}

		model.addAttribute("types", typeList);
		model.addAttribute("mapsTypes", types);
		model.addAttribute("pageModel", page);
		model.addAttribute("modelList", list);

		return "manager.manager";
	}

	@RequestMapping("/news/page")
	@RequiresUser
	public String newsPage(String keyword, Long newsTypeId,
			@PageableDefaults(value = 20) Pageable pageable, ModelMap model) {

		List<NewsType> typeList = newsTypeService.list();
		AuditStatus status = null;
		Map<Long, List<NewsType>> types = Maps.newHashMap();

		Page<NewsCategory> page = newsCategoryService.newsCategorypage(keyword,
				newsTypeId, status, pageable);
		List<NewsCategory> list = page.getContent();
		if (CollectionUtils.isNotEmpty(list)) {
			types = findNewsTypes(page.getContent());
		}

		model.addAttribute("types", typeList);
		model.addAttribute("mapsTypes", types);
		model.addAttribute("pageModel", page);
		model.addAttribute("nav", newsTypeId);
		model.addAttribute("keyword", keyword);
		model.addAttribute("modelList", list);

		return "manager.manager";
	}

	@RequestMapping("/news/add")
	@RequiresUser
	public String newsAdd(NewsCategory newsCategory,
			@PageableDefaults(value = 20) Pageable pageable, ModelMap model)
			throws Exception {

		Long newsTypeId = null;
		String error = "";

		if (null == newsCategory || null == newsCategory.getNewsCategory()) {
			error = "新增文章失败!";
			model.addAttribute("error", error);
			return newsPage(null, newsTypeId, pageable, model);
		}

		User session = LoginUserHelper.getLoginUser();

		News news = newsCategory.getNewsCategory().getNews();
		NewsType sNewsType = newsCategory.getNewsCategory().getNewsType();

		if (null == sNewsType || null == sNewsType.getId()) {
			error = "新增文章失败,请选择文章类型!";
			model.addAttribute("error", error);
			return newsPage(null, newsTypeId, pageable, model);
		}

		if (null == news
				|| org.apache.commons.lang.StringUtils.isBlank(news.getTitle())
				|| org.apache.commons.lang.StringUtils.isBlank(news.getBody())) {
			error = "新增文章失败,文章标题货内容不能为空!";
			model.addAttribute("error", error);
			return newsPage(null, newsTypeId, pageable, model);
		}

		newsTypeId = sNewsType.getId();

		NewsType newsType = newsTypeService.get(newsTypeId);
		if (null == newsType) {
			error = "新增文章失败,请选择文章类型!";
			model.addAttribute("error", error);
			return newsPage(null, newsTypeId, pageable, model);
		}
		news = splitNewsBody(news, newsType, session);
		news.setCommentCount(0);
		news.setCreator(session);
		news.setFavorite(0);
		news.setVer(1);
		news.setImgCount(0);
		news.setDelStatus(DelStatus.SAVE);
		news.setPubDate(new Date());
		news = newsService.create(news);

		NewsCategory category = new NewsCategory();
		NewsCategoryPK categoryPK = new NewsCategoryPK();
		categoryPK.setNews(news);
		categoryPK.setNewsType(newsType);

		category.setNewsCategory(categoryPK);
		category.setCreateTime(new Date());
		category.setStatus(AuditStatus.WAIT);

		newsCategoryService.create(category);

		model.addAttribute("success", "新增成功!");

		return newsPage(null, newsTypeId, pageable, model);
	}

	@RequestMapping("/news/edit")
	@RequiresUser
	public String newsEdit(NewsCategory newsCategory,
			@PageableDefaults(value = 20) Pageable pageable, ModelMap model)
			throws Exception {

		String error = "";

		if (null == newsCategory || null == newsCategory.getNewsCategory()) {
			error = "文章修改失败!";
			model.addAttribute("error", error);
			return index(pageable, model);
		}

		News news = newsCategory.getNewsCategory().getNews();

		if (null == news || null == news.getId()) {
			error = "文章修改失败,文章不能为空!";
			model.addAttribute("error", error);
			return index(pageable, model);
		}

		if (org.apache.commons.lang.StringUtils.isBlank(news.getTitle())
				|| org.apache.commons.lang.StringUtils.isBlank(news.getBody())) {
			error = "新增文章失败,文章标题货内容不能为空!";
			model.addAttribute("error", error);
			return index(pageable, model);
		}

		News original = newsService.get(news.getId());

		if (null == original) {
			error = "文章修改失败,文章id[" + news.getId() + "]不存在!";
			model.addAttribute("error", error);
			return index(pageable, model);
		}
		NewsType newsType = null;
		List<NewsCategory> categories = newsCategoryService
				.getByNewsId(original.getId());
		NewsCategory category = null;
		if (CollectionUtils.isNotEmpty(categories)) {
			category = categories.get(0);
			newsType = category.getNewsCategory().getNewsType();
		}
		if (null == newsType) {
			error = "新增文章失败,请选择文章类型!";
			model.addAttribute("error", error);
			return index(pageable, model);
		}

		User session = LoginUserHelper.getLoginUser();

		news = splitNewsBody(news, newsType, session);

		int imgCount = news.getImgCount() < 1 ? original.getImgCount() : news
				.getImgCount();
		original.setBody(news.getBody());
		original.setTitle(news.getTitle());
		original.setVer(original.getVer() + 1);
		original.setImgCount(imgCount);

		newsService.update(original);

		category.setStatus(AuditStatus.WAIT);
		newsCategoryService.update(category);

		model.addAttribute("success", "修改成功!");

		return newsPage(null, newsType.getId(), pageable, model);
	}

	@RequestMapping("/news/get/{newscate}")
	@ResponseBody
	@RequiresUser
	public DataView newsGet(@PathVariable String newscate) throws Exception {

		DataView dataView = new DataView();
		dataView.setCode(-1);
		dataView.setMsg("文章id不存在!");

		if (org.apache.commons.lang.StringUtils.isBlank(newscate)) {
			return dataView;
		}
		String[] ids = newscate.split("-");
		Long newsId = Long.valueOf(ids[0]);
		Long typeId = Long.valueOf(ids[1]);
		News news = null;
		if (null == typeId || 0 == typeId) {
			news = newsService.get(newsId);
		} else {
			NewsCategory newsCategory = newsCategoryService.get(newsId, typeId);
			if (null != newsCategory)
				news = newsCategory.getNewsCategory().getNews();
		}

		if (null != news) {
			dataView.setData(JSONObject.toJSONString(news));
			dataView.setCode(1);
			dataView.setMsg("");
		}

		return dataView;
	}

	@RequestMapping(value = "/news/delete/{newsId}")
	@RequiresUser
	public String newsDelete(@PathVariable Long newsId,
			@PageableDefaults(value = 20) Pageable pageable, ModelMap model) {

		if (null != newsId)
			newsService.delete(Sets.newHashSet(newsId), DelStatus.DELETE);

		return index(pageable, model);
	}

	@RequestMapping(value = "/news/category/audit/pass")
	@RequiresUser
	public String newsCategoryAuditPass(String[] newscates,
			@PageableDefaults(value = 20) Pageable pageable, ModelMap model) {

		Set<NewsCategoryPK> pks = splitNewsCategoryPK(newscates);
		if (CollectionUtils.isNotEmpty(pks))
			newsCategoryService.audit(pks, AuditStatus.PASS);

		return index(pageable, model);
	}

	@RequestMapping(value = "/news/category/audit/not")
	@RequiresUser
	public String newsCategoryAuditNot(String[] newscates,
			@PageableDefaults(value = 20) Pageable pageable, ModelMap model) {
		Set<NewsCategoryPK> pks = splitNewsCategoryPK(newscates);
		if (CollectionUtils.isNotEmpty(pks))
			newsCategoryService.audit(pks, AuditStatus.NOT);

		return index(pageable, model);
	}

	@RequestMapping(value = "/news/category/delete")
	@RequiresUser
	public String newsCategoryDetete(String[] newscates,
			@PageableDefaults(value = 20) Pageable pageable, ModelMap model) {
		Set<NewsCategoryPK> pks = splitNewsCategoryPK(newscates);
		if (CollectionUtils.isNotEmpty(pks))
			newsCategoryService.deleteByPK(pks);

		return index(pageable, model);
	}

	private Set<NewsCategoryPK> splitNewsCategoryPK(String[] newscates) {

		if (null != newscates && newscates.length > 0) {
			Set<NewsCategoryPK> pks = Sets.newHashSet();
			Set<Long> newsIds = Sets.newHashSet();
			NewsType newsType = null;

			for (String newscate : newscates) {
				if (org.apache.commons.lang.StringUtils.isBlank(newscate))
					continue;
				String[] newsCate = newscate.split("-");
				if (null == newsCate || newsCate.length < 2)
					continue;
				String part1 = newsCate[0];
				String part2 = newsCate[1];

				Long newsId = Long.valueOf(part1);
				Long typeId = Long.valueOf(part2);

				if (newsId <= 0 || typeId <= 0)
					continue;

				newsType = (null == newsType) ? newsTypeService.get(typeId)
						: newsType;

				if (null == newsType)
					continue;

				newsIds.add(newsId);
			}

			if (CollectionUtils.isNotEmpty(newsIds)) {
				List<News> newsList = newsService.get(newsIds);

				for (News news : newsList) {
					NewsCategoryPK pk = new NewsCategoryPK();
					pk.setNews(news);
					pk.setNewsType(newsType);

					pks.add(pk);
				}

			}

			return pks;
		}

		return null;
	}

	private News splitNewsBody(News news, NewsType newsType, User session)
			throws Exception {
		String content = news.getBody();
		Set<String> imgSrcs = StringUtils.findImgSrcs(content);
		int index = news.getImgCount() | 0;
		if (!org.springframework.util.CollectionUtils.isEmpty(imgSrcs)) {
			for (String src : imgSrcs) {
				String fullPathSrc = new String(src);
				// from editor
				if (org.apache.commons.lang.StringUtils.startsWithIgnoreCase(
						src, "//")) {
					fullPathSrc = "http:" + src;
				} else if (org.apache.commons.lang.StringUtils
						.startsWithIgnoreCase(src, "/")) {
					fullPathSrc = baseUrl + src;
				} else if (!org.apache.commons.lang.StringUtils
						.startsWithIgnoreCase(src, "http")) {
					fullPathSrc = "http://" + src;
				}

				CommonImage ci = null;
				try {
					ci = new CommonImage(new URI(fullPathSrc));
				} catch (Exception e) {
					// ignore
				}
				if (ci != null) {
					Resource parent = resourceMappingManager
							.getRealPathByBizAndOwner(BIZ_NEWSIMG,
									String.valueOf(news.getId()));

					String imgC = newsType.getConfig();

					ImgConfig imgConfig = org.apache.commons.lang.StringUtils
							.isBlank(imgC) ? null : JSONObject.parseObject(
							imgC, ImgConfig.class);

					LOGGER.debug("Resource parent exists {} " + parent.exists());
					LOGGER.debug("Resource parent {} " + parent);
					String resouceWithIndex = parent.getFile().getPath()
							+ File.separator + index;
					LOGGER.debug("resouceWithIndex: " + resouceWithIndex);
					FileUtils.forceMkdir(new File(resouceWithIndex));
					if (imgConfig == null
							|| (imgConfig.getMaxHeight() <= 0 || imgConfig
									.getMaxHeight() <= 0)) {
						ci.save(resouceWithIndex + File.separator
								+ "orginal.jpg");
					} else {
						ci.resizeWithContainer(imgConfig.getMaxWidth(),
								imgConfig.getMaxHeight()).save(
								resouceWithIndex + File.separator
										+ "orginal.jpg");
					}
					if (imgConfig != null) {
						List<ImgSize> imgSizes = imgConfig.getImgSizes();
						if (!CollectionUtils.isEmpty(imgSizes)) {
							int sizeIndex = 0;
							for (ImgSize imgSize : imgSizes) {
								if (imgSize.getWidth() != null
										&& imgSize.getHeight() != null
										&& imgSize.getWidth() > 0
										&& imgSize.getHeight() > 0) {
									ci.resizeWithMaxWidth(imgSize.getWidth())
											.save(resouceWithIndex
													+ File.separator + 'z'
													+ sizeIndex + ".jpg");
									ci.cropWithContainer(imgSize.getWidth(),
											imgSize.getHeight()).save(
											resouceWithIndex + File.separator
													+ 'c' + sizeIndex + ".jpg");
									sizeIndex++;
								}
							}
						}
					}
					String httpPath = resourceMappingManager
							.getHttpPathByBizAndOwner(BIZ_NEWSIMG,
									String.valueOf(news.getId()));
					String orginalhttpPath = httpPath + "/" + index
							+ "/orginal.jpg?ver=" + news.getVer();
					content = org.apache.commons.lang.StringUtils.replace(
							content, src, orginalhttpPath);
				}
				index++;
			}
			news.setBody(content);
			news.setCommentCount(0);
			news.setCreator(session);
			news.setFavorite(0);
			news.setVer(0);
			news.setImgCount(index);

			// commentDao.save(comment);
		}

		return news;
	}

	private Map<Long, List<NewsType>> findNewsTypes(
			List<NewsCategory> newsCategories) {

		Map<Long, List<NewsType>> types = Maps.newHashMap();
		Set<Long> newsIds = Sets.newHashSet();
		for (NewsCategory nc : newsCategories)
			newsIds.add(nc.getNewsCategory().getNews().getId());
		List<NewsCategory> categories = newsCategoryService
				.getByNewsIds(newsIds);
		if (CollectionUtils.isNotEmpty(categories))
			for (NewsCategory newsCategory : categories) {
				Long key = newsCategory.getNewsCategory().getNews().getId();
				NewsType newsType = newsCategory.getNewsCategory()
						.getNewsType();
				List<NewsType> value = null;
				if (types.containsKey(key)) {
					value = types.get(key);
					value.add(newsType);
				} else {
					value = Lists.newArrayList(newsType);
				}
				types.put(key, value);
			}
		return types;
	}

}
