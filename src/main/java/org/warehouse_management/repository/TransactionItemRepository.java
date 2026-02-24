package org.warehouse_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.warehouse_management.entity.TransactionItem;
import org.warehouse_management.entity.TransactionType;

import java.util.List;

@Repository
public interface TransactionItemRepository extends JpaRepository<TransactionItem, Long> {

    @Query("""
        SELECT ti
        FROM TransactionItem ti
        JOIN ti.transaction t
        WHERE ( t.billNumber = :billNumber)
          AND (t.type = :type)
    """)
    List<TransactionItem> search(
            @Param("billNumber") String billNumber,
            @Param("type") TransactionType type
    );
}
