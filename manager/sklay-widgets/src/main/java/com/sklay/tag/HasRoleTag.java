package com.sklay.tag;


import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.sklay.core.enums.MemberRole;
import com.sklay.model.User;
import com.sklay.util.LoginUserHelper;

public class HasRoleTag extends RequestContextAwareTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6242333088658496999L;

	private int value = 0 ;
	
	@SuppressWarnings("static-access")
	@Override
	protected int doStartTagInternal() throws Exception {

		User user = LoginUserHelper.getLoginUser();

		if (null == user || null == user.getGroup()
				|| null == user.getGroup().getRole())

			return super.SKIP_BODY;
		MemberRole role = user.getGroup().getRole();

		if (role != MemberRole.findByValue(value))
			return super.SKIP_BODY;
		return EVAL_BODY_INCLUDE;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}


}
