package com.tekpyramid.DoctorFlow.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tekpyramid.DoctorFlow.Entity.Doctor;
import com.tekpyramid.DoctorFlow.Entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int appointmentId;

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private LocalDate createdDate;

    @ManyToOne
    @JoinColumn(name = "user_id")     // ✅ better for DB clarity
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "doctor_id")   // ✅ same here
    @JsonIgnore
    private Doctor doctor;
}
