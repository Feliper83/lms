package org.cb.minilms.dto;

public record UserResponse(
    Long id,
    String name,
    String email,
    String libraryId,
    String password
) {
}