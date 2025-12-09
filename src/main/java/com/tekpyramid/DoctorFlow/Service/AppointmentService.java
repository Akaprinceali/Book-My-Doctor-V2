package com.tekpyramid.DoctorFlow.Service;

import com.tekpyramid.DoctorFlow.Entity.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {

    Appointment bookAppointment(int userId, int doctorId, LocalDate date, LocalTime time);


    List<Appointment> getAppointmentsByUser(int userId);

    List<Appointment> getAppointmentsByDoctor(int doctorId);

    List<Appointment> getTodayAppointments(int doctorId);

    List<Appointment> getTomorrowAppointments(int doctorId);


}



