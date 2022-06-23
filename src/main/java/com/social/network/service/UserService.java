package com.social.network.service;

import com.social.network.entity.User;
import com.social.network.exception.UserAlreadyExistsException;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    String deleteUser(User user);
}
