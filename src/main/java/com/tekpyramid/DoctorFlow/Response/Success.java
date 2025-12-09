package com.tekpyramid.DoctorFlow.Response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class Success {

    private String message;
    private boolean error;
    private HttpStatus httpStatus;
    private Object data;


    public Success(String userRegisteredSuccessfully, boolean b, HttpStatus httpStatus, String userId) {
    }

    public Success() {

    }
}


