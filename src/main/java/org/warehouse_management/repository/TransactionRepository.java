package org.warehouse_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.warehouse_management.entity.Transaction;
import org.warehouse_management.entity.TransactionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByBillNumber(String billNumber);

    @Query("""
    SELECT t
    FROM Transaction t
    WHERE t.type = :type
      AND t.transactionDate BETWEEN :from AND :to
""")
    List<Transaction> findByTypeAndDateRange(
            @Param("type") TransactionType type,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );
}
