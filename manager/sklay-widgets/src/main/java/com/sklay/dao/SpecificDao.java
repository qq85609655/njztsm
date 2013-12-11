package com.sklay.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sklay.core.enums.AppType;
import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.BindingMold;
import com.sklay.core.enums.Level;
import com.sklay.core.enums.MemberRole;
import com.sklay.core.enums.ReadType;
import com.sklay.core.enums.SMSStatus;
import com.sklay.core.enums.SMSType;
import com.sklay.core.enums.Sex;
import com.sklay.core.enums.TipType;
import com.sklay.core.ex.SklayException;
import com.sklay.enums.LogLevelType;
import com.sklay.model.Application;
import com.sklay.model.DeviceBinding;
import com.sklay.model.GlobalSetting;
import com.sklay.model.Group;
import com.sklay.model.MataData;
import com.sklay.model.MedicalReport;
import com.sklay.model.Operation;
import com.sklay.model.SMS;
import com.sklay.model.SMSLog;
import com.sklay.model.User;

public interface SpecificDao {

	public List<MataData> queryUserMataData(int age, int sex, int lowPressure,
			int highPressure);

	public MedicalReport createMedicalReport(MedicalReport report);

	public GlobalSetting getGlobalConfig();

	public DeviceBinding getDefaultBinding(String sn) throws SklayException;

	public List<DeviceBinding> getDefaultBindings(Set<String> devices,
			Level level) throws SklayException;

	public List<User> getBindingUser(String sn, Level level,
			AuditStatus auditStatus, BindingMold mold) throws SklayException;

	public long getBindingUserCount(String sn, Level level, BindingMold mold,
			AuditStatus auditStatus) throws SklayException;

	public Page<MataData> getMataDataPage(Integer minAge, Integer maxAge,
			Sex sex, Integer lowPressure, Integer highPressure, String keyword,
			Pageable pageable) throws SklayException;

	public List<DeviceBinding> getUserBinding(Set<Long> userId, Level level,
			User creator);

	public Page<SMSLog> getSMSPage(String keyword, Date startDate,
			Date endDate, SMSStatus status, Long userOwner, Pageable pageable);

	public Page<DeviceBinding> getDeviceBindingPage(String keyword,
			Level level, BindingMold bindingMold, User creator,
			Pageable pageable) throws SklayException;

	public Page<DeviceBinding> getDeviceBindingPage(Set<Group> groups,
			String keyword, Level level, BindingMold bindingMold, User creator,
			Pageable pageable) throws SklayException;

	public List<User> findAgent(User owner);

	public Map<Long, Long> findMemberCount(Set<User> owner);

	public Page<User> findMemberPage(Group group, String keyword,
			Pageable pageable, User user, MemberRole memberRole);

	public Page<User> findMemberPage(Set<Group> groups, String keyword,
			Pageable pageable, User user, MemberRole memberRole);

	public Page<Operation> getOperationPage(LogLevelType levelType,
			Pageable pageable) throws SklayException;

	public MedicalReport findLastReport(Long userId, SMSType reportType)
			throws SklayException;

	public Page<MedicalReport> searchMedicalReport(String keyword,
			TipType tipType, ReadType readType, User user, Set<Group> groups,
			Pageable pageable) throws SklayException;

	public Page<Application> findAppPage(String keyword, AppType appType,
			AuditStatus status, User creator, Pageable pageable)
			throws SklayException;

	public List<Application> getByCreator(AppType appType, AuditStatus status,
			User creator) throws SklayException;

	public Page<SMS> getSMSPage(Application app, SMSStatus status,
			User crteator, Pageable pageable) throws SklayException;

	public List<DeviceBinding> findTargetBinding(String phone, Level level);

	public List<DeviceBinding> getUserBindingByLevel(Set<Long> targetUser,
			Level level, String sn) throws SklayException;
}