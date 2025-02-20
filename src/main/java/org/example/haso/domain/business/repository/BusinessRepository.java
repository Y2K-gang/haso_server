package org.example.haso.domain.business.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.haso.domain.business.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Long> {
//    Business findByUserId(String userId);

    void deleteByUserId(String userId);

    Business findByUserId(String userId);

//    Optional<Business> findByUserId(String userId);


//    @Query("SELECT b FROM Business b WHERE b.userId = :userId")
//    Optional<Business> findUserByUserId(@Param("userId") String userId);

}
