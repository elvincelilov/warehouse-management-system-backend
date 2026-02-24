package org.warehouse_management.dto.transaction;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class TransactionItemRequestDto {
    private Long productId;
    private BigDecimal quantity;
}
