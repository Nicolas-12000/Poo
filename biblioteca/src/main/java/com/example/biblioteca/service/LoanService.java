package com.example.biblioteca.service;

import com.example.biblioteca.dto.LoanRequestDto;

public interface LoanService {
    Long lend(LoanRequestDto request);
    void returnLoan(Long loanId);
}
