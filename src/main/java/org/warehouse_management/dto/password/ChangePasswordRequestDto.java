package org.warehouse_management.dto.password;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePasswordRequestDto {

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmNewPassword;
}
