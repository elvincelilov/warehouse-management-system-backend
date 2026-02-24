package org.warehouse_management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.warehouse_management.dto.password.ChangePasswordRequestDto;
import org.warehouse_management.exception.InvalidPasswordException;
import org.warehouse_management.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User Operations", description = "Operations related to user profile")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Change password",
            description = "Allows authenticated user to change their password"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid password"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequestDto dto,
            Authentication authentication) throws InvalidPasswordException {

        userService.changePassword(dto, authentication.getName());
        return ResponseEntity.ok("Password changed successfully");
    }
}
