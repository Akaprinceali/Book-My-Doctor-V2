package com.tekpyramid.DoctorFlow.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tekpyramid.DoctorFlow.Entity.Doctor;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Entity
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int leaveId;

    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
    private String status;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    private Doctor doctor;
}
