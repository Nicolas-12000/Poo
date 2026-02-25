package com.example.biblioteca.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Schema(description = "Payload to create a new book")
public class BookCreateDto {
    @NotBlank
    @Schema(description = "Book title", requiredMode = Schema.RequiredMode.REQUIRED, example = "The Pragmatic Programmer")
    private String title;

    @Schema(description = "Author name", example = "Andrew Hunt")
    private String author;

    @Schema(description = "ISBN code", example = "9780201616224")
    private String isbn;

    @Schema(description = "Published year", example = "1999")
    private Integer publishedYear;
}
