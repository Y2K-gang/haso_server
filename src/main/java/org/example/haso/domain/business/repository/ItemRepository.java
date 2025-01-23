package org.example.haso.domain.business.repository;

import org.example.haso.domain.business.entity.Business;
import org.example.haso.domain.business.entity.Item;
import org.example.haso.domain.business.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByTransaction(Transaction transaction);
}
