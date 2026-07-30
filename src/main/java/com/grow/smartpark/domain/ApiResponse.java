package com.grow.smartpark.domain;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String status;   
    private String code;     
    private String message;  
    private T data;            
}
