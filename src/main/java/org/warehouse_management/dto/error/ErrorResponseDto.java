package org.warehouse_management.dto.error;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDto {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
