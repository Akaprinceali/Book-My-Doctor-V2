package com.tekpyramid.DoctorFlow.Response;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String token;
    private T data;


}