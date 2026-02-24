package org.warehouse_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.warehouse_management.dto.product.ProductCreateDto;
import org.warehouse_management.dto.product.ProductResponseDto;
import org.warehouse_management.dto.product.ProductUpdateDto;
import org.warehouse_management.entity.Product;
import org.warehouse_management.exception.AlreadyExistsException;
import org.warehouse_management.exception.NotFoundException;
import org.warehouse_management.mapper.ProductMapper;
import org.warehouse_management.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public void createProduct(ProductCreateDto dto) {
        if(productRepository.existsByProductId(dto.getProductId())) {
            throw new AlreadyExistsException("Product already exists");
        }

        Product product = productMapper.toEntity(dto);
        productRepository.save(product);
    }
    public Page<ProductResponseDto> getProducts(
            String name,
            Pageable pageable
    ) {

        if (name == null || name.isBlank()) {
            return productRepository.findAll(pageable)
                    .map(productMapper::toDto);
        }

        return productRepository
                .findByProductNameContainingIgnoreCase(name, pageable)
                .map(productMapper::toDto);
    }

    public ProductResponseDto getProductById(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        return productMapper.toDto(p);
    }

    public void updateProduct(Long id, ProductUpdateDto dto) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        productMapper.updateEntity(p, dto);
        productRepository.save(p);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
