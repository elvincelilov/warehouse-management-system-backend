package org.warehouse_management.mapper;

import org.springframework.stereotype.Component;
import org.warehouse_management.dto.transaction.TransactionViewDto;
import org.warehouse_management.entity.TransactionItem;

@Component
public class TransactionViewMapper {

    public TransactionViewDto toDto(TransactionItem item) {

        TransactionViewDto dto = new TransactionViewDto();
        dto.setBillNumber(item.getTransaction().getBillNumber());
        dto.setType(item.getTransaction().getType());

        dto.setProductName(item.getProduct().getProductName());
        dto.setQuantity(item.getQuantity());
        dto.setAmount(item.getLineTotal());

        return dto;
    }
}
