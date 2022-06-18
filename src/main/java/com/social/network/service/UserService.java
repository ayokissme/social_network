package com.social.network.service;

import com.social.network.entity.User;
import com.social.network.exception.UserAlreadyExistsException;

public interface UserService {
    void registerUser(User user) throws UserAlreadyExistsException;
    User getUser(User user);
    String deleteUser(User user);
}
