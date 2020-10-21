package com.azure.ad.authentication.exception;

import com.microsoft.aad.adal4j.AuthenticationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Arpit Khandelwal.
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        return createResponseEntity(status, headers, errors, ex.getLocalizedMessage());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleNotFountExceptions1(Exception ex, WebRequest request) {
        List<String> errors = Collections.EMPTY_LIST;
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        String detailedException = ex.getLocalizedMessage();
        if(ex.getCause() instanceof AuthenticationException){
            AuthenticationException autEx = (AuthenticationException) ex.getCause();
            status = HttpStatus.UNAUTHORIZED;
            errors = new ArrayList<String>();
            errors.add("Error validating credentials due to invalid username or password");
            detailedException = autEx.getLocalizedMessage();
        }else {
            errors = Collections.singletonList(ex.getLocalizedMessage());
        }
        return createResponseEntity(status, null, errors, detailedException);
    }

    /**
     * Create an ResponseEntity object using passed status and error list
     * @param status
     * @param errors
     * @return ResponseEntity
     */
    private ResponseEntity<Object> createResponseEntity(HttpStatus status, HttpHeaders headers, List<String> errors, String detailedException) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("errors", errors);
        body.put("detailed-exception", detailedException);
        return new ResponseEntity<>(body, headers, status);
    }
}