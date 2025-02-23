package org.example.haso.domain.business.repository;

import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.business.model.Business;
import org.example.haso.domain.business.model.BusinessType;
import org.example.haso.domain.business.model.Statement;
import org.example.haso.domain.business.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StatementRepository extends JpaRepository<Statement, Integer> {

        Statement findByUserAndTxnId(String user, int txnId);

        Optional<Statement> findByTransaction_TxnId(int transactionId);

//        Statement findByBusinessAndTxnId(Business business, int txnId);
}
