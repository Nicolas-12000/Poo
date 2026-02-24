package com.example.biblioteca.dto;

import lombok.*;

@Data
public class BookUpdateDto {
    private String title;
    private String author;
    private String isbn;
    private Integer publishedYear;
    private Boolean available;
}
