package com.sklay.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sklay.core.enums.DelStatus;
import com.sklay.model.Product;

public interface ProductDao extends JpaRepository<Product, Long> {

	@Query("select p from Product p where p.delete = ?1")
	public List<Product> list(DelStatus delStatus) ;
}
