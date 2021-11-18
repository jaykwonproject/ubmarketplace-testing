package com.ubmarketplace.app.controller.advice;

import com.ubmarketplace.app.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.security.InvalidParameterException;

@RestControllerAdvice(basePackages = {"com.ubmarketplace.app.controller"})
public class ExceptionControllerAdvice {

    @ExceptionHandler(InvalidParameterException.class)
    public ErrorResponse invalidParameterExceptionHandler(InvalidParameterException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return ErrorResponse.builder()
                .message(e.getMessage())
                .type(e.getClass().getCanonicalName())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, HttpServletResponse response) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return ErrorResponse.builder()
                .message(objectError.getDefaultMessage())
                .type(e.getClass().getCanonicalName())
                .build();
    }

    @ExceptionHandler(NullPointerException.class)
    public ErrorResponse nullPointerExceptionHandler(NullPointerException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return ErrorResponse.builder()
                .message(e.getMessage())
                .type(e.getClass().getCanonicalName())
                .build();
    }

}
