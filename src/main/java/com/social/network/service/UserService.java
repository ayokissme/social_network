package com.social.network.service;

import com.social.network.entity.User;
import com.social.network.exception.UserAlreadyExistsException;

public interface UserService {
    String deleteUser(User user);
}
