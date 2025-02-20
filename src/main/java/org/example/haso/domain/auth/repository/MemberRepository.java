package org.example.haso.domain.auth.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.business.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    boolean existsByUserId(String userId);
    Optional<MemberEntity> findByUserId(String userId);

}
