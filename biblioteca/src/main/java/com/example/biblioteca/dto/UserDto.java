package com.example.biblioteca.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Schema(description = "Representation of a user")
public class UserDto {
    @Schema(description = "User id", example = "1")
    private Long id;
    @Schema(description = "Full name", example = "Juan Perez")
    private String name;
    @Schema(description = "Email address", example = "juan@example.com")
    private String email;
}
