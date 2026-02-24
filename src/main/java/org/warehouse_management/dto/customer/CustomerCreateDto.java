package org.warehouse_management.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerCreateDto {
    @NotBlank
    private String customerId;

    @NotBlank
    private String name;

    @Email
    private String emailId;

    @NotBlank
    private String mobileNo;

    private String contactNo;
    private String address;
    private String details;
    private String remark;
}
