package org.example.haso.domain.business.repository;

import org.example.haso.domain.business.model.Business;
import org.example.haso.domain.business.model.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StatementRepository extends JpaRepository<Statement, Long> {
        List<Statement> findByBusiness(Business business);

}
