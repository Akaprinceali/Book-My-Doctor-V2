package com.tekpyramid.DoctorFlow.Service;

import com.tekpyramid.DoctorFlow.Constants.Status;
import com.tekpyramid.DoctorFlow.Entity.Doctor;
import com.tekpyramid.DoctorFlow.Entity.LeaveRequest;
import com.tekpyramid.DoctorFlow.Repository.DoctorRepository;
import com.tekpyramid.DoctorFlow.Repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    // ✅ Doctor applies for leave
    @Override
    public LeaveRequest applyLeave(int doctorId, LocalDate fromDate, LocalDate toDate, String reason) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        LeaveRequest leave = LeaveRequest.builder()
                .doctor(doctor)
                .fromDate(fromDate)
                .toDate(toDate)
                .reason(reason)
                .status(Status.PENDING)
                .build();

        return leaveRepository.save(leave);
    }

    // ✅ Admin views only pending leaves
    @Override
    public List<LeaveRequest> getPendingLeaves() {
        return leaveRepository.findByStatus(Status.PENDING);
    }

    // ✅ Admin updates leave status (Accept or Reject)
    @Override
    public LeaveRequest updateLeaveStatus(int leaveId, String status) {
        LeaveRequest leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        leave.setStatus(status);
        return leaveRepository.save(leave);
    }
}
