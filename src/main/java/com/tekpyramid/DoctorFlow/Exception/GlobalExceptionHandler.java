package com.tekpyramid.DoctorFlow.Exception;

import com.tekpyramid.DoctorFlow.Response.Failure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🔹 Handle duplicate record exception
    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<Failure> handleDuplicateRecord(DuplicateRecordException exception) {

        Failure failureResponse = new Failure();
        failureResponse.setData(null);
        failureResponse.setError(true);
        failureResponse.setHttpStatus(HttpStatus.CONFLICT);
        failureResponse.setMessage(exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(failureResponse);
    }

    // 🔹 Handle validation errors (e.g., @Valid DTO validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();
        List<FieldError> fieldErrors = exception.getFieldErrors();

        for (FieldError error : fieldErrors) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // 🔹 Handle all other runtime exceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Failure> handleRuntimeException(RuntimeException exception) {

        Failure failureResponse = new Failure();
        failureResponse.setData(null);
        failureResponse.setError(true);
        failureResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        failureResponse.setMessage(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failureResponse);
    }
}
