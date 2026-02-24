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
import org.warehouse_management.dto.transaction.TransactionReportDto;
import org.warehouse_management.entity.TransactionType;
import org.warehouse_management.service.TransactionReportService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions/reports")
@RequiredArgsConstructor
@Tag(name = "Transaction Reports", description = "Monthly transaction reports")
public class TransactionReportController {

    private final TransactionReportService service;

    @Operation(summary = "Get transaction report by date range")
    @ApiResponse(responseCode = "200", description = "Report generated successfully")
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
    @GetMapping
    public ResponseEntity<Map<String, List<TransactionReportDto>>> getMonthlyReport(
            @Parameter(description = "Start date", example = "2024-01-01")
            @RequestParam LocalDate fromDate,
            @Parameter(description = "End date", example = "2024-01-31")
            @RequestParam LocalDate toDate) {

        LocalDateTime from = fromDate.atStartOfDay();
        LocalDateTime to = toDate.atTime(23, 59, 59);

        List<TransactionReportDto> inList =
                service.getReport(TransactionType.IN, from, to);

        List<TransactionReportDto> outList =
                service.getReport(TransactionType.OUT, from, to);

        Map<String, List<TransactionReportDto>> result = new HashMap<>();
        result.put("IN", inList);
        result.put("OUT", outList);

        return ResponseEntity.ok(result);
    }
}