package org.warehouse_management.dto.transaction;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionReportDto {
    private Long transactionId;
    private LocalDateTime transactionDate;

    private BigDecimal amount;
    private BigDecimal paid;
    private BigDecimal balance;
}
