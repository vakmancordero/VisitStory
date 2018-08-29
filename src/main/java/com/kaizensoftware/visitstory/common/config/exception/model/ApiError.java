package com.kaizensoftware.visitstory.common.config.exception.model;


import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.sql.Timestamp;
import java.util.Calendar;

import lombok.Data;

@Data
public class ApiError {

    private long timestamp;
    private int status;
    private String error;
    private String exception;
    private Object message;
    private String technicalMessage;
    private String path;

    private ApiError() {
        Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
        timestamp = currentTimestamp.getTime();
    }

    public ApiError(Throwable ex, HttpStatus status, WebRequest request) {
        this();
        this.status = status.value();
        this.error  = status.getReasonPhrase();
        this.exception = ex.getClass().getTypeName();
        this.message = ex.getMessage();
        this.path  = ((ServletWebRequest) request).getRequest().getServletPath();
    }

    public ApiError(Throwable ex, HttpStatus status, String path) {
        this();
        this.status = status.value();
        this.error  = status.getReasonPhrase();
        this.exception = ex.getClass().getTypeName();
        this.message = ex.getMessage();
        this.path  = path;
    }

    public ApiError(Throwable ex, String message, HttpStatus status, WebRequest request) {
        this();
        this.status = status.value();
        this.error  = status.getReasonPhrase();
        this.exception = ex.getClass().getTypeName();
        this.message = message;
        this.technicalMessage = ex.getMessage();
        this.path  = ((ServletWebRequest) request).getRequest().getServletPath();
    }

    public ApiError(ValidationException ex, String message, HttpStatus status, WebRequest request) {
        this();
        this.status = status.value();
        this.error  = status.getReasonPhrase();
        this.exception = ex.getClass().getTypeName();
        this.message = StringUtils.isEmpty(ex.getBody()) ? message : ex.getBody();
        this.technicalMessage = ex.getMessage();
        this.path  = ((ServletWebRequest) request).getRequest().getServletPath();
    }

    public ApiError(Throwable ex, String message, HttpStatus status) {
        this();
        this.status = status.value();
        this.error  = status.getReasonPhrase();
        this.exception = ex.getClass().getTypeName();
        this.message = message;
        this.technicalMessage = ex.getMessage();
        this.path = null;
    }

}

