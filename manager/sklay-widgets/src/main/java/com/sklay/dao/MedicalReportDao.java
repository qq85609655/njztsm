package com.sklay.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sklay.core.enums.SMSType;
import com.sklay.model.MedicalReport;

public interface MedicalReportDao extends JpaRepository<MedicalReport, Long> {

	@Query(" select count(m) from MedicalReport m where m.targetUser.id = ?1  and m.reportTime >= ?2 and  m.reportTime <= ?3 and  m.reportType = ?4 ")
	public long countDayReport(Long userId, long timeStart, long timeEnd,
			SMSType reportType);

	@Query(" select m from MedicalReport m where m.targetUser.id = ?1  and m.reportTime between  ?2 and ?3 and  m.reportType = ?4 GROUP BY date_format(from_unixtime(m.reportTime/1000),'%Y:%m:%d') order by m.reportTime desc")
	public List<MedicalReport> getDayReport(Long userId, long timeStart,
			long timeEnd, SMSType reportType);

	@Modifying
	@Query("delete from MedicalReport m where m.targetUser.id = ?1 ")
	public void deleteMedicalReport(Long userId);
}
