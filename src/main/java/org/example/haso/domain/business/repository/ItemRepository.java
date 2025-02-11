package org.example.haso.domain.business.repository;

import org.example.haso.domain.business.model.Item;
import org.example.haso.domain.business.model.Statement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByStatement(Statement statement);
}
