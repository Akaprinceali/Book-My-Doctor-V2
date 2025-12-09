package com.tekpyramid.DoctorFlow.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int doctorId;
    private String userName;
    private String email;
    private String password;
    private long mobile;
    private String Specialist;
    private double rating;

    @ManyToOne
    @JoinColumn(name = "role_role_id")
    private Role role;


    @OneToMany(mappedBy = "doctor" ,cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LeaveRequest> leaveRequests;

    @OneToMany(mappedBy = "doctor" , cascade = CascadeType.ALL)
    private List<Appointment> appointmentLists;

    @OneToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;


}
