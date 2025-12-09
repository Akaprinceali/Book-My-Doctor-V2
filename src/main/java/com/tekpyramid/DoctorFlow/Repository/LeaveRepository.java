package com.tekpyramid.DoctorFlow.Repository;

import com.tekpyramid.DoctorFlow.Entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<LeaveRequest, Integer> {

    List<LeaveRequest> findByStatus(String status);
}
