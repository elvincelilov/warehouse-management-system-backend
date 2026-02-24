package org.warehouse_management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.warehouse_management.dto.login.LoginRequestDto;
import org.warehouse_management.dto.login.LoginResponseDto;
import org.warehouse_management.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication and login operations")
public class AuthController {

    private final UserService userService;

    @Operation(
            summary = "User login",
            description = "Authenticates user and returns JWT token"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(userService.login(dto));
    }
}
