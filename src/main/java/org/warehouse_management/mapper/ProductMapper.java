package org.warehouse_management.mapper;

import org.springframework.stereotype.Component;
import org.warehouse_management.dto.product.ProductCreateDto;
import org.warehouse_management.dto.product.ProductResponseDto;
import org.warehouse_management.dto.product.ProductUpdateDto;
import org.warehouse_management.entity.Product;

@Component
public class ProductMapper {
    public Product toEntity(ProductCreateDto dto) {
        Product product = new Product();
        product.setProductId(dto.getProductId());
        product.setProductName(dto.getProductName());
        product.setProductImage(dto.getProductImage());
        product.setUnit(dto.getUnit());
        product.setCostPerUnit(dto.getCostPerUnit());
        product.setDescription(dto.getDescription());
        product.setStock(dto.getStock());
        return product;
    }

    public ProductResponseDto toDto(Product p) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(p.getId());
        dto.setProductId(p.getProductId());
        dto.setProductName(p.getProductName());
        dto.setProductImage(p.getProductImage());
        dto.setUnit(p.getUnit());
        dto.setCostPerUnit(p.getCostPerUnit());
        dto.setDescription(p.getDescription());
        dto.setStock(p.getStock());
        return dto;
    }

    public void updateEntity(Product p, ProductUpdateDto dto) {
        p.setProductName(dto.getProductName());
        p.setProductImage(dto.getProductImage());
        p.setUnit(dto.getUnit());
        p.setCostPerUnit(dto.getCostPerUnit());
        p.setDescription(dto.getDescription());
        p.setStock(dto.getStock());
    }
}
