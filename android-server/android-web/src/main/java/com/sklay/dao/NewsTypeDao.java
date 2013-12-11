package com.sklay.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sklay.model.NewsType;

public interface NewsTypeDao extends JpaRepository<NewsType, Long> {

}
