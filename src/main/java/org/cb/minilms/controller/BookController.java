package org.cb.minilms.controller;

import lombok.RequiredArgsConstructor;
import org.cb.minilms.dto.BookRequest;
import org.cb.minilms.dto.BookResponse;
import org.cb.minilms.entity.Book;
import org.cb.minilms.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(book));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<BookResponse>> search(@RequestParam(required=false) String title,
                             @RequestParam(required=false) String author,
                             @RequestParam(required=false) String isbn) {
        return ResponseEntity.ok(bookService.searchBooks(title, author, isbn));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<BookResponse> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }
}

