package com.tekpyramid.DoctorFlow.Service;

import com.tekpyramid.DoctorFlow.Entity.Appointment;
import com.tekpyramid.DoctorFlow.Entity.Doctor;
import com.tekpyramid.DoctorFlow.Entity.User;
import com.tekpyramid.DoctorFlow.Repository.AppointmentRepository;
import com.tekpyramid.DoctorFlow.Repository.DoctorRepository;
import com.tekpyramid.DoctorFlow.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Appointment bookAppointment(int userId, int doctorId, LocalDate date, LocalTime time) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Appointment appointment = Appointment.builder()
                .appointmentDate(date)
                .appointmentTime(time)
                .createdDate(LocalDate.now())
                .user(user)
                .doctor(doctor)
                .build();

        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAppointmentsByUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return appointmentRepository.findByUser(user);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return appointmentRepository.findByDoctor(doctor);
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
}
