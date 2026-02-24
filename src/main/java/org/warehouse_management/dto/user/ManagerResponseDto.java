package org.warehouse_management.dto.user;

import lombok.Data;

@Data
public class ManagerResponseDto {
    private Long id;
    private String managerName;
    private String phone;
    private String password;
}
