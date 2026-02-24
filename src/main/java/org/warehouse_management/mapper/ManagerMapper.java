package org.warehouse_management.mapper;

import org.springframework.stereotype.Component;
import org.warehouse_management.dto.user.ManagerResponseDto;
import org.warehouse_management.entity.User;

@Component
public class ManagerMapper {
    public ManagerResponseDto toDto(User user) {

        ManagerResponseDto dto = new ManagerResponseDto();
        dto.setId(user.getId());
        dto.setManagerName(user.getUsername());
        dto.setPhone(user.getContactNumber());
        dto.setPassword(user.getPassword()); // HASH qaytarılır

        return dto;
    }
}
