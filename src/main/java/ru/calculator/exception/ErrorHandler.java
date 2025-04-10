package ru.calculator.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error(HttpStatus.BAD_REQUEST.toString(), e.getLocalizedMessage(), e.getMessage());
        String name = Objects.requireNonNull(e.getParameter().getParameterName());
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.toString(),
                "Неправильно задан параметр",
                String.format("Поле: %s. Некорректное значения поля", name));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(final ConstraintViolationException e) {
        log.error(HttpStatus.BAD_REQUEST.toString(), e.getLocalizedMessage(), e.getMessage());
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.toString(),
                "Неправильно задан параметр",
                e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error(HttpStatus.BAD_REQUEST.toString(), e.getLocalizedMessage(), e.getMessage());
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.toString(),
                "Не задан параметр",
                String.format("Параметр: %s. Некорректное значения поля", e.getParameterName()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error(HttpStatus.BAD_REQUEST.toString(), e.getLocalizedMessage(), e.getMessage());
        String name = Objects.requireNonNull(e.getParameter().getParameterName());
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.toString(),
                "Неправильно задан параметр",
                String.format("Поле: %s. Некорректное значения поля", name));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable e) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getLocalizedMessage(), e.getMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                "Ошибка сервера",
                e.getMessage());
    }
}

