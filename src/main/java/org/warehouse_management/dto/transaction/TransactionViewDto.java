package org.warehouse_management.dto.transaction;

import lombok.Data;
import org.warehouse_management.entity.TransactionType;

import java.math.BigDecimal;

@Data
public class TransactionViewDto {

    private String billNumber;
    private TransactionType type; // IN / OUT

    private String productName;
    private BigDecimal quantity;
    private BigDecimal amount;
}
