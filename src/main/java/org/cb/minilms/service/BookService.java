package org.cb.minilms.service;

import lombok.RequiredArgsConstructor;
import org.cb.minilms.dto.BookRequest;
import org.cb.minilms.dto.BookResponse;
import org.cb.minilms.entity.Book;
import org.cb.minilms.mapper.BookMapper;
import org.cb.minilms.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    protected static final BookMapper mapper = BookMapper.INSTANCE;

    private final BookRepository bookRepo;

    public BookResponse addBook(BookRequest book) {
        return mapper.toDtoResponse(bookRepo.save(mapper.toEntity(book)));
    }

    public List<BookResponse> searchBooks(String title, String author, String isbn) {
        if (title != null) return getBooks(bookRepo.findByTitleContainingIgnoreCase(title));
        if (author != null) return getBooks(bookRepo.findByAuthorNameContainingIgnoreCase(author));
        if (isbn != null) return getBooks(bookRepo.findByIsbn(isbn).map(List::of).orElse(List.of()));
        return getBooks(bookRepo.findAll());
    }

    private List<BookResponse> getBooks(List<Book> bookList){
        return bookList.stream().map(book -> mapper.toDtoResponse(book)).toList();
    }

    public BookResponse getBook(Long id) {
        return bookRepo.findById(id)
                .map(book -> mapper.toDtoResponse(book))
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }
}