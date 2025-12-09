package com.tekpyramid.DoctorFlow.Dto;

import lombok.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LeaveRequestDTO {

    private int doctorId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
}
