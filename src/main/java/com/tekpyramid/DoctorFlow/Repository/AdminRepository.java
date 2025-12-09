package com.tekpyramid.DoctorFlow.Repository;

import com.tekpyramid.DoctorFlow.Entity.Admin;
import com.tekpyramid.DoctorFlow.Entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,String> {

    Optional<Admin> findByAdminName(String username);

    List<LeaveRequest> findByStatus(String status);
}


