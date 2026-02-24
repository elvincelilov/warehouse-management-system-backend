package org.warehouse_management.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="suppliers")
@Data
@NoArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // DB PK

    private String supplierId;   // SUP-001

    private String supplierName;

    private String emailId;

    private String mobileNo;

    private String contactNo;

    private String address;

    @Column(length = 1000)
    private String details;

    private String remark;
}
