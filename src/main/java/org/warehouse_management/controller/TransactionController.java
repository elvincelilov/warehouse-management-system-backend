package org.warehouse_management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.warehouse_management.dto.bill.BillResponseDto;
import org.warehouse_management.dto.transaction.TransactionInRequestDto;
import org.warehouse_management.dto.transaction.TransactionOutRequestDto;
import org.warehouse_management.entity.Transaction;
import org.warehouse_management.exception.NotFoundException;
import org.warehouse_management.mapper.BillMapper;
import org.warehouse_management.repository.TransactionRepository;
import org.warehouse_management.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction Management", description = "Operations for stock transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionRepository transactionRepo;
    private final BillMapper billMapper;

    @Operation(summary = "Stock In transaction")
    @ApiResponse(responseCode = "200", description = "Transaction created successfully")
    @PreAuthorize("hasAnyRole('ADMIN','SALES_MANAGER','WAREHOUSE_MANAGER')")
    @PostMapping("/in")
    public ResponseEntity<String> transactionIn(
            @RequestBody TransactionInRequestDto dto) {

        Transaction tx = transactionService.createTransactionIn(dto);
        return ResponseEntity.ok(tx.getBillNumber());
    }

    @Operation(summary = "Stock Out transaction")
    @ApiResponse(responseCode = "200", description = "Transaction created successfully")
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER','SALES_MANAGER')")
    @PostMapping("/out")
    public ResponseEntity<String> transactionOut(
            @RequestBody TransactionOutRequestDto dto) {

        Transaction tx = transactionService.createTransactionOut(dto);
        return ResponseEntity.ok(tx.getBillNumber());
    }

    @Operation(summary = "Get bill by bill number")
    @ApiResponse(responseCode = "200", description = "Bill retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Bill not found")
    @PreAuthorize("hasAnyRole('ADMIN','SALES_MANAGER','WAREHOUSE_MANAGER')")
    @GetMapping("/{billNumber}/bill")
    public ResponseEntity<BillResponseDto> getBill(
            @Parameter(description = "Bill number", example = "BILL-2024-001")
            @PathVariable String billNumber) {

        Transaction tx = transactionRepo.findByBillNumber(billNumber)
                .orElseThrow(() -> new NotFoundException("Bill not found"));

        return ResponseEntity.ok(billMapper.toBillDto(tx));
    }
}
