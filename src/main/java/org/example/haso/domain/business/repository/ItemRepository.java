package org.example.haso.domain.business.repository;

import org.example.haso.domain.business.model.Item;
import org.example.haso.domain.business.model.Statement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByStatement(Statement statement);
}
