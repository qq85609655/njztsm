package com.sklay.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sklay.core.ex.SklayException;
import com.sklay.model.Group;
import com.sklay.model.User;

public interface SpecificGroupDao {

	public Page<Group> page(String keyword, User owner, Long belong,
			Pageable pageable) throws SklayException;

}
