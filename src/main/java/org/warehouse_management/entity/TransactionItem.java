package org.warehouse_management.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name="transaction_items")
public class TransactionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Transaction transaction;

    @ManyToOne
    private Product product;

    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private BigDecimal lineTotal;
}
