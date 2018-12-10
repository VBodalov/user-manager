package com.vbodalov.usermanager.common.exceptionshandling;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.*;

/**
 * A class providing centralized exception handling. Created to encapsulate errors contains more
 * details than default exception handling methods.
 */

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles HttpMessageNotReadableException. Happens when request JSON is malformed.
     *
     * @param exception a HttpMessageNotReadableException from which the detailed message to be
     *                  written in response
     * @param headers   just overridden HttpHeaders argument. It doesn't take part in building the
     *                  exception response.
     * @param status    just overridden HttpStatus argument. It doesn't take part in building the
     *                  exception response.
     * @param request   just overridden WebRequest argument. It doesn't take part in building the
     *                  exception response.
     * @return an exception response contains more details than default exception
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        ApiError apiError = ApiError.builder()
                .status(BAD_REQUEST)
                .message("Malformed JSON request!")
                .debugMessage(exception.getLocalizedMessage())
                .build();
        return buildResponseEntity(apiError);
    }

    /**
     * Handles EntityNotFoundException. Happens when entity can't be find, for example, by means
     * of CRUD repository.
     *
     * @param exception the EntityNotFoundException from which the detailed message to be written
     *                  in response
     * @return an exception response contains more details than default exception
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException exception) {
        ApiError apiError = ApiError.builder()
                .status(NOT_FOUND)
                .message(exception.getMessage())
                .build();
        return buildResponseEntity(apiError);
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     *
     * @param exception the ConstraintViolationException to put it to exception response
     * @return an exception response contains more details than default exception
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException exception) {

        ApiError apiError = ApiError.builder()
                .status(BAD_REQUEST)
                .message("Validation error")
                .build();
        apiError.addValidationErrors(exception.getConstraintViolations());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles DataIntegrityViolationException, inspects the cause for different DB causes.
     *
     * @param exception the DataIntegrityViolationException to inspect the cause
     * @param request   ust overridden WebRequest argument. It doesn't take part in building the
     *                  exception response.
     * @return an exception response contains more details than default exception
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(
            DataIntegrityViolationException exception,
            WebRequest request) {

        if (exception.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
            org.hibernate.exception.ConstraintViolationException cause =
                    (org.hibernate.exception.ConstraintViolationException) exception.getCause();

            String causeMessage = cause.getLocalizedMessage();
            String sqlExceptionMessage;
            if (cause.getSQLException() != null) {
                sqlExceptionMessage = cause.getSQLException().getLocalizedMessage();
            } else {
                sqlExceptionMessage = "";
            }

            ApiError apiError = ApiError.builder()
                    .status(CONFLICT)
                    .message("Database error!")
                    .debugMessage(String.format("%s %s", causeMessage, sqlExceptionMessage))
                    .build();

            return buildResponseEntity(apiError);
        }

        ApiError apiError = ApiError.builder()
                .status(INTERNAL_SERVER_ERROR)
                .message("Unexpected error!")
                .debugMessage(exception.getLocalizedMessage())
                .build();
        return buildResponseEntity(apiError);
    }

    /**
     * Builds Spring HTTP response entity.
     *
     * @param apiError the customized ApiError object to return instead default exception response
     * @return exception response with customized ApiError object
     */
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
