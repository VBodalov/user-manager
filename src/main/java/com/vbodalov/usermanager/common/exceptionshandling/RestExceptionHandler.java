package com.vbodalov.usermanager.common.exceptionshandling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * A class providing centralized exception handling across @RequestMapping methods through @ExceptionHandler methods.
 * Created to encapsulate errors with more details than default exception handling methods.
 */

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles HttpMessageNotReadableException. Happens when request JSON is malformed.
     *
     * @param exception a HttpMessageNotReadableException from which the detailed message to be written in response
     * @param headers   just overridden HttpHeaders argument not to be written to the response
     * @param status    just overridden HttpStatus argument not to be written to the response
     * @param request   just overridden WebRequest argument not to be written to the response
     * @return an exception response with more details than default
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ApiError apiError = ApiError.builder()
                .status(BAD_REQUEST)
                .message("Malformed JSON request!")
                .debugMessage(exception.getLocalizedMessage())
                .build();
        return buildResponseEntity(apiError);
    }

    /**
     * Handles EntityNotFoundException. Happens when entity can't be find, for example, by means of CRUD repository.
     *
     * @param exception the EntityNotFoundException from which the detailed message to be written in response
     * @return an exception response with more details than default
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
     * Build Spring HTTP response entity.
     *
     * @param apiError the customized ApiError object to return instead default exception response
     * @return exception response with customized ApiError object
     */
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
