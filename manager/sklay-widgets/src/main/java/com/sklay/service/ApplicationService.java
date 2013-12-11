package com.sklay.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sklay.core.enums.AppType;
import com.sklay.core.enums.AuditStatus;
import com.sklay.core.ex.SklayException;
import com.sklay.model.Application;
import com.sklay.model.User;

public interface ApplicationService {

	public Application cerate(Application application) throws SklayException;

	public void remove(User creator) throws SklayException;

	public Application update(Application application) throws SklayException;

	public Page<Application> getPage(String keyword, AppType appType,
			AuditStatus status, User creator, Pageable pageable)
			throws SklayException;

	public List<Application> getByCreator(AppType appType, AuditStatus status,
			User creator) throws SklayException;

	public Application get(Long id) throws SklayException;
}
