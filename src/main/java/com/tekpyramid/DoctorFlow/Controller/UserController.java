package com.tekpyramid.DoctorFlow.Controller;

import com.tekpyramid.DoctorFlow.Dto.AppointmentRequestDTO;
import com.tekpyramid.DoctorFlow.Dto.DoctorInfoDTO;
import com.tekpyramid.DoctorFlow.Entity.Appointment;
import com.tekpyramid.DoctorFlow.Response.Success;
import com.tekpyramid.DoctorFlow.Service.DoctorService;
import com.tekpyramid.DoctorFlow.Service.AppointmentService;
import com.tekpyramid.DoctorFlow.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/app/user")
public class UserController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    // 🔍 1️⃣ Search Doctor by Name or Specialization
    @GetMapping("/search-doctor")
    public ResponseEntity<Success> searchDoctor(@RequestParam("keyword") String keyword) {
        List<DoctorInfoDTO> doctorList = doctorService.findByUserNameContainingIgnoreCase(keyword);

        Success successResponse = new Success();
        successResponse.setMessage("Doctor list fetched successfully");
        successResponse.setError(false);
        successResponse.setHttpStatus(HttpStatus.OK);
        successResponse.setData(doctorList);

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    // 📋 2️⃣ Get All Doctors
    @GetMapping("/all-doctors")
    public ResponseEntity<Success> getAllDoctors() {
        List<DoctorInfoDTO> doctorList = doctorService.getAllDoctors();

        Success successResponse = new Success();
        successResponse.setMessage("All doctors fetched successfully");
        successResponse.setError(false);
        successResponse.setHttpStatus(HttpStatus.OK);
        successResponse.setData(doctorList);

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    // 🩺 3️⃣ Book an Appointment
    @PostMapping("/book")
    public ResponseEntity<Success> bookAppointment(@RequestBody AppointmentRequestDTO appointmentRequestDTO) {

        // Pass data from DTO directly to service
        Appointment appointment = appointmentService.bookAppointment(
                appointmentRequestDTO.getUserId(),
                appointmentRequestDTO.getDoctorId(),
                appointmentRequestDTO.getDate(),
                appointmentRequestDTO.getTime()
        );

        // Prepare success response
        Success successResponse = new Success();
        successResponse.setMessage("Appointment booked successfully");
        successResponse.setError(false);
        successResponse.setHttpStatus(HttpStatus.CREATED);
        successResponse.setData(appointment);

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    // 📅 4️⃣ View User's Appointments
    @GetMapping("/my-appointments")
    public ResponseEntity<Success> getMyAppointments(@RequestParam("userName") int userName) {
        List<Appointment> appointmentList = appointmentService.getAppointmentsByUser(userName);

        Success successResponse = new Success();
        successResponse.setMessage("User appointments fetched successfully");
        successResponse.setError(false);
        successResponse.setHttpStatus(HttpStatus.OK);
        successResponse.setData(appointmentList);

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}
