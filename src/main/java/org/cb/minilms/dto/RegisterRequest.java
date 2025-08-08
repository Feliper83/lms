package org.cb.minilms.dto;

import lombok.Data;
import org.cb.minilms.entity.Role;

@Data
public class RegisterRequest {
    private String name;
    private String userName;
    private String email;
    private String libraryId;
    private String password;
    private Role role;
}
