package com.tekpyramid.DoctorFlow.Service;

import com.tekpyramid.DoctorFlow.Constants.Status;
import com.tekpyramid.DoctorFlow.Dto.LeaveStatusUpdateDTO;
import com.tekpyramid.DoctorFlow.Entity.Admin;
import com.tekpyramid.DoctorFlow.Entity.LeaveRequest;
import com.tekpyramid.DoctorFlow.Repository.AdminRepository;
import com.tekpyramid.DoctorFlow.Repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private LeaveRepository leaveRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public List<LeaveRequest> getPendingLeaves() {
        // ✅ Fetch only pending leaves
        return leaveRepository.findByStatus(Status.PENDING);
    }

    @Override
    public LeaveRequest updateLeaveStatus(LeaveStatusUpdateDTO dto) {
        // ✅ Find leave
        LeaveRequest leave = leaveRepository.findById(dto.getLeaveId())
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        // ✅ Validate status
        if (!dto.getStatus().equalsIgnoreCase(Status.ACCEPTED) &&
                !dto.getStatus().equalsIgnoreCase(Status.REJECTED)) {
            throw new RuntimeException("Invalid status. Allowed: Accepted / Rejected");
        }

        // ✅ Update and save
        leave.setStatus(dto.getStatus());
        return leaveRepository.save(leave);
    }

    @Override
    public Admin getAdminByName(String username) {
        return adminRepository.findByAdminName(username)
                .orElseThrow(() -> new RuntimeException("Admin not found with name: " + username));
    }
}
