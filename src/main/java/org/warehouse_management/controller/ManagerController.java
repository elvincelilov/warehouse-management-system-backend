package org.warehouse_management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.warehouse_management.dto.user.ManagerResponseDto;
import org.warehouse_management.entity.Role;
import org.warehouse_management.service.ManagerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/managers")
@Tag(name = "Manager Management", description = "Operations related to managers")
public class ManagerController {

    private final ManagerService managerService;

    @Operation(summary = "Get warehouse managers", description = "Returns list of warehouse managers (ADMIN only)")
    @ApiResponse(responseCode = "200", description = "Warehouse managers fetched successfully")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/warehouse")
    public ResponseEntity<List<ManagerResponseDto>> warehouseManagers() {
        return ResponseEntity.ok(managerService.getManagers(Role.WAREHOUSE_MANAGER));
    }

    @Operation(summary = "Get sales managers", description = "Returns list of sales managers (ADMIN only)")
    @ApiResponse(responseCode = "200", description = "Sales managers fetched successfully")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sales")
    public ResponseEntity<List<ManagerResponseDto>> salesManagers() {
        return ResponseEntity.ok(managerService.getManagers(Role.SALES_MANAGER));
    }
}
