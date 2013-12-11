package com.sklay.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sklay.core.enums.ReadType;
import com.sklay.core.enums.SMSType;
import com.sklay.core.enums.TipType;
import com.sklay.core.ex.SklayException;
import com.sklay.core.util.DateTimeUtil;
import com.sklay.dao.MedicalReportDao;
import com.sklay.dao.SpecificDao;
import com.sklay.model.Group;
import com.sklay.model.MedicalReport;
import com.sklay.model.User;
import com.sklay.service.MedicalReportService;

@Service
public class MedicalReportServiceImpl implements MedicalReportService {

	@Autowired
	private MedicalReportDao reportDao;

	@Autowired
	private SpecificDao specificDao;

	@Override
	public MedicalReport createMedicalReport(MedicalReport report)
			throws SklayException {
		if (null == report)
			return null;
		return reportDao.save(report);
	}

	@Override
	public long countDayReport(Long userId, long timeStart, long timeEnd,
			SMSType reportType) {
		return reportDao.countDayReport(userId, timeStart, timeEnd, reportType);
	}

	@Override
	public List<MedicalReport> getLastReports(Long userId, int day) {

		MedicalReport lastReport = specificDao.findLastReport(userId,
				SMSType.PHYSICAL);
		if (null == lastReport)
			return null;

		Long date = lastReport.getReportTime();
		Date endDate = DateTimeUtil.getDate(date, 1);
		Date startDate = DateTimeUtil.getDate(date, day);

		Long timeStart = startDate.getTime();
		Long timeEnd = endDate.getTime();

		return reportDao.getDayReport(userId, timeStart, timeEnd,
				SMSType.PHYSICAL);

	}

	@Override
	public MedicalReport getMemberLastReports(Long userId) {

		MedicalReport lastReport = specificDao.findLastReport(userId,
				SMSType.PHYSICAL);
		if (null == lastReport)
			return null;

		return lastReport;

	}

	@Override
	public List<MedicalReport> getMemberReports(MedicalReport lastReport,
			Long userId, int day) {

		Long date = lastReport.getReportTime();
		Date endDate = DateTimeUtil.getDate(date, 1);
		Date startDate = DateTimeUtil.getDate(date, day);

		Long timeStart = startDate.getTime();
		Long timeEnd = endDate.getTime();

		return reportDao.getDayReport(userId, timeStart, timeEnd,
				SMSType.PHYSICAL);

	}

	@Override
	public void deleteMedicalReport(Long userId) {
		reportDao.deleteMedicalReport(userId);
	}

	@Override
	public Page<MedicalReport> search(String keyword, TipType tipType,
			ReadType readType, User user, Set<Group> groups, Pageable pageable)
			throws SklayException {

		return specificDao.searchMedicalReport(keyword, tipType, readType,
				user, groups, pageable);
	}
}
