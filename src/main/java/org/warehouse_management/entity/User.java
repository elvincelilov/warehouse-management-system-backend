package org.warehouse_management.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String contactNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean active;
}
