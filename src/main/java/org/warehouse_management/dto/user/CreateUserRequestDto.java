package org.warehouse_management.dto.user;

import lombok.Data;
import org.warehouse_management.entity.Role;

@Data
public class CreateUserRequestDto {

    private String username;
    private String password;
    private String fullName;
    private String contactNumber;
    private Role role;

}
