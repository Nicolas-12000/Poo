package com.example.biblioteca.controller;

import com.example.biblioteca.dto.BookCreateDto;
import com.example.biblioteca.dto.BookDto;
import com.example.biblioteca.dto.BookUpdateDto;
import com.example.biblioteca.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new book", description = "Creates a book and returns the created resource")
    public ResponseEntity<BookDto> create(@Valid @RequestBody BookCreateDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    @Operation(summary = "List books", description = "Returns a list of books in the catalog")
    public ResponseEntity<List<BookDto>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by id", description = "Returns a single book resource by id")
    public ResponseEntity<BookDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a book", description = "Updates provided fields for a book")
    public ResponseEntity<BookDto> update(@PathVariable Long id, @RequestBody BookUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book", description = "Deletes a book by id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
