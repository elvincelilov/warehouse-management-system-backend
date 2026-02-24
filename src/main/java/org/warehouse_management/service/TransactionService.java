package org.warehouse_management.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.warehouse_management.dto.transaction.TransactionInRequestDto;
import org.warehouse_management.dto.transaction.TransactionItemRequestDto;
import org.warehouse_management.dto.transaction.TransactionOutRequestDto;
import org.warehouse_management.entity.*;
import org.warehouse_management.exception.NotFoundException;
import org.warehouse_management.repository.CustomerRepository;
import org.warehouse_management.repository.ProductRepository;
import org.warehouse_management.repository.SupplierRepository;
import org.warehouse_management.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepo;
    private final ProductRepository productRepo;
    private final SupplierRepository supplierRepo;
    private final CustomerRepository customerRepo;

    @Transactional
    public Transaction createTransactionIn(TransactionInRequestDto dto) {

        Transaction tx = new Transaction();
        tx.setType(TransactionType.IN);
        tx.setTransactionDate(LocalDateTime.now());
        tx.setBillNumber(generateBillNumber());

        Supplier supplier = supplierRepo.findById(dto.getSupplierId())
                .orElseThrow(() -> new NotFoundException("Supplier not found"));
        tx.setSupplier(supplier);

        List<TransactionItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (TransactionItemRequestDto itemDto : dto.getItems()) {

            Product product = productRepo.findById(itemDto.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found"));

            BigDecimal lineTotal =
                    product.getCostPerUnit().multiply(itemDto.getQuantity());

            product.setStock(product.getStock().add(itemDto.getQuantity()));

            TransactionItem item = new TransactionItem();
            item.setTransaction(tx);
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            item.setUnitPrice(product.getCostPerUnit());
            item.setLineTotal(lineTotal);

            total = total.add(lineTotal);
            items.add(item);
        }

        tx.setItems(items);
        tx.setTotalAmount(total);
        tx.setTax(dto.getTax());
        tx.setPaid(dto.getPaid());
        tx.setBalance(
                total.add(dto.getTax()).subtract(dto.getPaid())
        );
        tx.setNote(dto.getNote());

        return transactionRepo.save(tx);
    }

    @Transactional
    public Transaction createTransactionOut(TransactionOutRequestDto dto) {

        Transaction tx = new Transaction();
        tx.setType(TransactionType.OUT);
        tx.setTransactionDate(LocalDateTime.now());
        tx.setBillNumber(generateBillNumber());

        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        tx.setCustomer(customer);

        List<TransactionItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (TransactionItemRequestDto itemDto : dto.getItems()) {

            Product product = productRepo.findById(itemDto.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found"));

            if (product.getStock().compareTo(itemDto.getQuantity()) < 0) {
                throw new IllegalArgumentException("Insufficient stock");
            }

            BigDecimal lineTotal =
                    product.getCostPerUnit().multiply(itemDto.getQuantity());

            product.setStock(product.getStock().subtract(itemDto.getQuantity()));

            TransactionItem item = new TransactionItem();
            item.setTransaction(tx);
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            item.setUnitPrice(product.getCostPerUnit());
            item.setLineTotal(lineTotal);

            total = total.add(lineTotal);
            items.add(item);
        }

        tx.setItems(items);
        tx.setTotalAmount(total);
        tx.setTax(dto.getTax());
        tx.setPaid(dto.getPaid());
        tx.setBalance(
                total.add(dto.getTax()).subtract(dto.getPaid())
        );
        tx.setNote(dto.getNote());

        return transactionRepo.save(tx);
    }

    private String generateBillNumber() {
        return "BILL-" + System.currentTimeMillis();
    }
}
