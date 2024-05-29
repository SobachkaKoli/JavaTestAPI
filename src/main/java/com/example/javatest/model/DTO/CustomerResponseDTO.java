package com.example.javatest.model.DTO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomerResponseDTO {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
}
