package org.warehouse_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.warehouse_management.dto.supplier.SupplierRequestDto;
import org.warehouse_management.dto.supplier.SupplierResponseDto;
import org.warehouse_management.entity.Supplier;
import org.warehouse_management.exception.AlreadyExistsException;
import org.warehouse_management.exception.NotFoundException;
import org.warehouse_management.mapper.SupplierMapper;
import org.warehouse_management.repository.SupplierRepository;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public void createSupplier(SupplierRequestDto dto ) {
        if(supplierRepository.existsBySupplierId(dto.getSupplierId())){
            throw new AlreadyExistsException("Supplier already exists");
        }
        supplierRepository.save(supplierMapper.toEntity(dto));
    }

    public Page<SupplierResponseDto> getSuppliers(
            String name,
            Pageable pageable
    ) {
        if (name == null || name.isBlank()) {
            return supplierRepository.findAll(pageable)
                    .map(supplierMapper::toDto);
        }

        return supplierRepository
                .findBySupplierNameContainingIgnoreCase(name, pageable)
                .map(supplierMapper::toDto);
    }

    public SupplierResponseDto getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier not found"));
        return supplierMapper.toDto(supplier);
    }

    public void updateSupplier(Long id, SupplierRequestDto dto) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier not found"));
        supplierMapper.updateEntity(dto, supplier);
        supplierRepository.save(supplier);
    }

    public void deleteSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier not found"));
        supplierRepository.delete(supplier);
    }

}
