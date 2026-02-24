package org.warehouse_management.dto.bill;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BillResponseDto {
    private String billNumber;
    private LocalDateTime date;

    private String partyName;

    private List<BillItemDto> items;

    private BigDecimal totalAmount;
    private BigDecimal tax;
    private BigDecimal grandTotal;
}
