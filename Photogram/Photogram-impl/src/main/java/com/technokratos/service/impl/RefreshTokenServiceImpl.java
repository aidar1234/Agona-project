package com.technokratos.service.impl;

import com.technokratos.model.jooq.tables.pojos.RefreshTokenEntity;
import com.technokratos.repository.RefreshTokenRepository;
import com.technokratos.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public Long createToken(RefreshTokenEntity refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshTokenEntity> findTokenByName(UUID name) {
        return refreshTokenRepository.findByName(name);
    }

    @Override
    public void updateToken(RefreshTokenEntity refreshToken) {
        refreshTokenRepository.update(refreshToken);
    }

    @Override
    public Optional<RefreshTokenEntity> findTokenByAccountId(UUID id) {
        return refreshTokenRepository.findByAccountId(id);
    }

    @Override
    public void deleteTokenByAccountId(UUID id) {
        refreshTokenRepository.deleteByAccountId(id);
    }
}
