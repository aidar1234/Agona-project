package com.technokratos.controller.v1;

import com.technokratos.api.v1.AuthApi;
import com.technokratos.dto.request.AccountSignUpRequest;
import com.technokratos.dto.request.AccountSignInRequest;
import com.technokratos.dto.response.AuthResponse;
import com.technokratos.security.AuthUtil;
import com.technokratos.security.AuthenticationPrinciple;
import com.technokratos.service.AuthService;
import com.technokratos.service.RefreshTokenService;
import com.technokratos.validation.validator.user.AccountSignInValidator;
import com.technokratos.validation.validator.user.AccountSignUpValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final AuthUtil authUtil;
    private final AccountSignUpValidator signUpValidator;
    private final AccountSignInValidator signInValidator;

    @Override
    public ResponseEntity<AuthResponse> authenticate(UUID refreshToken) {
        AuthResponse response = authService.authenticate(refreshToken);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AuthResponse> signIn(AccountSignInRequest request) {
        signInValidator.validate(request);
        AuthResponse response = authService.signIn(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public void signUp(AccountSignUpRequest request) {
        signUpValidator.validate(request);
        String emailVerifyDestination = "http://localhost:8080/api/v1/auth/verify/";
        authService.signUp(request, emailVerifyDestination);
    }

    @Override
    public void logout() {
        AuthenticationPrinciple principle = authUtil.getPrincipal();
        refreshTokenService.deleteTokenByAccountId(principle.getId());
    }

    @Override
    public void verifyEmail(String token) {
        authService.verifyEmail(token);
    }
}
