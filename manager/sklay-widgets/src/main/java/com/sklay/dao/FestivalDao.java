package com.sklay.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sklay.core.enums.SwitchStatus;
import com.sklay.core.ex.SklayException;
import com.sklay.model.Festival;

public interface FestivalDao extends JpaRepository<Festival, Long> {

	@Query(" select f from Festival f where f.id in ( ?1 ) ")
	public List<Festival> list(Set<Long> ids) ;
	
	@Modifying
	@Query(" update  Festival f set f.switchStatus = ?2 where f.id in ( ?1 ) ")
	public void offOn(Set<Long> festival, SwitchStatus switchStatus)
			throws SklayException ;
}
