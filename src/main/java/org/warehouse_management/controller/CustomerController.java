package org.warehouse_management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.warehouse_management.dto.customer.CustomerCreateDto;
import org.warehouse_management.dto.customer.CustomerResponseDto;
import org.warehouse_management.dto.customer.CustomerUpdateDto;
import org.warehouse_management.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Tag(name = "Customer Management", description = "Operations related to customer management")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Create customer", description = "Creates a new customer (ADMIN only)")
    @ApiResponse(responseCode = "201", description = "Customer created successfully")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<String> create(
            @RequestBody @Valid CustomerCreateDto dto) {
        customerService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer created");
    }

    @Operation(summary = "List customers", description = "Returns paginated list of customers")
    @ApiResponse(responseCode = "200", description = "Customers fetched successfully")
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
    @GetMapping
    public ResponseEntity<Page<CustomerResponseDto>> list(
            @Parameter(description = "Filter by customer name")
            @RequestParam(required = false) String name,
            Pageable pageable) {
        return ResponseEntity.ok(customerService.getCustomers(name, pageable));
    }

    @Operation(summary = "Get customer details", description = "Returns customer by ID")
    @ApiResponse(responseCode = "200", description = "Customer found")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> detail(
            @Parameter(description = "Customer ID", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(customerService.getById(id));
    }

    @Operation(summary = "Update customer", description = "Updates customer information")
    @ApiResponse(responseCode = "200", description = "Customer updated successfully")
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<String> update(
            @PathVariable Long id,
            @RequestBody CustomerUpdateDto dto) {
        customerService.update(id, dto);
        return ResponseEntity.ok("Customer updated");
    }

    @Operation(summary = "Delete customer", description = "Deletes customer by ID")
    @ApiResponse(responseCode = "200", description = "Customer deleted successfully")
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.ok("Customer deleted");
    }
}
