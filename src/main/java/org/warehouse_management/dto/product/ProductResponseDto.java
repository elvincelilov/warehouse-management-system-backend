package org.warehouse_management.dto.product;

import lombok.Data;
import org.warehouse_management.entity.UnitType;

import java.math.BigDecimal;

@Data
public class ProductResponseDto {
    private Long id;
    private String productId;
    private String productName;
    private String productImage;
    private UnitType unit;
    private BigDecimal costPerUnit;
    private String description;
    private BigDecimal stock;
}
