package com.example.biblioteca.mapper;

import com.example.biblioteca.dto.BookCreateDto;
import com.example.biblioteca.dto.BookDto;
import com.example.biblioteca.dto.BookUpdateDto;
import com.example.biblioteca.model.Book;

public class BookMapper {
    public static BookDto toDto(Book b) {
        if (b == null) return null;
        BookDto d = new BookDto();
        d.setId(b.getId());
        d.setTitle(b.getTitle());
        d.setAuthor(b.getAuthor());
        d.setIsbn(b.getIsbn());
        d.setPublishedYear(b.getPublishedYear());
        d.setAvailable(b.getAvailable());
        return d;
    }

    public static Book toEntity(BookCreateDto d) {
        if (d == null) return null;
        return Book.builder()
                .title(d.getTitle())
                .author(d.getAuthor())
                .isbn(d.getIsbn())
                .publishedYear(d.getPublishedYear())
                .available(true)
                .build();
    }

    public static void updateEntity(Book book, BookUpdateDto u) {
        if (u.getTitle() != null) book.setTitle(u.getTitle());
        if (u.getAuthor() != null) book.setAuthor(u.getAuthor());
        if (u.getIsbn() != null) book.setIsbn(u.getIsbn());
        if (u.getPublishedYear() != null) book.setPublishedYear(u.getPublishedYear());
        if (u.getAvailable() != null) book.setAvailable(u.getAvailable());
    }
}
