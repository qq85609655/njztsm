package com.sklay.tag;

import java.util.List;
import java.util.Set;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.sklay.model.User;
import com.sklay.util.LoginUserHelper;

public class HasPermissionTag extends RequestContextAwareTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3497936601900974175L;

	private String name = null;

	@SuppressWarnings("static-access")
	@Override
	protected int doStartTagInternal() throws Exception {

		User user = LoginUserHelper.getLoginUser();

		if (null == user || null == user.getGroup()
				|| StringUtils.isBlank(user.getGroup().getPerms())
				|| StringUtils.isBlank(name))

			return super.SKIP_BODY;
		String perm = user.getGroup().getPerms();

		List<String> perms = JSONObject.parseArray(perm, String.class);
		Set<String> permSet = Sets.newHashSet(perms);
		if (!permSet.contains(name))
			return super.SKIP_BODY;
		return EVAL_BODY_INCLUDE;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
