package com.tekpyramid.DoctorFlow.Service;

import com.tekpyramid.DoctorFlow.Dto.LeaveStatusUpdateDTO;
import com.tekpyramid.DoctorFlow.Entity.Admin;
import com.tekpyramid.DoctorFlow.Entity.LeaveRequest;

import java.util.List;

public interface AdminService {
    List<LeaveRequest> getPendingLeaves();
    LeaveRequest updateLeaveStatus(LeaveStatusUpdateDTO dto);

    Admin getAdminByName(String username);
}
