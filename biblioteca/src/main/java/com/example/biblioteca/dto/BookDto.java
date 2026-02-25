package com.example.biblioteca.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Schema(description = "Representation of a book returned by the API")
public class BookDto {
    @Schema(description = "Database id of the book", example = "1")
    private Long id;

    @Schema(description = "Title of the book", example = "Clean Code")
    private String title;

    @Schema(description = "Author name", example = "Robert C. Martin")
    private String author;

    @Schema(description = "ISBN code", example = "9780132350884")
    private String isbn;

    @Schema(description = "Year of publication", example = "2008")
    private Integer publishedYear;

    @Schema(description = "Availability status")
    private Boolean available;
}
