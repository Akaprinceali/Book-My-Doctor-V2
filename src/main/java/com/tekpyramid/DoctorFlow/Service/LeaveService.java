package com.tekpyramid.DoctorFlow.Service;

import com.tekpyramid.DoctorFlow.Entity.LeaveRequest;
import java.time.LocalDate;
import java.util.List;

public interface LeaveService {
    LeaveRequest applyLeave(int doctorId, LocalDate fromDate, LocalDate toDate, String reason);

    List<LeaveRequest> getPendingLeaves();       // for admin
    LeaveRequest updateLeaveStatus(int leaveId, String status);
}
