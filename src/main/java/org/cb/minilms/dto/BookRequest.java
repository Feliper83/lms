package org.cb.minilms.dto;

import org.cb.minilms.entity.Author;

public record BookRequest(
        Long id,
        String title,
        String isbn,
        boolean available,
        Author author
) {
}