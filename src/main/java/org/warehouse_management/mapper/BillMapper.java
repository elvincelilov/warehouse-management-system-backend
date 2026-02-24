package org.warehouse_management.mapper;

import org.springframework.stereotype.Component;
import org.warehouse_management.dto.bill.BillItemDto;
import org.warehouse_management.dto.bill.BillResponseDto;
import org.warehouse_management.entity.Transaction;
import org.warehouse_management.entity.TransactionType;

import java.util.List;

@Component
public class BillMapper {
    public BillResponseDto toBillDto(Transaction tx) {

        BillResponseDto bill = new BillResponseDto();
        bill.setBillNumber(tx.getBillNumber());
        bill.setDate(tx.getTransactionDate());

        if (tx.getType() == TransactionType.IN && tx.getSupplier() != null) {
            bill.setPartyName(tx.getSupplier().getSupplierName());
        } else if (tx.getType() == TransactionType.OUT && tx.getCustomer() != null) {
            bill.setPartyName(tx.getCustomer().getName());
        }

        List<BillItemDto> items = tx.getItems().stream().map(item -> {
            BillItemDto dto = new BillItemDto();
            dto.setProductName(item.getProduct().getProductName());
            dto.setQuantity(item.getQuantity());
            dto.setAmount(item.getLineTotal());
            return dto;
        }).toList();

        bill.setItems(items);
        bill.setTotalAmount(tx.getTotalAmount());
        bill.setTax(tx.getTax());
        bill.setGrandTotal(tx.getTotalAmount().add(tx.getTax()));

        return bill;
    }
}
