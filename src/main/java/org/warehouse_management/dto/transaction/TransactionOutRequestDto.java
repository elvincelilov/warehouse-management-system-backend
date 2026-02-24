package org.warehouse_management.dto.transaction;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TransactionOutRequestDto {

    private Long customerId;   // "To" hissəsi (customer seçimi)

    private List<TransactionItemRequestDto> items;

    private BigDecimal tax;
    private BigDecimal paid;
    private String note;
}
