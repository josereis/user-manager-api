package com.josereis.usermanagerapi.shared.handler;

import com.josereis.usermanagerapi.domain.dto.response.ErrorResponse;
import com.josereis.usermanagerapi.shared.exception.BusinessRuleException;
import com.josereis.usermanagerapi.shared.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.time.Instant;

@RestControllerAdvice
public class ExceptionAdviseHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({ AuthenticationException.class })
    public ErrorResponse onAuthenticationException(HttpServletRequest req, AuthenticationException e) {
        return ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .path(req.getPathInfo())
                .message("Unauthorized")
                .timestamp(Instant.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessRuleException.class)
    public ErrorResponse onBusinessRuleException(HttpServletRequest req, BusinessRuleException e) {
        return this.builderError(req.getPathInfo(), 400, e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse onNotFoundException(HttpServletRequest req, EntityNotFoundException e) {
        return this.builderError(req.getPathInfo(), 404, e);
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
