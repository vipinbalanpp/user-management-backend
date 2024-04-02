package com.vipin.usermanagementbackend.service;

import com.vipin.usermanagementbackend.dto.UserDetailsDto;
import com.vipin.usermanagementbackend.dto.UserDto;
import com.vipin.usermanagementbackend.entity.User;

import java.util.List;

public interface UserService {



    List<UserDetailsDto> getAllUsers();

    void register(UserDto userDto);


    void editUser(String id, UserDto userDto);

    boolean existsById(String id);

    boolean existsByUsername(String username, String id);


    void deleteUser(String id);

    User getCurrentUser();

    void editProfile(UserDto userDto);


    boolean existsByUsername(String username);

    User getUserByUsername(String username);

    UserDetailsDto getUserDetails(String username);
}
