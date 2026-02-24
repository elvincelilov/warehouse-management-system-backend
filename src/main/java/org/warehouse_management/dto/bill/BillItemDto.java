package org.warehouse_management.dto.bill;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillItemDto {
    private String productName;
    private BigDecimal quantity;
    private BigDecimal amount;
}
