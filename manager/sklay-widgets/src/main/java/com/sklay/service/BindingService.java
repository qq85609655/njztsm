package com.sklay.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.BindingMold;
import com.sklay.core.enums.Level;
import com.sklay.core.ex.SklayException;
import com.sklay.model.DeviceBinding;
import com.sklay.model.User;

public interface BindingService {

	public DeviceBinding get(Long id) throws SklayException;

	public List<DeviceBinding> getDefaultBindingUser(String target)
			throws SklayException;

	public void update(DeviceBinding deviceBinding) throws SklayException;

	public DeviceBinding createBinding(DeviceBinding deviceBinding)
			throws SklayException;

	public void createBinding(Set<DeviceBinding> deviceBinding)
			throws SklayException;

	public void updateMoldStatus(AuditStatus status, Set<User> needAutid,
			String sn) throws SklayException;

	public List<DeviceBinding> getUserBinding(Set<Long> targetUser,
			Level level, User creator) throws SklayException;

	public List<DeviceBinding> getUserBindingByLevel(Set<Long> targetUser,
			Level level, String sn) throws SklayException;

	public List<DeviceBinding> getDefaultBindings(Set<String> devices,
			Level level) throws SklayException;

	public DeviceBinding getDefaultBinding(String sn) throws SklayException;

	public List<User> getBindingUser(String sn, Level level,
			AuditStatus auditStatus, BindingMold mold) throws SklayException;

	public long getBindingUserCount(String sn, Level level, BindingMold mold,
			AuditStatus auditStatus) throws SklayException;

	public void delete(Long id) throws SklayException;

	public void deleteByLevel(DeviceBinding binding) throws SklayException;

	public void delete(Set<DeviceBinding> bindings) throws SklayException;

	public Page<DeviceBinding> getDeviceBindingPage(Long groups,
			String keyword, Level level, BindingMold bindingMold, User creator,
			AuditStatus status, AuditStatus moldStatus, Long belong,
			Pageable pageable) throws SklayException;

	public void deleteTargetBinding(Long userId);

	public DeviceBinding findTargetBinding(Long userId, Level level);

	public List<DeviceBinding> findTargetBinding(String phone, Level level);

	public void deleteBinding(String serialNumber);

	public void updateBindingCreator(User creator, Long updator, User original);
}
