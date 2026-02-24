package org.warehouse_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.warehouse_management.dto.user.ManagerResponseDto;
import org.warehouse_management.entity.Role;
import org.warehouse_management.mapper.ManagerMapper;
import org.warehouse_management.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final UserRepository userRepository;
    private final ManagerMapper mapper;

    public List<ManagerResponseDto> getManagers(Role role) {
        return userRepository.findByRole(role)
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
