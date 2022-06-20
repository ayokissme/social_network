package com.social.network.service;

import com.social.network.entity.TokenConfirm;

import java.util.Optional;

public interface TokenConfirmService {
    void saveToken(TokenConfirm tokenConfirm);
    Optional<TokenConfirm> getToken(String token);
    int setConfirmedAt(String token);
}
