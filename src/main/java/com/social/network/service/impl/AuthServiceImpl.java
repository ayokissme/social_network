package com.social.network.service.impl;

import com.social.network.entity.TokenConfirm;
import com.social.network.entity.User;
import com.social.network.exception.UserAlreadyExistsException;
import com.social.network.repo.UserRepo;
import com.social.network.service.AuthService;
import com.social.network.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.social.network.entity.enums.UserRole.USER;
import static com.social.network.entity.enums.UserStatus.ACTIVE;
import static com.social.network.entity.enums.UserStatus.NOT_CONFIRMED;

@Service
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {

    private final TokenConfirmServiceImpl tokenService;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    @Autowired
    public AuthServiceImpl(TokenConfirmServiceImpl tokenService, EmailService emailService, BCryptPasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @Override
    public void registerUser(User user) throws UserAlreadyExistsException {
        List<User> users = userRepo.findUsersByEmailOrNickname(user.getEmail(), user.getNickname());
        if (users.size() != 0) {
            throw new UserAlreadyExistsException(getExceptionMessage(users, user));
        }

        this.saveRegisteredUserToDB(user);

        String token = UUID.randomUUID().toString();
        TokenConfirm tokenConfirm = new TokenConfirm(
                token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user
        );

        String confirmLink = "http://localhost:8081/registration/confirm?token=" + token;
        tokenService.saveToken(tokenConfirm);
        emailService.send(user.getEmail(), confirmLink);

        log.info("Registered user: {}", user.getEmail());
    }

    @Override
    public String confirmToken(String token) {
        TokenConfirm tokenConfirm = tokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (tokenConfirm.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = tokenConfirm.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        tokenService.setConfirmedAt(token);

        User user = tokenConfirm.getUser();
        user.setStatus(ACTIVE);
        userRepo.save(user);

        return "confirmed";
    }

    private String getExceptionMessage(List<User> users, User user) {
        StringBuilder exMessage = new StringBuilder();
        users.forEach(u -> {
            if (u.getEmail().equals(user.getEmail())) exMessage.append("User with this email already exists. ");
            if (u.getNickname().equals(user.getNickname())) exMessage.append("User with this nickname already exists");
        });
        return exMessage.toString();
    }

    private void saveRegisteredUserToDB(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setRoles(Collections.singleton(USER));
        user.setPassword(password);
        user.setStatus(NOT_CONFIRMED);
        userRepo.save(user);
    }
}
