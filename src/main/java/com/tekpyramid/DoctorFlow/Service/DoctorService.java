package com.tekpyramid.DoctorFlow.Service;

import com.tekpyramid.DoctorFlow.Dto.DoctorInfoDTO;
import com.tekpyramid.DoctorFlow.Dto.DoctorSignUpDTO;
import com.tekpyramid.DoctorFlow.Entity.Appointment;
import com.tekpyramid.DoctorFlow.Entity.Doctor;
import com.tekpyramid.DoctorFlow.Entity.User;
import com.tekpyramid.DoctorFlow.Response.Success;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface DoctorService  {




    String signUpDoctor(DoctorSignUpDTO doctorDto);


    // ✅ Find doctors by name (optional)
    List<DoctorInfoDTO> findByUserNameContainingIgnoreCase(String userName);

    List<Appointment> getTodayAppointments(int doctorId);
    List<Appointment> getTomorrowAppointments(int doctorId);
    List<Appointment> getAllAppointments(int doctorId);

    List<DoctorInfoDTO> getAllDoctors();

    Doctor getDoctorByName(String username);
}



