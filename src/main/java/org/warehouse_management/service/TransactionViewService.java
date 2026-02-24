package org.warehouse_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.warehouse_management.dto.transaction.TransactionViewDto;
import org.warehouse_management.entity.TransactionType;
import org.warehouse_management.mapper.TransactionViewMapper;
import org.warehouse_management.repository.TransactionItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionViewService {
    private final TransactionItemRepository itemRepo;
    private final TransactionViewMapper mapper;

    public List<TransactionViewDto> search(
            String billNumber,
            TransactionType type
    ) {
        return itemRepo.search(billNumber, type)
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
