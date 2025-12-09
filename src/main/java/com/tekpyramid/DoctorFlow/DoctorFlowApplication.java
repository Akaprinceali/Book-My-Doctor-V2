package com.tekpyramid.DoctorFlow;

import com.google.common.collect.Lists;
import com.tekpyramid.DoctorFlow.Entity.Admin;
import com.tekpyramid.DoctorFlow.Entity.AppUser;
import com.tekpyramid.DoctorFlow.Entity.Role;
import com.tekpyramid.DoctorFlow.Repository.AdminRepository;
import com.tekpyramid.DoctorFlow.Repository.AppUserRepository;
import com.tekpyramid.DoctorFlow.Repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class DoctorFlowApplication {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AdminRepository adminRepository;
    private final AppUserRepository appUserRepository;

    public static void main(String[] args) {
        SpringApplication.run(DoctorFlowApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {

            if (roleRepository.count() == 0) {

                // --- Create roles ---
                Role userRole = Role.builder().roleName("ROLE_USER").build();
                Role doctorRole = Role.builder().roleName("ROLE_DOCTOR").build();
                Role adminRole = Role.builder().roleName("ROLE_ADMIN").appUsers(Lists.newArrayList()).build();

                roleRepository.save(userRole);
                roleRepository.save(doctorRole);

                // --- Create admin entity ---
                Admin admin01 = Admin.builder()
                        .adminId("ADMIN01")
                        .adminName("admin01")
                        .adminPassword(passwordEncoder.encode("qwerty"))
                        .role(adminRole)
                        .status("")
                        .build();

                // --- Create AppUser tied to admin ---
                AppUser adminCredentials = AppUser.builder()
                        .username("admin01")
                        .password(passwordEncoder.encode("qwerty"))
                        .roles(Lists.newArrayList(adminRole))   // Add role immediately
                        .admin(admin01)
                        .build();

                adminRole.getAppUsers().add(adminCredentials);

                // Save adminRole *after* linking user
                roleRepository.save(adminRole);

                // Save admin + app user
                admin01.setAppUser(adminCredentials);
                adminRepository.save(admin01);
                appUserRepository.save(adminCredentials);

                System.out.println("✅ Roles and Admin initialized successfully!");
            }
        };
    }

}
