package com.technokratos.exception.handler;

import com.technokratos.dto.response.ErrorResponse;
import com.technokratos.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(RuntimeException exception, WebRequest request, HttpServletRequest httpServletRequest) {
        String message = exception.getMessage();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(message == null ? "Bad request" : message)
                .status(400)
                .path(httpServletRequest.getServletPath())
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<Object> handleInternalServerError(RuntimeException exception, WebRequest request, HttpServletRequest httpServletRequest) {
        String message = exception.getMessage();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(message == null ? "Internal server error" : message)
                .status(500)
                .path(httpServletRequest.getServletPath())
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(RuntimeException exception, WebRequest request, HttpServletRequest httpServletRequest) {
        String message = exception.getMessage();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(message == null ? "User not found" : message)
                .status(404)
                .path(httpServletRequest.getServletPath())
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleEmailArleadyExistsException(RuntimeException exception, WebRequest request, HttpServletRequest httpServletRequest) {
        String message = exception.getMessage();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(message == null ? "User with this email already exists" : message)
                .status(400)
                .path(httpServletRequest.getServletPath())
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Object> handleUsernameAlreadyExistsException(RuntimeException exception, WebRequest request, HttpServletRequest httpServletRequest) {
        String message = exception.getMessage();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(message == null ? "User with this username already exists" : message)
                .status(400)
                .path(httpServletRequest.getServletPath())
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<Object> handlePasswordsMismatchException(RuntimeException exception, WebRequest request, HttpServletRequest httpServletRequest) {
        String message = exception.getMessage();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(message == null ? "Old password does not match the entered" : message)
                .status(400)
                .path(httpServletRequest.getServletPath())
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(PublicationNotFoundException.class)
    public ResponseEntity<Object> handlePublicationNotFoundException(RuntimeException exception, WebRequest request, HttpServletRequest httpServletRequest) {
        String message = exception.getMessage();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(message == null ? "Publication not found" : message)
                .status(404)
                .path(httpServletRequest.getServletPath())
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbiddenException(RuntimeException exception, WebRequest request, HttpServletRequest httpServletRequest) {
        String message = exception.getMessage();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(message == null ? "Forbidden" : message)
                .status(403)
                .path(httpServletRequest.getServletPath())
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Object> handleFileNotFoundException(RuntimeException exception, WebRequest request, HttpServletRequest httpServletRequest) {
        String message = exception.getMessage();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(message == null ? "File not found" : message)
                .status(404)
                .path(httpServletRequest.getServletPath())
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
