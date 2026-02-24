package org.warehouse_management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.warehouse_management.dto.transaction.TransactionViewDto;
import org.warehouse_management.entity.TransactionType;
import org.warehouse_management.service.TransactionViewService;

import java.util.List;

@RestController
@RequestMapping("/api/transactions/view")
@RequiredArgsConstructor
@Tag(name = "Transaction View", description = "Search and filter transaction records")
public class TransactionViewController {

    private final TransactionViewService service;

    @Operation(
            summary = "Search transactions",
            description = "Search transactions by bill number and/or transaction type"
    )
    @ApiResponse(responseCode = "200", description = "Transactions fetched successfully")
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
    @GetMapping
    public ResponseEntity<List<TransactionViewDto>> search(

            @Parameter(description = "Filter by bill number", example = "BILL-2024-001")
            @RequestParam(required = false) String billNumber,

            @Parameter(description = "Transaction type (IN or OUT)", example = "IN")
            @RequestParam(required = false) TransactionType type
    ) {
        return ResponseEntity.ok(service.search(billNumber, type));
    }
}
