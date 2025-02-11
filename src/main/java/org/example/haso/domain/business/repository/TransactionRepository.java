package org.example.haso.domain.business.repository;

import org.example.haso.domain.business.model.BusinessType;
import org.example.haso.domain.business.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByUserIdAndBtype(String userId, BusinessType btype);

}
