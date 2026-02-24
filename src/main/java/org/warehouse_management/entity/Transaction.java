package org.warehouse_management.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(unique = true)
    private String billNumber;

    @Enumerated(EnumType.STRING)
    private TransactionType type;


    @ManyToOne
    private Supplier supplier; // for IN

    @ManyToOne
    private Customer customer;  // for OUT

    private BigDecimal totalAmount;
    private BigDecimal tax;
    private BigDecimal paid;
    private BigDecimal balance;

    private String note;

    private LocalDateTime transactionDate;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<TransactionItem> items;

}
