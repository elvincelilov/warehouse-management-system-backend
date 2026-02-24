package org.warehouse_management.dto.customer;

import lombok.Data;

@Data
public class CustomerResponseDto {
    private Long id;
    private String customerId;
    private String name;
    private String emailId;
    private String mobileNo;
    private String contactNo;
    private String address;
    private String details;
    private String remark;
}
