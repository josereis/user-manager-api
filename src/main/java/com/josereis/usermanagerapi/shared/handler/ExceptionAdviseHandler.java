package com.josereis.usermanagerapi.shared.handler;

import com.josereis.usermanagerapi.domain.dto.response.ErrorResponse;
import com.josereis.usermanagerapi.shared.exception.BusinessRuleException;
import com.josereis.usermanagerapi.shared.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.time.Instant;

@RestControllerAdvice
public class ExceptionAdviseHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({ InsufficientAuthenticationException.class })
    public ErrorResponse onInsufficientAuthenticationException(HttpServletRequest req, InsufficientAuthenticationException e) {
        return ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .path(req.getRequestURI())
                .message("Unauthorized")
                .timestamp(Instant.now())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({ AuthenticationException.class })
    public ErrorResponse onAuthenticationException(HttpServletRequest req, AuthenticationException e) {
        return ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .path(req.getRequestURI())
                .message("Unauthorized")
                .timestamp(Instant.now())
                .build();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({ AccessDeniedException.class })
    public ErrorResponse onAccessDeniedException(HttpServletRequest req, AccessDeniedException e) {
        return ErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .path(req.getRequestURI())
                .message("Forbidden")
                .timestamp(Instant.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessRuleException.class)
    public ErrorResponse onBusinessRuleException(HttpServletRequest req, BusinessRuleException e) {
        return this.builderError(req.getRequestURI(), HttpStatus.BAD_REQUEST.value(), e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse onNotFoundException(HttpServletRequest req, EntityNotFoundException e) {
        return this.builderError(req.getRequestURI(), HttpStatus.NOT_FOUND.value(), e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse onNotFoundException(HttpServletRequest req, Exception e) {
        return this.builderError(req.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
    }

    private ErrorResponse builderError(String path, Integer statusCode, Exception e) {
        e.printStackTrace();

        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .message(e.getMessage())
                .status(statusCode)
                .path(path)
                .build();
    }
}
