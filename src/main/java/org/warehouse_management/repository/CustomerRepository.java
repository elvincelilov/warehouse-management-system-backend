package org.warehouse_management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.warehouse_management.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByCustomerId(String customerId);

    Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
