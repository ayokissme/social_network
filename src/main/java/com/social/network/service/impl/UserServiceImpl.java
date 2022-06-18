package com.social.network.service.impl;

import com.social.network.entity.User;
import com.social.network.exception.UserAlreadyExistsException;
import com.social.network.repo.UserRepo;
import com.social.network.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static com.social.network.entity.enums.UserRole.ROLE_USER;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with such mail was not found"));
    }

    @Override
    public void registerUser(User user) throws UserAlreadyExistsException {
        if (userRepo.existsUserByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("User with such mail already exists");
        }

        String password = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setRoles(Collections.singleton(ROLE_USER));
        user.setPassword(password);
        userRepo.save(user);
    }

    @Override
    public User getUser(User user) {
        return null;
    }

    @Override
    public String deleteUser(User user) {
        return null;
    }
}
