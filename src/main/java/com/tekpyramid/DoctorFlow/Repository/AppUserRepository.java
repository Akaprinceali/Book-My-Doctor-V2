package com.tekpyramid.DoctorFlow.Repository;

import com.tekpyramid.DoctorFlow.Entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser,String> {
    Optional<AppUser> findByUsername(String username);
}

