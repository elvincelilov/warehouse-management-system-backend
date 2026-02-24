package org.warehouse_management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.warehouse_management.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByProductId(String productId);
    Page<Product> findByProductNameContainingIgnoreCase(
            String productName,
            Pageable pageable
    );
}
