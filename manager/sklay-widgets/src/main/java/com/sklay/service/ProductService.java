package com.sklay.service;

import java.util.List;

import com.sklay.core.enums.DelStatus;
import com.sklay.model.Product;

public interface ProductService {

	public List<Product> list(DelStatus delStatus);
	
	public Product get(Long id) ;
}
