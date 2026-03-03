package com.example.biblioteca.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request to create a loan (lend a book)")
public class LoanRequestDto {
    @NotNull
    @Schema(description = "Id of the user borrowing the book", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long userId;
    @NotNull
    @Schema(description = "Id of the book to borrow", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Long bookId;
}
