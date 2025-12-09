package com.tekpyramid.DoctorFlow.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appUserId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private boolean active = true;

    // ✅ Bidirectional relationship
    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Doctor doctor;

    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL)
    private User user;

    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("appUser")
    private Admin admin;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "map_users_roles",
            joinColumns = @JoinColumn(name = "user_fk"),
            inverseJoinColumns = @JoinColumn(name = "role_fk")
    )
    @JsonIgnoreProperties("appUsers")
    private List<Role> roles = Lists.newArrayList();
}
