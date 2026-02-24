package com.example.biblioteca.service.impl;

import com.example.biblioteca.dto.LoanRequestDto;
import com.example.biblioteca.model.Book;
import com.example.biblioteca.model.Loan;
import com.example.biblioteca.model.User;
import com.example.biblioteca.repository.BookRepository;
import com.example.biblioteca.repository.LoanRepository;
import com.example.biblioteca.repository.UserRepository;
import com.example.biblioteca.service.LoanService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
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
        User user = userRepo.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepo.findById(request.getBookId()).orElseThrow(() -> new RuntimeException("Book not found"));
        if (Boolean.FALSE.equals(book.getAvailable())) {
            throw new RuntimeException("Book is not available");
        }
        book.setAvailable(false);
        bookRepo.save(book);

        Loan loan = Loan.builder()
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
        Loan loan = loanRepo.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));
        if (Boolean.TRUE.equals(loan.getReturned())) return;
        loan.setReturned(true);
        loan.setReturnDate(LocalDateTime.now());
        loanRepo.save(loan);

        Book book = loan.getBook();
        book.setAvailable(true);
        bookRepo.save(book);
    }
}
