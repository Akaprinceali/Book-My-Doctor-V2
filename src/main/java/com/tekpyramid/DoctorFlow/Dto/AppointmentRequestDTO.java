package com.tekpyramid.DoctorFlow.Dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentRequestDTO {
    private int userId;
    private int doctorId;
    private LocalDate date;
    private LocalTime time;
}
