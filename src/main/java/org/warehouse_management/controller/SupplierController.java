package org.warehouse_management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.warehouse_management.dto.supplier.SupplierRequestDto;
import org.warehouse_management.dto.supplier.SupplierResponseDto;
import org.warehouse_management.service.SupplierService;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
@Tag(name = "Supplier Management", description = "Operations related to suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @Operation(summary = "Create supplier")
    @ApiResponse(responseCode = "201", description = "Supplier created successfully")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<String> createSupplier(
            @RequestBody @Valid SupplierRequestDto dto) {

        supplierService.createSupplier(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Supplier created");
    }

    @Operation(summary = "List suppliers with pagination and sorting")
    @ApiResponse(responseCode = "200", description = "Suppliers fetched successfully")
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
    @GetMapping
    public ResponseEntity<Page<SupplierResponseDto>> getAllSuppliers(
            @Parameter(description = "Filter by supplier name")
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return ResponseEntity.ok(supplierService.getSuppliers(name, pageable));
    }
}
