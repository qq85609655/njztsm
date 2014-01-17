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
	public List<Festival> list(Set<Long> ids);

	@Modifying
	@Query(" update  Festival f set f.switchStatus = ?1 where f.id in ( ?2 ) ")
	public void offOn(SwitchStatus switchStatus, Set<Long> festival)
			throws SklayException;

	@Query(" select f from Festival f where f.jobTime = ?1  ")
	public List<Festival> list(String jobTime) throws SklayException;

	@Query(" select f from Festival f where f.jobTime = ?1 and f.switchStatus = ?2 ")
	public List<Festival> list(String jobTime, SwitchStatus switchStatus)
			throws SklayException;
}
