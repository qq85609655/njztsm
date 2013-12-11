package com.sklay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sklay.core.enums.DelStatus;
import com.sklay.dao.ProductDao;
import com.sklay.model.Product;
import com.sklay.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Override
	public List<Product> list(DelStatus delStatus) {
		return productDao.list(delStatus);
	}

	@Override
	public Product get(Long id) {
		return productDao.findOne(id);
	}

}
