package org.warehouse_management.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.warehouse_management.dto.login.LoginRequestDto;
import org.warehouse_management.dto.login.LoginResponseDto;
import org.warehouse_management.dto.password.ChangePasswordRequestDto;
import org.warehouse_management.dto.user.CreateUserRequestDto;
import org.warehouse_management.entity.User;
import org.warehouse_management.exception.InvalidPasswordException;
import org.warehouse_management.exception.NotFoundException;
import org.warehouse_management.repository.UserRepository;
import org.warehouse_management.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Transactional
    public void createUser(CreateUserRequestDto dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // 🔐
        user.setFullName(dto.getFullName());
        user.setContactNumber(dto.getContactNumber());
        user.setRole(dto.getRole());
        user.setActive(true);

        userRepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );

        String token = jwtUtil.generateToken(dto.getUsername());
        return new LoginResponseDto(token);
    }

    public void changePassword(ChangePasswordRequestDto dto,String username) throws InvalidPasswordException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Old password is incorrect");
        }

        if(!dto.getNewPassword().equals((dto.getConfirmNewPassword()))){
            throw new InvalidPasswordException("Password must be the same");
        }

        if(passwordEncoder.matches(dto.getNewPassword(), user.getPassword())){
            throw new InvalidPasswordException("New password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

}
