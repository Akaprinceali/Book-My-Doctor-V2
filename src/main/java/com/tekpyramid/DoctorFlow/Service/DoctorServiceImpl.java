package com.tekpyramid.DoctorFlow.Service;

import com.tekpyramid.DoctorFlow.Dto.DoctorInfoDTO;
import com.tekpyramid.DoctorFlow.Dto.DoctorSignUpDTO;
import com.tekpyramid.DoctorFlow.Entity.AppUser;
import com.tekpyramid.DoctorFlow.Entity.Appointment;
import com.tekpyramid.DoctorFlow.Entity.Doctor;
import com.tekpyramid.DoctorFlow.Entity.Role;
import com.tekpyramid.DoctorFlow.Repository.AppUserRepository;
import com.tekpyramid.DoctorFlow.Repository.AppointmentRepository;
import com.tekpyramid.DoctorFlow.Repository.DoctorRepository;
import com.tekpyramid.DoctorFlow.Repository.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    // ✅ Register Doctor
    @Override
    public String signUpDoctor(DoctorSignUpDTO doctorSignUpDTO) {

        // Check if doctor already exists by username or email
        if (doctorRepository.existsByUserName(doctorSignUpDTO.getUserName())) {
            return "Doctor username already exists!";
        }

        // Create Doctor entity
        Doctor doctor = new Doctor();
        BeanUtils.copyProperties(doctorSignUpDTO, doctor);

        // Encode password
        doctor.setPassword(passwordEncoder.encode(doctorSignUpDTO.getPassword()));

        // Assign Role
        Role doctorRole = roleRepository.findByRoleName("ROLE_DOCTOR")
                .orElseThrow(() -> new RuntimeException("Doctor role not found in database"));
        doctor.setRole(doctorRole);

        // Save Doctor

        AppUser appUser=new AppUser();

        appUser.setDoctor(doctor);
        appUser.setPassword(doctor.getPassword());
        appUser.setRoles(List.of(doctorRole));
        appUser.setUsername(doctor.getUserName());

        appUserRepository.save(appUser);
        doctor.setAppUser(appUser);

        doctorRepository.save(doctor);

        return "Doctor registered successfully!";
    }


    // ✅ Find doctors by name (optional)
    @Override
    public List<DoctorInfoDTO> findByUserNameContainingIgnoreCase(String userName) {
        List<Doctor> doctors = doctorRepository.findByUserNameContainingIgnoreCase(userName);

        if (doctors.isEmpty()) {
            throw new RuntimeException("No doctors found with name: " + userName);
        }

        return doctors.stream()
                .map(doctor -> {
                    DoctorInfoDTO dto = new DoctorInfoDTO();
                    BeanUtils.copyProperties(doctor, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> getTodayAppointments(int doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return appointmentRepository.findByDoctorAndAppointmentDate(doctor, LocalDate.now());
    }

    @Override
    public List<Appointment> getTomorrowAppointments(int doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return appointmentRepository.findByDoctorAndAppointmentDate(doctor, LocalDate.now().plusDays(1));
    }

    @Override
    public List<Appointment> getAllAppointments(int doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return appointmentRepository.findByDoctor(doctor);
    }

    @Override
    public List<DoctorInfoDTO> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();

        return doctors.stream()
                .map(doc -> DoctorInfoDTO.builder()
                        .userName(doc.getUserName())
                        .Specialist(doc.getSpecialist())
                        .mobile(doc.getMobile())
                        .rating(doc.getRating())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public Doctor getDoctorByName(String username) {
        return doctorRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("Doctor not found with name: " + username));
    }

}


