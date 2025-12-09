package com.tekpyramid.DoctorFlow.Service;

import com.tekpyramid.DoctorFlow.Dto.UserSignUpDTO;
import com.tekpyramid.DoctorFlow.Entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

    String signUpUser(UserSignUpDTO userDto);

    User getUserById(int userId);

    User getUserByName(String userName);

    List<User> getAllUsers();

    List<User> searchUsers(String keyword);


}




