package com.example.biblioteca.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoanRequestDto {
    @NotNull
    private Long userId;
    @NotNull
    private Long bookId;
}
