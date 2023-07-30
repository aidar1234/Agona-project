package com.technokratos.service;

import com.technokratos.dto.request.AccountSignInRequest;
import com.technokratos.dto.request.AccountSignUpRequest;
import com.technokratos.dto.response.AuthResponse;

import java.util.UUID;

public interface AuthService {

    AuthResponse authenticate(UUID refreshToken);

    AuthResponse signIn(AccountSignInRequest request);

    void signUp(AccountSignUpRequest request, String emailVerifyDestination);

    void sendVerificationEmail(AccountSignUpRequest request, String emailVerifyDestination);

    void verifyEmail(String token);
}
