package org.warehouse_management.dto.supplier;

import lombok.Data;

@Data
public class SupplierResponseDto {
    private Long id;
    private String supplierId;
    private String supplierName;
    private String emailId;
    private String mobileNo;
    private String contactNo;
    private String address;
    private String details;
    private String remark;
}
