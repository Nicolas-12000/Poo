package com.example.biblioteca.service.impl;

import com.example.biblioteca.dto.LoanRequestDto;
import com.example.biblioteca.model.Loan;

import com.example.biblioteca.repository.BookRepository;
import com.example.biblioteca.repository.LoanRepository;
import com.example.biblioteca.repository.UserRepository;
import com.example.biblioteca.service.LoanService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@org.springframework.transaction.annotation.Transactional
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepo;
    private final BookRepository bookRepo;
    private final UserRepository userRepo;

    public LoanServiceImpl(LoanRepository loanRepo, BookRepository bookRepo, UserRepository userRepo) {
        this.loanRepo = loanRepo;
        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Long lend(LoanRequestDto request) {
        var user = userRepo.findById(request.getUserId()).orElseThrow(() -> new com.example.biblioteca.exception.NotFoundException("User not found"));

        // Load book and use optimistic locking (@Version) to avoid race conditions
        var book = bookRepo.findById(request.getBookId()).orElseThrow(() -> new com.example.biblioteca.exception.NotFoundException("Book not found"));
        if (Boolean.FALSE.equals(book.getAvailable())) {
            throw new com.example.biblioteca.exception.ConflictException("Book is not available");
        }

        // change state and persist; optimistic locking will fail if concurrent update happened
        book.setAvailable(false);
        bookRepo.save(book);

        var loan = Loan.builder()
                .book(book)
                .user(user)
                .loanDate(LocalDateTime.now())
                .returned(false)
                .build();

        loan = loanRepo.save(loan);
        return loan.getId();
    }

    @Override
    public void returnLoan(Long loanId) {
        var loan = loanRepo.findById(loanId).orElseThrow(() -> new com.example.biblioteca.exception.NotFoundException("Loan not found"));
        if (Boolean.TRUE.equals(loan.getReturned())) return;
        loan.setReturned(true);
        loan.setReturnDate(LocalDateTime.now());
        loanRepo.save(loan);

        var book = loan.getBook();
        book.setAvailable(true);
        bookRepo.save(book);
    }
}
