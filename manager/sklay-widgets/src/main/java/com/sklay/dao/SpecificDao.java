package com.sklay.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sklay.core.enums.AppType;
import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.BindingMold;
import com.sklay.core.enums.Level;
import com.sklay.core.enums.ReadType;
import com.sklay.core.enums.SMSStatus;
import com.sklay.core.enums.SMSType;
import com.sklay.core.enums.Sex;
import com.sklay.core.enums.SwitchStatus;
import com.sklay.core.enums.TipType;
import com.sklay.core.ex.SklayException;
import com.sklay.enums.LogLevelType;
import com.sklay.model.Application;
import com.sklay.model.DeviceBinding;
import com.sklay.model.Festival;
import com.sklay.model.GlobalSetting;
import com.sklay.model.Group;
import com.sklay.model.MataData;
import com.sklay.model.MedicalReport;
import com.sklay.model.Operation;
import com.sklay.model.SMS;
import com.sklay.model.SMSTemplate;
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

	public Page<DeviceBinding> getDeviceBindingPage(Long groups,
			String keyword, Level level, BindingMold bindingMold, User creator,
			AuditStatus status, AuditStatus moldStatus, Long belong,
			Pageable pageable) throws SklayException;

	public List<User> findAgent(User owner);

	public Map<Long, Long> findMemberCount(Set<User> owner);

	public Page<User> findMemberPage(Long group, String keyword, User user,
			Long belong, Pageable pageable);

	public Page<Operation> getOperationPage(LogLevelType levelType,
			Pageable pageable) throws SklayException;

	public MedicalReport findLastReport(Long userId, SMSType reportType)
			throws SklayException;

	public Page<MedicalReport> searchMedicalReport(String keyword,
			TipType tipType, ReadType readType, User user, Set<Group> groups,
			Pageable pageable) throws SklayException;

	public Page<Application> findAppPage(String keyword, AppType appType,
			AuditStatus status, Long owner, Pageable pageable)
			throws SklayException;

	public List<Application> getByCreator(AppType appType, AuditStatus status,
			Long owner) throws SklayException;

	public Page<SMS> getSMSPage(Application app, SMSStatus status,
			User crteator, Long belong, Pageable pageable)
			throws SklayException;

	public List<DeviceBinding> findTargetBinding(String phone, Level level);

	public List<DeviceBinding> getUserBindingByLevel(Set<Long> targetUser,
			Level level, String sn) throws SklayException;

	public List<User> queryBirthdayUser() throws SklayException;

	public List<DeviceBinding> findTargetBinding(User targetUser, Level level);

	public Page<Festival> getFestivalPage(String jobTime,
			SwitchStatus switchStatus, String keyword, Pageable pageable)
			throws SklayException;

	public Page<SMSTemplate> getSMSTemplatePage(AuditStatus status,
			Festival festival, String keyword, Pageable pageable)
			throws SklayException;
}
