package com.social.network.service;

import com.social.network.entity.User;
import com.social.network.exception.UserAlreadyExistsException;

public interface AuthService {
    String confirmToken(String token);
    void registerUser(User user) throws UserAlreadyExistsException;
}
