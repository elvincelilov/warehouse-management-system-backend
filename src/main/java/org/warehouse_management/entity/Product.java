package org.warehouse_management.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name="products")
@NoArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;

    private String productName;

    private String productImage;

    @Enumerated(EnumType.STRING)
    private UnitType unit;

    private BigDecimal costPerUnit;

    @Column(length =1000)
    private String description;

    private BigDecimal stock;

}
