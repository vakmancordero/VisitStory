package com.kaizensoftware.visitstory.common.config.exception;

import com.kaizensoftware.visitstory.common.config.exception.model.ApiError;
import com.kaizensoftware.visitstory.common.config.exception.model.ValidationException;
import com.kaizensoftware.visitstory.common.util.EventMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        return handleEntityNotFound(ex, headers, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(RestClientException.class)
    protected ResponseEntity handleRestClientException(RestClientException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        return handleRestClientException(ex, headers, request);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity handleValidationException(ValidationException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        return handleValidationException(ex, headers, request);
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity handleThrowable(Throwable ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        return handleUnexpectedException(ex,headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (log.isErrorEnabled())
            log.error(ex.getMessage(), ex);

        return this.handleExceptionInternal(ex, new ApiError(ex, status, request), headers, status, request);
    }

    private ResponseEntity handleUnexpectedException(Throwable ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (log.isErrorEnabled())
            log.error(ex.getMessage(), ex);

        request.setAttribute("technicalMessage", ex.getMessage(),0);

        ApiError apiError = new ApiError(ex, EventMessage.UNEXPECTED_SERVER_ERROR.getMessage(), status, request);

        return RestExceptionHandler.handleExceptionInternal(ex, apiError, headers, status, request);
    }

    private ResponseEntity handleRestClientException(RestClientException ex, HttpHeaders headers, WebRequest request) {

        if (log.isErrorEnabled())
            log.error(ex.getMessage(), ex);

        if (ex instanceof HttpStatusCodeException) {

            HttpStatusCodeException httpClientError = ((HttpStatusCodeException) ex);

            String body = httpClientError.getResponseBodyAsString();

            try {

                ApiError apiError = objectMapper.readValue(body, ApiError.class);

                HttpStatus statusCode = HttpStatus.valueOf(apiError.getStatus());

                return this.handleExceptionInternal(ex, apiError, headers, statusCode, request);

            } catch (Exception e) {

                request.setAttribute("technicalMessage", EventMessage.UNEXPECTED_SERVER_ERROR.getMessage(), 0);

                HttpStatus statusCode = httpClientError.getStatusCode();

                ApiError apiError = new ApiError(ex, body, statusCode, request);

                return this.handleExceptionInternal(ex, apiError, headers, statusCode, request);
            }

        } else {

            HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;

            ApiError apiError = new ApiError(ex, ex.getMessage(), internalServerError, request);

            return this.handleExceptionInternal(ex, apiError, headers, internalServerError, request);

        }
    }

    private ResponseEntity handleValidationException(ValidationException ex, HttpHeaders headers, WebRequest request) {

        if (log.isErrorEnabled())
            log.error(ex.getMessage(), ex);

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        request.setAttribute("technicalMessage", ex.getMessage(),0);

        ApiError apiError = new ApiError(ex, EventMessage.UNEXPECTED_SERVER_ERROR.getMessage(), badRequest, request);

        return this.handleExceptionInternal(ex, apiError, headers, badRequest, request);
    }

    private static ResponseEntity handleExceptionInternal(Throwable ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (request != null) {
            if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
                request.setAttribute("javax.servlet.error.exception", ex, 0);
            }
        }

        return new ResponseEntity<>(body, headers, status);
    }
}

