package com.example.biblioteca.service;

import com.example.biblioteca.dto.BookCreateDto;
import com.example.biblioteca.dto.BookDto;
import com.example.biblioteca.dto.BookUpdateDto;

import java.util.List;

public interface BookService {
    BookDto create(BookCreateDto dto);
    List<BookDto> listAll();
    BookDto getById(Long id);
    BookDto update(Long id, BookUpdateDto dto);
    void delete(Long id);
}
