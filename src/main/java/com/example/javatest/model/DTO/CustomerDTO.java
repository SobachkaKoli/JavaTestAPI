package com.example.javatest.model.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter

public class CustomerDTO {
    private Long created;
    private Long updated;
    private String fullName;
    private String email;
    private String phone;
    private Boolean isActive;
}
