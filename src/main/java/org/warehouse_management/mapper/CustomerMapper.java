package org.warehouse_management.mapper;

import org.springframework.stereotype.Component;
import org.warehouse_management.dto.customer.CustomerCreateDto;
import org.warehouse_management.dto.customer.CustomerResponseDto;
import org.warehouse_management.dto.customer.CustomerUpdateDto;
import org.warehouse_management.entity.Customer;

@Component
public class CustomerMapper {
    public Customer toEntity(CustomerCreateDto dto) {
        Customer c = new Customer();
        c.setCustomerId(dto.getCustomerId());
        c.setName(dto.getName());
        c.setEmailId(dto.getEmailId());
        c.setMobileNo(dto.getMobileNo());
        c.setContactNo(dto.getContactNo());
        c.setAddress(dto.getAddress());
        c.setDetails(dto.getDetails());
        c.setRemark(dto.getRemark());
        return c;
    }

    public CustomerResponseDto toDto(Customer c) {
        CustomerResponseDto dto = new CustomerResponseDto();
        dto.setId(c.getId());
        dto.setCustomerId(c.getCustomerId());
        dto.setName(c.getName());
        dto.setEmailId(c.getEmailId());
        dto.setMobileNo(c.getMobileNo());
        dto.setContactNo(c.getContactNo());
        dto.setAddress(c.getAddress());
        dto.setDetails(c.getDetails());
        dto.setRemark(c.getRemark());
        return dto;
    }

    public void updateEntity(Customer c, CustomerUpdateDto dto) {

        if (dto.getName() != null) c.setName(dto.getName());
        if (dto.getEmailId() != null) c.setEmailId(dto.getEmailId());
        if (dto.getMobileNo() != null) c.setMobileNo(dto.getMobileNo());
        if (dto.getContactNo() != null) c.setContactNo(dto.getContactNo());
        if (dto.getAddress() != null) c.setAddress(dto.getAddress());
        if (dto.getDetails() != null) c.setDetails(dto.getDetails());
        if (dto.getRemark() != null) c.setRemark(dto.getRemark());
    }
}
