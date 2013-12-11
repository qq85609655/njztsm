package com.sklay.tag;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sklay.core.enums.WidgetLevel;
import com.sklay.core.support.SimpleSklayManager;
import com.sklay.core.support.WidgetInfo;
import com.sklay.core.support.WidgetManager;
import com.sklay.model.Group;
import com.sklay.model.User;
import com.sklay.util.LoginUserHelper;
import com.sklay.vo.Menu;

public class MenuTag extends RequestContextAwareTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6711964891755114967L;

	private String menuName;

	@Override
	protected int doStartTagInternal() throws Exception {

		List<Menu> menus = Lists.newArrayList();

		User user = LoginUserHelper.getLoginUser();

		if (StringUtils.isBlank(menuName))
			menus = findMenus(user, menus);
		else
			menus = findMenusByName(user, menus, menuName);
		pageContext.setAttribute("menus", menus);

		return EVAL_BODY_INCLUDE;
		// EVAL_BODY_INCLUDE隐含1：将body的内容输出到存在的输出流中
	}

	@Override
	public int doAfterBody() throws JspException {
		return super.doAfterBody();
	}

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	@Override
	public void release() {
		super.release();
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	private List<Menu> findMenus(User user, List<Menu> menus) {
		if (null != user && null != user.getGroup()) {
			Group group = user.getGroup();

			String perm = (null == group) ? "" : (StringUtils.isBlank(group
					.getPerms()) ? "" : group.getPerms());

			List<String> perms = (StringUtils.isBlank(perm) ? null : JSONObject
					.parseArray(group.getPerms(), String.class));
			Map<String, Menu> result = Maps.newHashMap();
			WidgetManager widgetManager = new SimpleSklayManager();

			Map<String, WidgetInfo> call = widgetManager.getModelWidgets();
			if (call != null && CollectionUtils.isNotEmpty(perms)) {
				Set<String> keySet = call.keySet();
				for (String key : keySet) {
					if (!perms.contains(key))
						continue;
					Menu menuInfo = null;
					WidgetInfo widget = call.get(key);
					if (result.containsKey(key)) {
						menuInfo = result.get(key);
					} else {
						menuInfo = new Menu(widget.getDescription(),
								"javascript:void(0);", widget.getName());

						List<Menu> child = Lists.newArrayList();

						if (CollectionUtils.isNotEmpty(widget.getChild())) {
							for (WidgetInfo widgetChild : widget.getChild()) {
								if (!perms.contains(widgetChild.getName())
										|| WidgetLevel.SECOND != widgetChild
												.getLevel())
									continue;
								String href = widgetChild.getUri();
								String title = widgetChild.getDescription();
								String nav = widgetChild.getName();
								child.add(new Menu(title, href, nav));
							}
							menuInfo.setChild(child);

							if (CollectionUtils.isEmpty(child))
								continue;

							if (child.size() == 1) {
								Menu menu = child.get(0);
								menuInfo.setHref(menu.getHref());
								menuInfo.setTitle(menu.getTitle());
								menuInfo.setNav(menu.getNav());
								menuInfo.setChild(null);
							}
						}
					}
					result.put(key, menuInfo);
				}
			}

			if (null != result && result.size() > 0) {
				Set<String> keySet = result.keySet();
				for (String key : keySet)
					menus.add(result.get(key));

			}

		}
		return menus;
	}

	private List<Menu> findMenusByName(User user, List<Menu> menus,
			String menuName) {
		if (null != user && null != user.getGroup()) {
			Group group = user.getGroup();

			String perm = (null == group) ? "" : (StringUtils.isBlank(group
					.getPerms()) ? "" : group.getPerms());

			List<String> perms = (StringUtils.isBlank(perm) ? null : JSONObject
					.parseArray(group.getPerms(), String.class));
			Map<String, Menu> result = Maps.newHashMap();
			WidgetManager widgetManager = new SimpleSklayManager();

			Map<String, WidgetInfo> call = widgetManager.getModelWidgets();
			if (call != null && CollectionUtils.isNotEmpty(perms)) {
				Set<String> keySet = call.keySet();
				for (String key : keySet) {
					if (!(perms.contains(key) && key.indexOf(menuName) != -1))
						continue;
					Menu menuInfo = null;
					WidgetInfo widget = call.get(key);
					if (result.containsKey(key)) {
						menuInfo = result.get(key);
					} else {
						menuInfo = new Menu(widget.getDescription(),
								"javascript:void(0);", widget.getName());

						List<Menu> child = Lists.newArrayList();

						if (CollectionUtils.isNotEmpty(widget.getChild())) {
							for (WidgetInfo widgetChild : widget.getChild()) {
								if (!perms.contains(widgetChild.getName())
										|| WidgetLevel.THIRD != widgetChild
												.getLevel())
									continue;
								String href = widgetChild.getUri();
								String title = widgetChild.getDescription();
								String nav = widgetChild.getName();
								child.add(new Menu(title, href, nav));
							}
							menuInfo.setChild(child);

							if (CollectionUtils.isEmpty(child))
								continue;

							if (child.size() == 1) {
								Menu menu = child.get(0);
								menuInfo.setHref(menu.getHref());
								menuInfo.setTitle(menu.getTitle());
								menuInfo.setNav(menu.getNav());
								menuInfo.setChild(null);
							}
						}
					}
					result.put(key, menuInfo);
				}
			}

			if (null != result && result.size() > 0) {
				Set<String> keySet = result.keySet();
				for (String key : keySet)
					menus.add(result.get(key));

			}

		}
		return menus;
	}
}
