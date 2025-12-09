package com.tekpyramid.DoctorFlow.Dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class DoctorInfoDTO {

    private String userName;
    private long mobile;
    private String Specialist;
    private double rating;
}
