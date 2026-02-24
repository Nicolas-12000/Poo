package com.example.biblioteca.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class UserCreateDto {
    @NotBlank
    private String name;
    @Email
    private String email;
}
