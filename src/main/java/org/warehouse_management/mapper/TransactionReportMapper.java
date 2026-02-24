package org.warehouse_management.mapper;

import org.springframework.stereotype.Component;
import org.warehouse_management.dto.transaction.TransactionReportDto;
import org.warehouse_management.entity.Transaction;

@Component
public class TransactionReportMapper {
    public TransactionReportDto toDto(Transaction t) {

        TransactionReportDto dto = new TransactionReportDto();
        dto.setTransactionId(t.getId());
        dto.setTransactionDate(t.getTransactionDate());
        dto.setAmount(t.getTotalAmount());
        dto.setPaid(t.getPaid());
        dto.setBalance(t.getBalance());

        return dto;
    }
}
