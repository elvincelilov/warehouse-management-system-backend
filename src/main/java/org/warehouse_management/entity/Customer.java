package org.warehouse_management.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="customers")
@Data
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // DB PK

    private String customerId; // CUST-001

    private String name;

    @Column(nullable = false)
    private String emailId;

    @Column(nullable = false)
    private String mobileNo;

    private String contactNo;

    private String address;

    @Column(length = 1000)
    private String details;

    @Column(length = 500)
    private String remark;
}
