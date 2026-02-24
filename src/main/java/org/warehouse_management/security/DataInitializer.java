package org.warehouse_management.security;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.warehouse_management.entity.Role;
import org.warehouse_management.entity.User;
import org.warehouse_management.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initAdmin() {
        if(!userRepository.existsByUsername("admin")) {

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin12345"));
            admin.setFullName("System Admin");
            admin.setContactNumber("0000000000");
            admin.setRole(Role.ADMIN);
            admin.setActive(true);

            userRepository.save(admin);
        }
    }
}
