package com.sklay.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.Level;
import com.sklay.model.DeviceBinding;
import com.sklay.model.User;

public interface DeviceBindingDao extends JpaRepository<DeviceBinding, Long> {

	@Query("select d from DeviceBinding d where d.targetUser in (?1) ")
	public List<DeviceBinding> getUserBinding(Set<Long> userId);

	@Modifying
	@Query("update DeviceBinding d set d.moldStatus = ?1 where d.targetUser in (?2) and d.serialNumber = ?3 ")
	public void updateMoldStatus(AuditStatus status, Set<User> needAutid,
			String sn);

	@Modifying
	@Query("delete from DeviceBinding d where d.serialNumber = ?1 ")
	public void deleteBinding(String serialNumber);

	@Modifying
	@Query("delete from DeviceBinding d where d.targetUser.id = ?1 ")
	public void deleteTargetBinding(Long userId);

	@Query("select d from DeviceBinding d where d.targetUser.id = ?1 and d.level = ?2 ")
	public DeviceBinding findTargetBinding(Long userId, Level level);

	@Query("select d from DeviceBinding d where d.creator = ?1 ")
	public List<DeviceBinding> getUserBinding(User creator);

	@Query("select d from DeviceBinding d where d.targetUser in (?1) and d.creator = ?2 ")
	public List<DeviceBinding> getUserBinding(Set<Long> userId, User creator);

	@Query("select d from DeviceBinding d where d.creator = ?1  and d.level=?2")
	public List<DeviceBinding> getUserBinding(User creator, Level level);

	@Query("select d from DeviceBinding d where d.targetUser in ( ?1 ) and d.level= ?2 and d.creator = ?3 ")
	public List<DeviceBinding> getUserBinding(Set<Long> userId, Level level,
			User creator);

	@Modifying
	@Query("update DeviceBinding d set d.creator = ?1 , d.updator= ?2 where d.creator = ?3 ")
	public void updateBindingCreator(User creator, Long updator, User original);
}
