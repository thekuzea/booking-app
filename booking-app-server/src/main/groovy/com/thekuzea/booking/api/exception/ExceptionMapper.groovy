package com.thekuzea.booking.api.exception

import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

import com.thekuzea.booking.api.dto.ErrorResultResource
import com.thekuzea.booking.api.dto.ValidationErrorResource

@Slf4j
@RestControllerAdvice
class ExceptionMapper {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ErrorResultResource handleException(final MethodArgumentNotValidException e) {
        final ErrorResultResource errorResult = new ErrorResultResource()
        e.getBindingResult()
                .getFieldErrors()
                .forEach { fieldError ->
                    errorResult.addErrorsItem(
                            new ValidationErrorResource()
                                    .source(fieldError.getField())
                                    .details(fieldError.getDefaultMessage())
                    )
                }

        errorResult
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    ErrorResultResource handleException(final IllegalArgumentException e) {
        createResponseWithMessage(e.getMessage())
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    ErrorResultResource handleException(final AuthenticationException e) {
        createResponseWithMessage(e.getMessage())
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ErrorResultResource handleException(final Exception e) {
        log.error("Unexpected error: ", e)
        createResponseWithMessage("Unknown error")
    }

    private static ErrorResultResource createResponseWithMessage(final String message) {
        final ErrorResultResource errorResultResource = new ErrorResultResource()
        errorResultResource.addErrorsItem(
                new ValidationErrorResource()
                        .source(null)
                        .details(message)
        )

        errorResultResource
    }
}
