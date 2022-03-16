package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.model.TransactionDetails;

public interface TransactionDetailsDao extends JpaRepository<TransactionDetails, Long> {
}
