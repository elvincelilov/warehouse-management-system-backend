package org.warehouse_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.warehouse_management.dto.customer.CustomerCreateDto;
import org.warehouse_management.dto.customer.CustomerResponseDto;
import org.warehouse_management.dto.customer.CustomerUpdateDto;
import org.warehouse_management.entity.Customer;
import org.warehouse_management.exception.AlreadyExistsException;
import org.warehouse_management.exception.NotFoundException;
import org.warehouse_management.mapper.CustomerMapper;
import org.warehouse_management.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public void create(CustomerCreateDto dto) {
        if (customerRepository.existsByCustomerId(dto.getCustomerId())) {
            throw new AlreadyExistsException("CustomerId already exists");
        }
        customerRepository.save(customerMapper.toEntity(dto));
    }

    public Page<CustomerResponseDto> getCustomers(
            String name,
            Pageable pageable
    ) {
        if (name == null || name.isBlank()) {
            return customerRepository.findAll(pageable)
                    .map(customerMapper::toDto);
        }

        return customerRepository
                .findByNameContainingIgnoreCase(name, pageable)
                .map(customerMapper::toDto);
    }



    public CustomerResponseDto getById(Long id) {
        Customer c = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        return customerMapper.toDto(c);
    }

    public void update(Long id, CustomerUpdateDto dto) {
        Customer c = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        customerMapper.updateEntity(c, dto);
        customerRepository.save(c);
    }

    public void delete(Long id) {
        customerRepository.deleteById(id);
    }


}
