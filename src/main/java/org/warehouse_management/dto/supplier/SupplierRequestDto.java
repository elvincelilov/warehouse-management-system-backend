package org.warehouse_management.dto.supplier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SupplierRequestDto {

    @NotBlank
    private String supplierId;

    @NotBlank
    private String supplierName;

    @Email
    private String emailId;

    @NotBlank
    private String mobileNo;

    private String contactNo;
    private String address;
    private String details;
    private String remark;
}
