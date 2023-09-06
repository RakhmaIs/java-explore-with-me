package ru.practicum.main.user.service;

import ru.practicum.main.user.dto.AdminUserDto;
import ru.practicum.main.user.model.User;

import java.util.List;

public interface UserService {
    AdminUserDto createUser(User user);

    void deleteUser(Long userId);

    List<User> readUsers(List<Long> idList, int from, int size);
}
