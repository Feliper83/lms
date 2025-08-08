package org.cb.minilms.dto;

public record UserRequest(
        Long id,
        String name,
        String email,
        String libraryId,
        String password
) {
}
