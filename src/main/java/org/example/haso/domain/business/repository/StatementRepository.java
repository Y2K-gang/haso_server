package org.example.haso.domain.business.repository;

import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.business.model.Business;
import org.example.haso.domain.business.model.BusinessType;
import org.example.haso.domain.business.model.Statement;
import org.example.haso.domain.business.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StatementRepository extends JpaRepository<Statement, Integer> {

        Statement findByUserAndTxnId(String user, int txnId);

//        Statement findByBusinessAndTxnId(Business business, int txnId);
}
