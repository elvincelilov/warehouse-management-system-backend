package org.warehouse_management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.warehouse_management.dto.user.CreateUserRequestDto;
import org.warehouse_management.service.UserService;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@Tag(name = "Admin - Users", description = "Admin operations for managing users")
public class AdminUserController {

    private final UserService userService;

    @Operation(
            summary = "Create new user",
            description = "Creates a new system user. Accessible only by ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(
            @RequestBody CreateUserRequestDto dto) {
        userService.createUser(dto);
    }
}
