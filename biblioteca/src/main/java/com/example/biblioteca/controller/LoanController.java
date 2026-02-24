package com.example.biblioteca.controller;

import com.example.biblioteca.dto.LoanRequestDto;
import com.example.biblioteca.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }

    @PostMapping("/lend")
    public ResponseEntity<Long> lend(@Valid @RequestBody LoanRequestDto req) {
        Long id = service.lend(req);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<Void> returnLoan(@PathVariable Long id) {
        service.returnLoan(id);
        return ResponseEntity.noContent().build();
    }
}
