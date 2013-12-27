package com.sklay.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sklay.core.support.AuthBase;
import com.sklay.model.Group;
import com.sklay.model.User;

public class Convert {

	public static Map<String, AuthBase> spiltUserAuth(User user,
			Map<String, AuthBase> call) {
		Group group = user.getGroup();

		String perm = (null == group) ? "" : (StringUtils.isBlank(group
				.getPerms()) ? "" : group.getPerms());

		List<String> perms = (StringUtils.isBlank(perm) ? null : JSONObject
				.parseArray(group.getPerms(), String.class));
		Map<String, AuthBase> result = Maps.newHashMap();
		if (call != null && CollectionUtils.isNotEmpty(perms)) {
			Set<String> keySet = call.keySet();
			for (String key : keySet) {
				if (!perms.contains(key))
					continue;
				AuthBase widgetInfo = null;
				if (result.containsKey(key))
					widgetInfo = result.get(key);
				else {
					widgetInfo = call.get(key);
					List<AuthBase> child = Lists.newArrayList();
					if (CollectionUtils.isEmpty(widgetInfo.getChildBase()))
						result.put(key, widgetInfo);
					else {
						for (AuthBase widgetChild : widgetInfo.getChildBase()) {
							if (perms.contains(widgetChild.getName()))
								child.add(widgetChild);
						}
						widgetInfo.setChildBase(child);
						result.put(key, widgetInfo);
					}

				}

			}

		}

		return result;
	}
}
