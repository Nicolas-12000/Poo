package com.example.biblioteca.service.impl;

import com.example.biblioteca.dto.BookCreateDto;
import com.example.biblioteca.dto.BookDto;
import com.example.biblioteca.dto.BookUpdateDto;
import com.example.biblioteca.mapper.BookMapper;
import com.example.biblioteca.model.Book;
import com.example.biblioteca.repository.BookRepository;
import com.example.biblioteca.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repo;

    public BookServiceImpl(BookRepository repo) {
        this.repo = repo;
    }

    @Override
    public BookDto create(BookCreateDto dto) {
        Book b = BookMapper.toEntity(dto);
        return BookMapper.toDto(repo.save(b));
    }

    @Override
    public List<BookDto> listAll() {
        return repo.findAll().stream().map(BookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BookDto getById(Long id) {
        return repo.findById(id).map(BookMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public BookDto update(Long id, BookUpdateDto dto) {
        Book b = repo.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        BookMapper.updateEntity(b, dto);
        return BookMapper.toDto(repo.save(b));
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
