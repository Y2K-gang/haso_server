package org.example.haso.domain.business.repository;

import org.example.haso.domain.business.model.Business;
import org.example.haso.domain.business.model.BusinessType;
import org.example.haso.domain.business.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    public List<Transaction> findByBtypeAndUserIdAndBusinessUserId(BusinessType btype, String userId, String businessUserId);


}
