package org.example.haso.domain.product.repository;

import org.example.haso.domain.business.model.Business;
import org.example.haso.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.userId = :userId")
    List<Product> findByUserId(@Param("userId") String userId);

    @Query("SELECT p FROM Product p WHERE p.userId.userId = :userId ORDER BY p.createdDate DESC")
    List<Product> findByUserIdOrderByCreatedDateDesc(@Param("userId") String userId);
}
