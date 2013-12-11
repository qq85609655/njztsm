package com.sklay.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sklay.model.Tag;

public interface TagService {

	List<Tag> listOrderByUsed(int fetchSize);

	Page<Tag> findAll(Pageable pageable);

	void save(Tag tag);

	Tag findOne(Long id);

}
