package com.vipin.usermanagementbackend.dto;

import com.vipin.usermanagementbackend.entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    private String id;
    private String username;
    private String profileImageUrl;
    private Roles role;
}
