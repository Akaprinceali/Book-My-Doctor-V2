package com.tekpyramid.DoctorFlow.Controller;

import com.tekpyramid.DoctorFlow.Constants.Specialist;
import com.tekpyramid.DoctorFlow.Dto.*;
import com.tekpyramid.DoctorFlow.Entity.*;
import com.tekpyramid.DoctorFlow.JwtUtils.JwtUtils;
import com.tekpyramid.DoctorFlow.Repository.AppUserRepository;
import com.tekpyramid.DoctorFlow.Repository.RoleRepository;
import com.tekpyramid.DoctorFlow.Response.ApiResponse;
import com.tekpyramid.DoctorFlow.Response.Success;
import com.tekpyramid.DoctorFlow.Service.AdminService;
import com.tekpyramid.DoctorFlow.Service.DoctorService;
import com.tekpyramid.DoctorFlow.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
@Slf4j
public class AppUserController {

    private final DoctorService doctorService;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final AppUserRepository appUserRepository;
    private final AdminService adminService;

    // 🔹 Doctor Registration
// 🔹 Doctor Registration
    @PostMapping("/doctor/register")
    public ResponseEntity<Success> registerDoctor(@RequestBody DoctorSignUpDTO doctorDto) {
        log.info("Doctor registration: {}", doctorDto);

        String doctorId = doctorService.signUpDoctor(doctorDto);

        Success successResponse = new Success();
        successResponse.setMessage("Doctor registered successfully");
        successResponse.setError(false);
        successResponse.setHttpStatus(HttpStatus.CREATED);
        successResponse.setData(doctorId);

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    // 🔹 User Registration
    @PostMapping("/user/register")
    public ResponseEntity<Success> registerUser(@RequestBody UserSignUpDTO userDto) {
        log.info("User registration: {}", userDto);

        String userId = userService.signUpUser(userDto);

        Success successResponse = new Success();
        successResponse.setMessage("User registered successfully");
        successResponse.setError(false);
        successResponse.setHttpStatus(HttpStatus.CREATED);
        successResponse.setData(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }


    // 🔹 Common Login Endpoint
    @PostMapping(path = "/login")
    public ResponseEntity<ApiResponse<?>> registerEmployee(@RequestBody LoginRequest loginDTO) throws IOException {
        log.info("Application controller:registerTrainer execution start, {}", loginDTO);

        //check user & password

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );

        Optional<AppUser> byUsername = appUserRepository.findByUsername(loginDTO.getUsername());

        AppUser appUser = byUsername.get();
        ApiResponse<Object> response = ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .token(jwtUtils.generateToken(loginDTO.getUsername()))
                .build();


        String role = appUser.getRoles().get(0).getRoleName().substring(5);

        if(role.equals("USER")){

            User user = userService.getUserByName(loginDTO.getUsername());
            UserResponse userResponse=new UserResponse();
            userResponse.setRole(role);
            BeanUtils.copyProperties(user,userResponse);
            response.setMessage("User login successfully");
            response.setData(userResponse);

        } else if (role.equals("DOCTOR")) {

            Doctor doctor=doctorService.getDoctorByName(loginDTO.getUsername());
            DoctorResponse doctorResponse=new DoctorResponse();
            doctorResponse.setRole(role);
            BeanUtils.copyProperties(doctor,doctorResponse);
            response.setMessage("Doctor login successfully");
            response.setData(doctorResponse);

        } else {

            Admin admin=adminService.getAdminByName(loginDTO.getUsername());
            AdminResponse adminResponse=new AdminResponse();
            adminResponse.setRole(role);
            BeanUtils.copyProperties(admin,adminResponse);
            response.setMessage("Admin login successfully");
            response.setData(adminResponse);

        }


        return ResponseEntity.ok(response);
    }


    @GetMapping("/roles")
    public ResponseEntity<ApiResponse<?>> getAllRoles(){

        List<Role> all = roleRepository.findAll();

        List<String> roles = all.stream().map(role -> role.getRoleName().substring(5)).collect(Collectors.toList());

        ApiResponse<List<String>> response=new ApiResponse<>();

        response.setData(roles);
        response.setMessage("Roles accessed successfully");
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(response);

    }

    @GetMapping("/specialist")
    public ResponseEntity<ApiResponse> getAllSpeciality(){

        List<String> speciality = List.of(Specialist.CARDIOLOGY, Specialist.NEUROLOGY, Specialist.ORTHOPEDICS, Specialist.PEDIATRICS);
        ApiResponse<List<String>> response=new ApiResponse<>();

        response.setData(speciality);
        response.setMessage("Roles accessed successfully");
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(response);

    }

}
