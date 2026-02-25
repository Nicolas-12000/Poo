package com.example.biblioteca.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Schema(description = "Payload to create a new user")
public class UserCreateDto {
    @NotBlank
    @Schema(description = "User full name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Ana Lopez")
    private String name;
    @Email
    @Schema(description = "Email address", example = "ana@example.com")
    private String email;
}
