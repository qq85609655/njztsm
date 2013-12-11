package com.sklay.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sklay.enums.DelStatus;
import com.sklay.model.News;

public interface NewsDao extends JpaRepository<News, Long> {

	@Query("select n from News n where n.id in (?1)")
	public List<News> get(Set<Long> ids);

	@Modifying
	@Query("update News n set n.delStatus = ?2 where n.id in (?1) ")
	public void delete(Set<Long> ids, DelStatus delStatus);
}
