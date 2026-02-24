package org.warehouse_management.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.warehouse_management.entity.UnitType;

import java.math.BigDecimal;

@Data
public class ProductCreateDto {
    @NotBlank
    private String productId;

    @NotBlank
    private String productName;

    private String productImage;

    @NotNull
    private UnitType unit;

    @NotNull
    @Positive
    private BigDecimal costPerUnit;

    private String description;

    @NotNull
    @PositiveOrZero
    private BigDecimal stock;
}
