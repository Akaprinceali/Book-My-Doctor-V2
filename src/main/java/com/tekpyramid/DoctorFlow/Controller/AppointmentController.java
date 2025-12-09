package com.tekpyramid.DoctorFlow.Controller;

import com.tekpyramid.DoctorFlow.Dto.AppointmentRequestDTO;
import com.tekpyramid.DoctorFlow.Entity.Appointment;
import com.tekpyramid.DoctorFlow.Service.AppointmentService;
import com.tekpyramid.DoctorFlow.Response.Success;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<Success> bookAppointment(@RequestBody AppointmentRequestDTO dto) {

        Appointment appointment = appointmentService.bookAppointment(
                dto.getUserId(),
                dto.getDoctorId(),
                dto.getDate(),
                dto.getTime()
        );

        Success successResponse = new Success();
        successResponse.setMessage("Appointment booked successfully");
        successResponse.setError(false);
        successResponse.setHttpStatus(HttpStatus.CREATED);
        successResponse.setData(appointment);

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }



    // ✅ Get appointments by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Success> getAppointmentsByUser(@PathVariable int userId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByUser(userId);

        Success successResponse = new Success();
        successResponse.setMessage("Appointments fetched successfully for user");
        successResponse.setError(false);
        successResponse.setHttpStatus(HttpStatus.OK);
        successResponse.setData(appointments);

        return ResponseEntity.ok(successResponse);
    }

    // ✅ Get appointments by Doctor ID
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<Success> getAppointmentsByDoctor(@PathVariable int doctorId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorId);

        Success successResponse = new Success();
        successResponse.setMessage("Appointments fetched successfully for doctor");
        successResponse.setError(false);
        successResponse.setHttpStatus(HttpStatus.OK);
        successResponse.setData(appointments);

        return ResponseEntity.ok(successResponse);
    }

    // ✅ Get today's appointments for doctor
    @GetMapping("/doctor/{doctorId}/today")
    public ResponseEntity<Success> getTodayAppointments(@PathVariable int doctorId) {
        List<Appointment> appointments = appointmentService.getTodayAppointments(doctorId);

        Success successResponse = new Success();
        successResponse.setMessage("Today's appointments fetched successfully");
        successResponse.setError(false);
        successResponse.setHttpStatus(HttpStatus.OK);
        successResponse.setData(appointments);

        return ResponseEntity.ok(successResponse);
    }

    // ✅ Get tomorrow's appointments for doctor
    @GetMapping("/doctor/{doctorId}/tomorrow")
    public ResponseEntity<Success> getTomorrowAppointments(@PathVariable int doctorId) {
        List<Appointment> appointments = appointmentService.getTomorrowAppointments(doctorId);

        Success successResponse = new Success();
        successResponse.setMessage("Tomorrow's appointments fetched successfully");
        successResponse.setError(false);
        successResponse.setHttpStatus(HttpStatus.OK);
        successResponse.setData(appointments);

        return ResponseEntity.ok(successResponse);
    }
}
