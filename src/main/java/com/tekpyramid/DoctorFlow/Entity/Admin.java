package com.tekpyramid.DoctorFlow.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Entity
public class Admin {

    @Id
    private String adminId;

    private String adminName;
    private String adminPassword;

    // ✅ Explicitly define the join column for Role
    @ManyToOne
    @JoinColumn(name = "role_id")// foreign key column in admin table
    @JsonIgnore
    private Role role;

    // ✅ Proper one-to-one link with AppUser
    @OneToOne
    @JoinColumn(name = "app_user_id", referencedColumnName = "appUserId")
    @JsonIgnore
    private AppUser appUser;

    private String status;
}
