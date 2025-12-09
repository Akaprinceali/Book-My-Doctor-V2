package com.tekpyramid.DoctorFlow.Entity;

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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    private String userName;
    private int age;
    private String sex;
    private String password;
    private long mobile;
    private String email;

    @ManyToOne(cascade = CascadeType.ALL)
    private Role role;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Appointment> appointentList;

    @OneToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;


}
