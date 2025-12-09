package com.tekpyramid.DoctorFlow.Service;

import com.tekpyramid.DoctorFlow.Dto.UserSignUpDTO;
import com.tekpyramid.DoctorFlow.Entity.AppUser;
import com.tekpyramid.DoctorFlow.Entity.Role;
import com.tekpyramid.DoctorFlow.Entity.User;
import com.tekpyramid.DoctorFlow.Repository.AppUserRepository;
import com.tekpyramid.DoctorFlow.Repository.RoleRepository;
import com.tekpyramid.DoctorFlow.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;

    @Override
    public String signUpUser(UserSignUpDTO userDto) {

        // ✅ Check if username already exists
        if (userRepository.existsByUserName(userDto.getUserName())) {
            return "Username already exists!";
        }

        // ✅ Create new User entity and copy DTO properties
        User user = new User();
        BeanUtils.copyProperties(userDto, user);

        // ✅ Encode password
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found in database"));

        user.setRole(role);// assign Role entity

        AppUser appUser=new AppUser();

        appUser.setUser(user);
        appUser.setPassword(user.getPassword());
        appUser.setRoles(List.of(role));
        appUser.setUsername(user.getUserName());

        appUserRepository.save(appUser);
        user.setAppUser(appUser);

        userRepository.save(user);
        

        userRepository.save(user);

        return "User registered successfully!";
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    // ✅ Get user by name
    @Override
    public User getUserByName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("User not found with name: " + userName));
    }

    // ✅ Get all users
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ Search users by keyword (if needed)
    @Override
    public List<User> searchUsers(String keyword) {
        return userRepository.findByUserNameContainingIgnoreCase(keyword);
    }



}
