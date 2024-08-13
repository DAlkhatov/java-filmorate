package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotMetException(final ConditionsNotMetException e) {
        return new ErrorResponse("Параметры запрос не соответствуют ожидаемым параметрам",
                e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(final NotFoundException e) {
        return new ErrorResponse("Ничего не найдено",
                e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorResponse handleNullPoint(final NullPointerException e) {
        return new ErrorResponse("Возвращаемый параметр равен null",
                e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleError(final Exception e) {
        return new ErrorResponse("Необработанное исключение",
                e.getMessage());
    }
}
