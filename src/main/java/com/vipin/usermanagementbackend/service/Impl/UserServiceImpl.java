package com.vipin.usermanagementbackend.service.Impl;

import com.vipin.usermanagementbackend.dto.UserDetailsDto;
import com.vipin.usermanagementbackend.dto.UserDto;
import com.vipin.usermanagementbackend.entity.Roles;
import com.vipin.usermanagementbackend.entity.User;
import com.vipin.usermanagementbackend.exception.UserNotFoundException;
import com.vipin.usermanagementbackend.repository.UserRepository;
import com.vipin.usermanagementbackend.service.UserService;
import com.vipin.usermanagementbackend.utils.UserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service

public class UserServiceImpl implements UserService {
    public final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;



    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<UserDetailsDto> getAllUsers() {
        List<User> users = userRepository.findByRoleNot(Roles.ADMIN);
        return users.stream().map(UserMapper::mapToUserDetailsDto).collect(Collectors.toList());

    }

    @Override
    @Transactional
    public void register(UserDto userDto) {
        try {
            User user = new User();
            user.setUsername(userDto.getUsername());
            if(!Objects.equals(userDto.getProfileImageUrl(), ""))
            user.setProfileImageUrl(userDto.getProfileImageUrl());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRole(Roles.USER);
            userRepository.save(user);
        }catch (Exception e){
            throw new RuntimeException("something went wrong");
        }

    }

    @Override
    public void editUser(String id,UserDto userDto) {
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException("User Not Found");
        }
        User user=  userRepository.findById(id).get();
        user.setUsername(userDto.getUsername());
        System.out.println(userDto.getProfileImageUrl());
        if(!Objects.equals(userDto.getProfileImageUrl(), ""))
        user.setProfileImageUrl(userDto.getProfileImageUrl());
        userRepository.save(user);
    }

    @Override
    public boolean existsById(String id) {
       return userRepository.existsById(id);
    }

    @Override
    public boolean existsByUsername(String username, String id) {
      User user = userRepository.findById(id).get();
        return existsByUsername(username) && !Objects.equals(user.getUsername(),username);
    }



    @Override
    public void deleteUser(String id)  {
        userRepository.deleteById(id);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername());
    }

    @Override
    public void editProfile(UserDto userDto) {
        User user = getCurrentUser();
        if(!Objects.equals(userDto.getUsername(), null))
        user.setUsername(userDto.getUsername());
        if(!Objects.equals(userDto.getProfileImageUrl(), ""))
            user.setProfileImageUrl(userDto.getProfileImageUrl());
        userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User getUserByUsername(String username) {
       return  userRepository.findByUsername(username);
    }

    @Override
    public UserDetailsDto getUserDetails(String username) {
       User user = userRepository.findByUsername(username);
       return UserMapper.mapToUserDetailsDto(user);
    }
}
