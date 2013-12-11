package com.sklay.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sklay.core.enums.Sex;
import com.sklay.model.MataData;

/**
 * 
 * 
 * @author <a href="mailto:1988fuyu@163.com">fuyu</a>
 * 
 * @version v1.0 2013-7-1
 */
public interface MatadataDao extends JpaRepository<MataData, Long> {

	@Query("select m from MataData m where m.ageMin<= ?1 and  m.ageMax >= ?1 and m.sex = ?2 and m.lowPressureMin <= ?3 and m.lowPressureMax >= ?3 and m.highPressureMin <= ?4 and m.highPressureMax >= ?4 order by m.ageMin asc ")
	List<MataData> queryUserMataData(int age, Sex sex, int lowPressure,
			int highPressure);

	@Query("select count( m ) from MataData m where m.ageMin = ?1 and  m.ageMax  = ?2 and m.sex = ?3 and m.lowPressureMin  = ?4 and m.lowPressureMax  = ?5 and m.highPressureMin  = ?6 and m.highPressureMax = ?7 order by m.ageMin asc ")
	long countMataData(int ageMin, int ageMax, Sex sex,
			int lowPressureMin, int lowPressureMax, int highPressureMin,
			int highPressureMax);
}
