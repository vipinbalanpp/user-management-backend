package com.vipin.usermanagementbackend.utils;

import com.vipin.usermanagementbackend.dto.UserDetailsDto;
import com.vipin.usermanagementbackend.dto.UserDto;
import com.vipin.usermanagementbackend.entity.User;

public class UserMapper {
    public static UserDto mapToUserDto(User user){
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),

                user.getProfileImageUrl());
    }
    public static  User mapToUser(UserDto userDto){
        return  new User(
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getProfileImageUrl()
        );
    }

    public static UserDetailsDto mapToUserDetailsDto(User user) {
        return new UserDetailsDto(
                user.getId(),
                user.getUsername(),
                user.getProfileImageUrl(),
                user.getRole()
        );
    }
}
