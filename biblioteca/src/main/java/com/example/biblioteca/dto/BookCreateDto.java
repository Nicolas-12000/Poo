package com.example.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class BookCreateDto {
    @NotBlank
    private String title;
    private String author;
    private String isbn;
    private Integer publishedYear;
}
