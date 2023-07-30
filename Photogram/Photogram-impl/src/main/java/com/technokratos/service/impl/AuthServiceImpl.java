package com.technokratos.service.impl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.technokratos.dto.request.AccountSignInRequest;
import com.technokratos.dto.request.AccountSignUpRequest;
import com.technokratos.dto.response.AuthResponse;
import com.technokratos.exception.*;
import com.technokratos.mapper.AuthMapper;
import com.technokratos.model.jooq.enums.AccountState;
import com.technokratos.model.jooq.tables.pojos.AccountEntity;
import com.technokratos.model.jooq.tables.pojos.RefreshTokenEntity;
import com.technokratos.security.JwtUtil;
import com.technokratos.service.AuthService;
import com.technokratos.service.RefreshTokenService;
import com.technokratos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.technokratos.security.JwtUtil.REFRESH_TOKEN_EXPIRE_DAYS;

@Component
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final Environment environment;
    private final JwtUtil jwtUtil;
    private final JavaMailSender mailSender;
    private final AuthMapper authMapper;

    @Override
    public AuthResponse authenticate(UUID refreshToken) {
        String invalidToken = "Invalid refresh roken";

        RefreshTokenEntity token = refreshTokenService
                .findTokenByName(refreshToken)
                .orElseThrow(
                        () -> new BadRequestException(invalidToken)
                );

        LocalDateTime expire = token.getExpire();
        if (LocalDateTime.now().isAfter(expire)) {
            throw new BadRequestException("Refresh token expired");
        }

        AccountEntity account = userService
                .findAccountById(token.getAccountId())
                .orElseThrow(UserNotFoundException::new);

        token.setName(UUID.randomUUID());
        token.setExpire(LocalDateTime.now().plusDays(REFRESH_TOKEN_EXPIRE_DAYS));
        refreshTokenService.updateToken(token);

        String jwt = jwtUtil.createJwt(account.getId());

        return authMapper.getAuthResponse(jwt, token.getName().toString());
    }

    @Override
    public AuthResponse signIn(AccountSignInRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        AccountEntity account = userService
                .findAccountByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        if (account.getState().equals(AccountState.NOT_CONFIRMED)) {
            throw new ForbiddenException("You have not verified your email");
        }

        if (!passwordEncoder.matches(password, account.getHashPassword())) {
            throw new UserNotFoundException();
        }

        RefreshTokenEntity refreshToken;
        Optional<RefreshTokenEntity> optionalToken = refreshTokenService.findTokenByAccountId(account.getId());
        if (optionalToken.isEmpty()) {
            refreshToken = RefreshTokenEntity.builder()
                    .name(UUID.randomUUID())
                    .expire(LocalDateTime.now().plusDays(REFRESH_TOKEN_EXPIRE_DAYS))
                    .accountId(account.getId())
                    .build();
            refreshTokenService.createToken(refreshToken);
        } else {
            refreshToken = optionalToken.get();
            refreshToken.setName(UUID.randomUUID());
            refreshToken.setExpire(LocalDateTime.now().plusDays(REFRESH_TOKEN_EXPIRE_DAYS));
            refreshTokenService.updateToken(refreshToken);
        }

        String jwt = jwtUtil.createJwt(account.getId());

        return authMapper.getAuthResponse(jwt, refreshToken.getName().toString());
    }

    @Override
    public void signUp(AccountSignUpRequest request, String emailVerifyDestination) {
        userService.findAccountByUsername(request.getUsername()).ifPresent(object -> {
            throw new UsernameAlreadyExistsException();
        });

        userService.findAccountByEmail(request.getEmail()).ifPresent(object -> {
            throw new EmailAlreadyExistsException();
        });

        userService.createAccount(request);
        sendVerificationEmail(request, emailVerifyDestination);
    }

    @Override
    public void sendVerificationEmail(AccountSignUpRequest request, String emailVerifyDestination) {
        String addressTo = request.getEmail();
        String addressFrom = environment.getRequiredProperty("spring.mail.username");
        String sender = "Photogram";
        String subject = "Please, verify email";

        String token = jwtUtil.createEmailVerificationToken(request.getEmail());

        //language=html
        String content = request.getFirstName() + " " + request.getLastName() +
                ",<br>" +
                "Please, click on button for verify:<br>" +
                "<h3><a href=\"" +
                emailVerifyDestination + token +
                "\" target=\"_blank\">VERIFY</a></h3>" +
                "Thanks,<br>" +
                "Photogram.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        try {
            messageHelper.setFrom(addressFrom, sender);
            messageHelper.setTo(addressTo);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new InternalServerError();
        }
        mailSender.send(message);
    }

    @Override
    public void verifyEmail(String token) {
        try {
            DecodedJWT decodedJWT = jwtUtil.verify(token);
            String email = decodedJWT.getClaim("email").asString();
            Optional<AccountEntity> account = userService.findAccountByEmail(email);
            if (account.isPresent()) {
                userService.confirmAccountByEmail(account.get().getEmail());
            } else {
                throw new UserNotFoundException();
            }
        } catch (JWTVerificationException e) {
            throw new BadRequestException("Wrong verification token");
        }
    }
}
