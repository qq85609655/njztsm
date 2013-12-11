package com.sklay.controller.manage;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sklay.core.annotation.Widget;
import com.sklay.core.annotation.Widgets;
import com.sklay.core.enums.MemberRole;
import com.sklay.core.ex.ErrorCode;
import com.sklay.core.ex.SklayException;
import com.sklay.core.util.Constants;
import com.sklay.core.util.UUIDUtils;
import com.sklay.dao.SpecificDao;
import com.sklay.model.Group;
import com.sklay.model.User;
import com.sklay.service.UserService;
import com.sklay.util.LoginUserHelper;
import com.sklay.vo.TreeView;

@Controller
@RequestMapping("/admin/statistics")
@Widgets(value = "statistics", description = "统计服务")
public class StatisticsController {

	@Autowired
	private SpecificDao specificDao;

	@Autowired
	private UserService userService;

	@RequestMapping("/initTree")
	@RequiresPermissions("statistics:tree")
	@Widget(name = ":tree", description = "会员统计")
	public String initTree(ModelMap modelMap) {
		modelMap.addAttribute("nav", "statistics:tree");

		User owner = LoginUserHelper.getLoginUser();

		Long parentId = owner.getId();

		List<User> agents = null;
		TreeView parentView = new TreeView(Constants.NODE_AGENT + parentId,
				owner.getName());

		if (null != owner.getGroup()) {
			Group group = owner.getGroup();
			if (null != group.getRole()
					&& (MemberRole.ADMINSTROTAR == group.getRole() || MemberRole.AGENT == group
							.getRole())) {
				agents = specificDao.findAgent(owner);

				Set<User> userSet = Sets.newHashSet(owner);
				if (CollectionUtils.isNotEmpty(agents))
					userSet.addAll(agents);

				Map<Long, Long> memberCountMap = specificDao
						.findMemberCount(userSet);

				List<TreeView> childList = Lists.newArrayList();

				TreeView memberView = new TreeView(Constants.NODE_MEMBER
						+ UUIDUtils.randomNum(20), "普通会员");

				long agentCount = CollectionUtils.isNotEmpty(agents) ? agents
						.size() : 0;
				if (CollectionUtils.isNotEmpty(userSet)) {
					for (User agent : userSet) {
						if (null == agent)
							continue;

						Long nodeId = agent.getId();

						if (nodeId == parentId) {
							if (memberCountMap.containsKey(nodeId)) {
								long total = memberCountMap.get(nodeId);
								parentView.setName(parentView.getName() + "("
										+ total + ")");
								memberView.setName(memberView.getName() + "("
										+ (total - agentCount) + ")");
								if (total - agentCount > 0)
									childList.add(memberView);
							}

							continue;
						}

						TreeView agentView = new TreeView(Constants.NODE_AGENT
								+ nodeId, "[代]" + agent.getName());
						if (memberCountMap.containsKey(nodeId)) {
							long total = memberCountMap.get(nodeId);
							agentView.setName(agentView.getName() + "(" + total
									+ ")");
						}
						childList.add(agentView);
					}
					parentView.setChildren(childList);
				}

			}

		}

		modelMap.addAttribute("treeJson", JSONObject.toJSONString(parentView));

		return "manager.system.tree";
	}

	@RequestMapping("/treeData")
	@RequiresPermissions("statistics:tree")
	@ResponseBody
	public TreeView treeData(String nodeId, int level) {

		if (StringUtils.isEmpty(nodeId))
			throw new SklayException(ErrorCode.FINF_NULL, null, "该节点");
		if (nodeId.trim().indexOf(Constants.NODE_MEMBER) != -1)
			return new TreeView();

		Long current_NodeId = Long.valueOf(nodeId.trim().replaceAll(
				Constants.NODE_AGENT, ""));
		User owner = userService.getUser(current_NodeId);

		if (null == owner)
			throw new SklayException(ErrorCode.FINF_NULL, null, "该节点");

		List<User> agents = null;
		TreeView parentView = new TreeView(nodeId, owner.getName());

		if (null != owner.getGroup()) {
			Group group = owner.getGroup();
			if (null != group.getRole()
					&& (MemberRole.ADMINSTROTAR == group.getRole() || MemberRole.AGENT == group
							.getRole())) {
				agents = specificDao.findAgent(owner);

				Set<User> userSet = Sets.newHashSet(owner);
				if (CollectionUtils.isNotEmpty(agents))
					userSet.addAll(agents);

				Map<Long, Long> memberCountMap = specificDao
						.findMemberCount(userSet);

				List<TreeView> childList = Lists.newArrayList();

				TreeView memberView = new TreeView(Constants.NODE_MEMBER
						+ UUIDUtils.randomNum(20), "普通会员");

				long agentCount = CollectionUtils.isNotEmpty(agents) ? agents
						.size() : 0;
				if (CollectionUtils.isNotEmpty(userSet)) {
					for (User agent : userSet) {
						if (null == agent)
							continue;

						Long currentNodeId = agent.getId();

						if (currentNodeId == current_NodeId) {
							if (memberCountMap.containsKey(currentNodeId)) {
								long total = memberCountMap.get(currentNodeId);
								parentView.setName(parentView.getName() + "("
										+ total + ")");
								memberView.setName(memberView.getName() + "("
										+ (total - agentCount) + ")");
								if (total - agentCount > 0)
									childList.add(memberView);
							}
							continue;
						}

						TreeView agentView = new TreeView(Constants.NODE_AGENT
								+ currentNodeId, "[代]" + agent.getName());
						if (memberCountMap.containsKey(currentNodeId)) {
							long total = memberCountMap.get(currentNodeId);
							agentView.setName(agentView.getName() + "(" + total
									+ ")");
						}
						childList.add(agentView);
					}
					parentView.setChildren(childList);
				}

			}

		}

		return parentView;
	}
}
