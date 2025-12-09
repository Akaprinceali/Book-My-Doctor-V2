package com.tekpyramid.DoctorFlow.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
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
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;   // Must match `role_role_id` foreign key type

    private String roleName;

    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL)
    private List<User> userList;

    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL)
    private List<Doctor>doctorList;

    @OneToOne(mappedBy = "role")
    @JsonIgnoreProperties("role")
    private Admin admin;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private List<AppUser> appUsers = Lists.newArrayList();
}
