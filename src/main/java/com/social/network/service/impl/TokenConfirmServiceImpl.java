package com.social.network.service.impl;

import com.social.network.entity.TokenConfirm;
import com.social.network.repo.TokenConfirmRepo;
import com.social.network.service.TokenConfirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TokenConfirmServiceImpl implements TokenConfirmService {

    private final TokenConfirmRepo tokenConfirmRepo;

    @Autowired
    public TokenConfirmServiceImpl(TokenConfirmRepo tokenConfirmRepo) {
        this.tokenConfirmRepo = tokenConfirmRepo;
    }

    @Override
    public void saveToken(TokenConfirm tokenConfirm) {
        tokenConfirmRepo.save(tokenConfirm);
    }

    @Override
    public Optional<TokenConfirm> getToken(String token) {
        return tokenConfirmRepo.findByToken(token);
    }

    @Override
    public int setConfirmedAt(String token) {
        return tokenConfirmRepo.updateConfirmedAt(token, LocalDateTime.now());
    }
}
