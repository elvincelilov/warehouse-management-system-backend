package org.warehouse_management.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private String token;
}
