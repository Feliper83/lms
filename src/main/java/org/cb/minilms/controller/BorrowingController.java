package org.cb.minilms.controller;


import lombok.RequiredArgsConstructor;
import org.cb.minilms.dto.BorrowingRecordResponse;
import org.cb.minilms.service.BorrowingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class BorrowingController {
    private final BorrowingService borrowingService;

    @PostMapping("/{userId}/borrow/{bookId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BorrowingRecordResponse> borrow(@PathVariable Long userId, @PathVariable Long bookId) {
        return ResponseEntity
                .ok()
                .body(borrowingService.borrowBook(userId, bookId));
    }

    @PutMapping("/{userId}/return/{bookId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BorrowingRecordResponse> returnBook(@PathVariable Long userId, @PathVariable Long bookId) {
        return ResponseEntity
                .ok()
                .body(borrowingService.returnBook(userId, bookId));
    }

    @GetMapping("/{userId}/books")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<BorrowingRecordResponse>> borrowedBooks(@PathVariable Long userId) {
        return ResponseEntity
                .ok()
                .body(borrowingService.getBorrowedBooks(userId));
    }

    @GetMapping("/{userId}/history")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<BorrowingRecordResponse>> history(@PathVariable Long userId) {
        return ResponseEntity
                .ok()
                .body(borrowingService.getBorrowHistory(userId));
    }
}
