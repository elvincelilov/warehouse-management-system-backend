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
import org.warehouse_management.dto.product.ProductCreateDto;
import org.warehouse_management.dto.product.ProductResponseDto;
import org.warehouse_management.dto.product.ProductUpdateDto;
import org.warehouse_management.service.ProductService;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "Operations related to product management")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Create product", description = "Creates a new product (ADMIN only)")
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<String> createProduct(
            @RequestBody @Valid ProductCreateDto dto) {
        productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Product created successfully");
    }

    @Operation(summary = "List products", description = "Returns paginated list of products")
    @ApiResponse(responseCode = "200", description = "Products fetched successfully")
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER','SALES_MANAGER')")
    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> list(
            @Parameter(description = "Filter by product name")
            @RequestParam(required = false) String name,
            Pageable pageable) {

        return ResponseEntity.ok(productService.getProducts(name, pageable));
    }

    @Operation(summary = "Get product by ID")
    @ApiResponse(responseCode = "200", description = "Product found")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER','SALES_MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> productById(
            @Parameter(description = "Product ID", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Update product")
    @ApiResponse(responseCode = "200", description = "Product updated successfully")
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER','SALES_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<String> update(
            @PathVariable Long id,
            @RequestBody @Valid ProductUpdateDto dto) {

        productService.updateProduct(id, dto);
        return ResponseEntity.ok("Product updated");
    }

    @Operation(summary = "Delete product")
    @ApiResponse(responseCode = "200", description = "Product deleted successfully")
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER','SALES_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted");
    }
}
