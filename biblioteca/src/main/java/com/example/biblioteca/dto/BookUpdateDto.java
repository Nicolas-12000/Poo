package com.example.biblioteca.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Schema(description = "Payload to update a book — all fields optional")
public class BookUpdateDto {
    @Schema(description = "Title — optional")
    private String title;
    @Schema(description = "Author — optional")
    private String author;
    @Schema(description = "ISBN — optional")
    private String isbn;
    @Schema(description = "Published year — optional")
    private Integer publishedYear;
    @Schema(description = "Availability flag — optional")
    private Boolean available;
}
