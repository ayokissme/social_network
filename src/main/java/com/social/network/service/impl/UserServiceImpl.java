package com.social.network.service.impl;

import com.social.network.entity.TokenConfirm;
import com.social.network.entity.User;
import com.social.network.exception.UserAlreadyExistsException;
import com.social.network.repo.UserRepo;
import com.social.network.service.EmailService;
import com.social.network.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static com.social.network.entity.enums.UserRole.USER;
import static com.social.network.entity.enums.UserStatus.NOT_CONFIRMED;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));
    }

    @Override
    public String deleteUser(User user) {
        return null;
    }
}
