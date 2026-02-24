package org.warehouse_management.mapper;

import org.springframework.stereotype.Component;
import org.warehouse_management.dto.supplier.SupplierRequestDto;
import org.warehouse_management.dto.supplier.SupplierResponseDto;
import org.warehouse_management.entity.Supplier;

@Component
public class SupplierMapper {
    public Supplier toEntity(SupplierRequestDto dto) {
        Supplier supplier = new Supplier();
        supplier.setSupplierId(dto.getSupplierId());
        supplier.setSupplierName(dto.getSupplierName());
        supplier.setAddress(dto.getAddress());
        supplier.setEmailId(dto.getEmailId());
        supplier.setDetails(dto.getDetails());
        supplier.setMobileNo(dto.getMobileNo());
        supplier.setContactNo(dto.getContactNo());
        supplier.setRemark(dto.getRemark());
        return supplier;
    }

    public SupplierResponseDto toDto(Supplier entity) {
        SupplierResponseDto dto = new SupplierResponseDto();
        dto.setId(entity.getId());
        dto.setSupplierId(entity.getSupplierId());
        dto.setSupplierName(entity.getSupplierName());
        dto.setAddress(entity.getAddress());
        dto.setEmailId(entity.getEmailId());
        dto.setDetails(entity.getDetails());
        dto.setMobileNo(entity.getMobileNo());
        dto.setContactNo(entity.getContactNo());
        dto.setRemark(entity.getRemark());
        return dto;
    }

    public void updateEntity(SupplierRequestDto dto, Supplier entity) {
        entity.setSupplierName(dto.getSupplierName());
        entity.setAddress(dto.getAddress());
        entity.setEmailId(dto.getEmailId());
        entity.setDetails(dto.getDetails());
        entity.setMobileNo(dto.getMobileNo());
        entity.setContactNo(dto.getContactNo());
        entity.setRemark(dto.getRemark());
    }
}
