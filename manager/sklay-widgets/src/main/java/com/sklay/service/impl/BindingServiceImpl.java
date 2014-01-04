package com.sklay.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.BindingMold;
import com.sklay.core.enums.Level;
import com.sklay.core.ex.SklayException;
import com.sklay.dao.DeviceBindingDao;
import com.sklay.dao.SpecificDao;
import com.sklay.model.DeviceBinding;
import com.sklay.model.User;
import com.sklay.service.BindingService;

/**
 * 
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-2
 */
@Service
public class BindingServiceImpl implements BindingService {

	@Autowired
	private DeviceBindingDao deviceBindingDao;

	@Autowired
	private SpecificDao specificDao;

	@Override
	public DeviceBinding createBinding(DeviceBinding deviceBinding)
			throws SklayException {
		if (null == deviceBinding)
			return null;
		deviceBinding = deviceBindingDao.saveAndFlush(deviceBinding);

		return deviceBinding;

	}

	@Override
	public void createBinding(Set<DeviceBinding> deviceBinding)
			throws SklayException {
		if (CollectionUtils.isNotEmpty(deviceBinding))
			deviceBindingDao.save(deviceBinding);
	}

	@Override
	public DeviceBinding getDefaultBinding(String sn) throws SklayException {
		if (StringUtils.isBlank(sn))
			return null;
		return specificDao.getDefaultBinding(sn);
	}

	public List<DeviceBinding> getDefaultBindings(Set<String> devices,
			Level level) throws SklayException {
		return specificDao.getDefaultBindings(devices, level);
	}

	@Override
	public List<User> getBindingUser(String sn, Level level,
			AuditStatus auditStatus, BindingMold mold) throws SklayException {
		if (StringUtils.isBlank(sn))
			return null;
		return specificDao.getBindingUser(sn, level, auditStatus, mold);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeviceBinding> getUserBinding(Set<Long> targetUser,
			Level level, User creator) throws SklayException {
		if (null == targetUser)
			return Collections.EMPTY_LIST;

		return specificDao.getUserBinding(targetUser, level, creator);
	}

	@Override
	public Page<DeviceBinding> getDeviceBindingPage(Long groups,
			String keyword, Level level, BindingMold bindingMold, User creator,
			AuditStatus status, AuditStatus moldStatus, Long belong,
			Pageable pageable) throws SklayException {

		return specificDao.getDeviceBindingPage(groups, keyword, level,
				bindingMold, creator, status, moldStatus, belong, pageable);
	}

	@Override
	public DeviceBinding get(Long id) throws SklayException {
		return deviceBindingDao.findOne(id);
	}

	@Override
	public void update(DeviceBinding deviceBinding) throws SklayException {
		deviceBindingDao.saveAndFlush(deviceBinding);
	}

	@Override
	public void delete(Long id) throws SklayException {
		deviceBindingDao.delete(id);
	}

	@Override
	public void deleteByLevel(DeviceBinding binding) throws SklayException {
		if (binding.getLevel() == Level.FIRST) {
			deviceBindingDao.deleteBinding(binding.getSerialNumber());
		} else {
			deviceBindingDao.delete(binding);
		}
	}

	public void delete(Set<DeviceBinding> bindings) throws SklayException {
		if (CollectionUtils.isNotEmpty(bindings))
			deviceBindingDao.delete(bindings);
	}

	@Override
	public long getBindingUserCount(String sn, Level level, BindingMold mold,
			AuditStatus auditStatus) throws SklayException {
		return specificDao.getBindingUserCount(sn, level, mold, auditStatus);
	}

	@Override
	public void deleteTargetBinding(Long userId) {
		deviceBindingDao.deleteTargetBinding(userId);
	}

	@Override
	public DeviceBinding findTargetBinding(Long userId, Level level) {
		return deviceBindingDao.findTargetBinding(userId, level);
	}

	@Override
	public void deleteBinding(String serialNumber) {
		deviceBindingDao.deleteBinding(serialNumber);
	}

	@Override
	public void updateMoldStatus(AuditStatus status, Set<User> needAutid,
			String sn) throws SklayException {
		deviceBindingDao.updateMoldStatus(status, needAutid, sn);
	}

	@Override
	public List<DeviceBinding> findTargetBinding(String phone, Level level) {

		return specificDao.findTargetBinding(phone, level);
	}

	@Override
	public List<DeviceBinding> getUserBindingByLevel(Set<Long> targetUser,
			Level level, String sn) throws SklayException {
		return specificDao.getUserBindingByLevel(targetUser, level, sn);
	}

	@Override
	public void updateBindingCreator(User creator, Long updator, User original) {
		deviceBindingDao.updateBindingCreator(creator, updator, original);
	}

	@Override
	public List<DeviceBinding> getDefaultBindingUser(String target)
			throws SklayException {
		return deviceBindingDao.getDefaultBindingUser(AuditStatus.PASS,
				BindingMold.FREE, BindingMold.PAID, target, Level.FIRST);
	}

	@Override
	public List<DeviceBinding> findTargetBinding(User targetUser, Level level) {
		return specificDao.findTargetBinding(targetUser, level);
	}
}
