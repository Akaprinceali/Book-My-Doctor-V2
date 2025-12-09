package com.tekpyramid.DoctorFlow.Repository;

import com.tekpyramid.DoctorFlow.Entity.Doctor;
import com.tekpyramid.DoctorFlow.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Integer> {


    List<Doctor> findByUserNameContainingIgnoreCase(String userName);

    Optional<Doctor> findByUserName(String username);

    boolean existsByUserName(String userName);


}



