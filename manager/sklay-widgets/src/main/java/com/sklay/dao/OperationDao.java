package com.sklay.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sklay.model.Operation;

public interface OperationDao extends JpaRepository<Operation, Long> {

}
