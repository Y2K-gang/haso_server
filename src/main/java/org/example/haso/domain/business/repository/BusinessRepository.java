package org.example.haso.domain.business.repository;

import org.example.haso.domain.business.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {
}
