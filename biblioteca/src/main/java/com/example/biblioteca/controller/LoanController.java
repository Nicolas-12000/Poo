package com.example.biblioteca.controller;

import com.example.biblioteca.dto.LoanRequestDto;
import com.example.biblioteca.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }

    @PostMapping("/lend")
    @Operation(summary = "Lend a book", description = "Creates a loan for a user if the book is available")
    public ResponseEntity<Long> lend(@Valid @RequestBody LoanRequestDto req) {
        Long id = service.lend(req);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/{id}/return")
    @Operation(summary = "Return a book", description = "Marks a loan as returned and frees the book")
    public ResponseEntity<Void> returnLoan(@PathVariable Long id) {
        service.returnLoan(id);
        return ResponseEntity.noContent().build();
    }
}
