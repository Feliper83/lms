package org.cb.minilms.service;

import lombok.RequiredArgsConstructor;
import org.cb.minilms.dto.UserRequest;
import org.cb.minilms.dto.UserResponse;
import org.cb.minilms.entity.User;
import org.cb.minilms.mapper.UserMapper;
import org.cb.minilms.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    protected static final UserMapper mapper = UserMapper.INSTANCE;
    private final UserRepository userRepo;

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(mapper::toDtoResponse)
                .toList();
    }

    public UserResponse createUser(UserRequest userRequest) {
        User user = mapper.toEntity(userRequest);
        return mapper.toDtoResponse(userRepo.save(user));
    }

    public UserResponse getUser(Long id) {
        User user = findUserById(id);
        return convertToResponse(user);
    }

    private User findUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private UserResponse convertToResponse(User user) {
        return mapper.toDtoResponse(user);
    }

    public UserResponse updateUser(Long id, UserRequest userUpdated) {
        User user = findUserById(id);
        return mapper.toDtoResponse(userRepo.save(user));
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}

