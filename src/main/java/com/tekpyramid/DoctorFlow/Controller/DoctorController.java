package com.tekpyramid.DoctorFlow.Controller;

import com.tekpyramid.DoctorFlow.Dto.DoctorInfoDTO;
import com.tekpyramid.DoctorFlow.Dto.LeaveRequestDTO;
import com.tekpyramid.DoctorFlow.Entity.Appointment;
import com.tekpyramid.DoctorFlow.Entity.LeaveRequest;
import com.tekpyramid.DoctorFlow.Response.Success;
import com.tekpyramid.DoctorFlow.Service.DoctorService;
import com.tekpyramid.DoctorFlow.Service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/app/doctor")
public class DoctorController {

    @Autowired
    public DoctorService doctorService;
    @Autowired
    private LeaveService leaveService;

    @GetMapping("/")
    public String request(){
        return "<h1>Hello World</h1>";
    }


    @GetMapping("/doctor-info")
    public ResponseEntity<Success> findDoctor(@RequestParam ("DoctorName") String userName){
        List<DoctorInfoDTO> doctorlist = doctorService.findByUserNameContainingIgnoreCase(userName);
        Success successResponce = new Success();
        successResponce.setMessage(" Doctor info fetched successful");
        successResponce.setError(false);
        successResponce.setHttpStatus(HttpStatus.ACCEPTED);
        successResponce.setData(doctorlist);

        return ResponseEntity.status(HttpStatus.OK).body(successResponce);

    }

    // ✅ 1️⃣ View Today's Appointments
    @GetMapping("/appointments/today")
    public ResponseEntity<Success> getTodayAppointments(@RequestParam("doctorId") int doctorId) {
        List<Appointment> appointments = doctorService.getTodayAppointments(doctorId);

        Success response = new Success();
        response.setMessage("Today's appointments fetched successfully");
        response.setError(false);
        response.setHttpStatus(HttpStatus.OK);
        response.setData(appointments);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // ✅ 2️⃣ View Tomorrow's Appointments
    @GetMapping("/appointments/tomorrow")
    public ResponseEntity<Success> getTomorrowAppointments(@RequestParam("doctorId") int doctorId) {
        List<Appointment> appointments = doctorService.getTomorrowAppointments(doctorId);

        Success response = new Success();
        response.setMessage("Tomorrow's appointments fetched successfully");
        response.setError(false);
        response.setHttpStatus(HttpStatus.OK);
        response.setData(appointments);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // ✅ 3️⃣ View All Appointments
    @GetMapping("/appointments/all")
    public ResponseEntity<Success> getAllAppointments(@RequestParam("doctorId") int doctorId) {
        List<Appointment> appointments = doctorService.getAllAppointments(doctorId);

        Success response = new Success();
        response.setMessage("All appointments fetched successfully");
        response.setError(false);
        response.setHttpStatus(HttpStatus.OK);
        response.setData(appointments);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // ✅ 4️⃣ Apply for Leave
    @PostMapping("/apply-leave")
    public ResponseEntity<Success> applyForLeave(@RequestBody LeaveRequestDTO leaveRequestDTO) {

        LeaveRequest leave = leaveService.applyLeave(
                leaveRequestDTO.getDoctorId(),
                leaveRequestDTO.getFromDate(),
                leaveRequestDTO.getToDate(),
                leaveRequestDTO.getReason()
        );

        Success response = new Success();
        response.setMessage("Leave request submitted successfully");
        response.setError(false);
        response.setHttpStatus(HttpStatus.CREATED);
        response.setData(leave);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
