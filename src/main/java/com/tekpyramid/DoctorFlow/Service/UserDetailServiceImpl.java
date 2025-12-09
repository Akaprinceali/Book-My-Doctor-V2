package com.tekpyramid.DoctorFlow.Service;

import com.tekpyramid.DoctorFlow.Entity.Admin;
import com.tekpyramid.DoctorFlow.Entity.AppUser;
import com.tekpyramid.DoctorFlow.Entity.Doctor;
import com.tekpyramid.DoctorFlow.Entity.User;
import com.tekpyramid.DoctorFlow.Repository.AdminRepository;
import com.tekpyramid.DoctorFlow.Repository.AppUserRepository;
import com.tekpyramid.DoctorFlow.Repository.DoctorRepository;
import com.tekpyramid.DoctorFlow.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Try user first
        User user = userRepository.findByUserName(username).orElse(null);
        if (user != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .authorities(new SimpleGrantedAuthority(user.getRole().getRoleName()))
                    .build();
        }

        // Try doctor next
        Doctor doctor = doctorRepository.findByUserName(username).orElse(null);
        if (doctor != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(doctor.getUserName())
                    .password(doctor.getPassword())
                    .authorities(new SimpleGrantedAuthority(doctor.getRole().getRoleName()))
                    .build();
        }

            // 🔹 Check in AppUser (admin credentials stored here)
            AppUser appUser = appUserRepository.findByUsername(username).orElse(null);
            if (appUser != null) {
                return org.springframework.security.core.userdetails.User.builder()
                        .username(appUser.getUsername())
                        .password(appUser.getPassword())
                        .authorities(
                                appUser.getRoles()
                                        .stream()
                                        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                                        .collect(Collectors.toList())
                        )
                        .build();
            }



            throw new UsernameNotFoundException("No user or doctor found with username: " + username);
    }
}
