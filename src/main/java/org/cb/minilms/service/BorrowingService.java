package org.cb.minilms.service;

import lombok.RequiredArgsConstructor;
import org.cb.minilms.dto.BorrowingRecordResponse;
import org.cb.minilms.entity.Book;
import org.cb.minilms.entity.BorrowingRecord;
import org.cb.minilms.entity.User;
import org.cb.minilms.mapper.BorrowRecordMapper;
import org.cb.minilms.repository.BookRepository;
import org.cb.minilms.repository.BorrowingRecordRepository;
import org.cb.minilms.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowingService {

    protected static final BorrowRecordMapper mapper = BorrowRecordMapper.INSTANCE;

    private final BorrowingRecordRepository recordRepo;
    private final BookRepository bookRepo;
    private final UserRepository userRepo;

    public BorrowingRecordResponse borrowBook(Long userId, Long bookId) {
        User user = userRepo.findById(userId).orElseThrow();
        Book book = bookRepo.findById(bookId).orElseThrow();

        if (!book.isAvailable()) throw new RuntimeException("Book not available");
        long activeBorrows = recordRepo.findByUserId(userId).stream()
                .filter(r -> r.getReturnedDate() == null).count();
        if (activeBorrows >= 2) throw new RuntimeException("User already borrowed max books");

        book.setAvailable(false);
        bookRepo.save(book);

        BorrowingRecord record = new BorrowingRecord();
        record.setUser(user);
        record.setBook(book);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusWeeks(2));
        return mapper.toDtoResponse(recordRepo.save(record));
    }

    public BorrowingRecordResponse returnBook(Long userId, Long bookId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<BorrowingRecord> records = recordRepo.findByUserAndReturnedDateIsNull(user);
        BorrowingRecord record = records.stream()
                .filter(r -> r.getBook().getId().equals(bookId))
                .findFirst().orElseThrow(() -> new RuntimeException("Borrow record not found"));

        record.setReturnedDate(LocalDate.now());

        Book book = record.getBook();
        book.setAvailable(true);
        bookRepo.save(book);

        return mapper.toDtoResponse(recordRepo.save(record));
    }

    public List<BorrowingRecordResponse> getBorrowedBooks(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return getBorrowBooks(recordRepo.findByUserAndReturnedDateIsNull(user));
    }

    private List<BorrowingRecordResponse> getBorrowBooks(List<BorrowingRecord> borrowingRecordList) {
        return borrowingRecordList.stream().map(borrowingRecord -> mapper.toDtoResponse(borrowingRecord)).toList();
    }

    public List<BorrowingRecordResponse> getBorrowHistory(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return recordRepo.findByUser(user)
                .stream()
                .map(r -> mapper.toDtoResponse(r))
                .toList();
    }
}