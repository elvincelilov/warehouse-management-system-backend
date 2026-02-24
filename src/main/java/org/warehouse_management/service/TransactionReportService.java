package org.warehouse_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.warehouse_management.dto.transaction.TransactionReportDto;
import org.warehouse_management.entity.TransactionType;
import org.warehouse_management.mapper.TransactionReportMapper;
import org.warehouse_management.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionReportService {
    private final TransactionRepository transactionRepo;
    private final TransactionReportMapper mapper;

    public List<TransactionReportDto> getReport(
            TransactionType type,
            LocalDateTime from,
            LocalDateTime to
    ) {
        return transactionRepo
                .findByTypeAndDateRange(type, from, to)
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
