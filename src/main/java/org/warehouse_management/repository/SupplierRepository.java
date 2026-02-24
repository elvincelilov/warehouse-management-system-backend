package org.warehouse_management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.warehouse_management.entity.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    boolean existsBySupplierId(String supplierId);

    Page<Supplier> findBySupplierNameContainingIgnoreCase(
            String supplierName,
            Pageable pageable
    );
}
