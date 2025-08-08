package org.cb.minilms.dto;

import org.cb.minilms.entity.Book;
import org.cb.minilms.entity.User;

import java.time.LocalDate;

public record BorrowingRecordResponse(
        Long id,
        User user,
        Book book,
        LocalDate borrowDate,
        LocalDate dueDate,
        LocalDate returnedDate
) {
}
