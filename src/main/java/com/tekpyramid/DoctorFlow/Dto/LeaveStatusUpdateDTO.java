package com.tekpyramid.DoctorFlow.Dto;

import lombok.Data;

@Data
public class LeaveStatusUpdateDTO {
    private int leaveId;
    private String status; // Should be "Accepted" or "Rejected"
}
