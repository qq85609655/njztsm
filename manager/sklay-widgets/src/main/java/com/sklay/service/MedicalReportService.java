package com.sklay.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sklay.core.enums.ReadType;
import com.sklay.core.enums.SMSType;
import com.sklay.core.enums.TipType;
import com.sklay.core.ex.SklayException;
import com.sklay.model.Group;
import com.sklay.model.MedicalReport;
import com.sklay.model.User;

public interface MedicalReportService
{
    
    public Page<MedicalReport> search(String keyword, TipType tipType, ReadType readType, User user, Set<Group> groups,
        Pageable pageable)
        throws SklayException;
    
    public MedicalReport createMedicalReport(MedicalReport report)
        throws SklayException;
    
    public long countDayReport(Long userId, long timeStart, long timeEnd, SMSType reportType);
    
    public List<MedicalReport> getLastReports(Long userId, int day);
    
    public List<MedicalReport> getLastReports(Long userId, Date dateTime, int day);
    
    public List<MedicalReport> getMemberReports(Long lastReportDate, Long userId, int day);
    
    public void deleteMedicalReport(Long userId);
    
    public MedicalReport getMemberLastReports(Long userId);
}
