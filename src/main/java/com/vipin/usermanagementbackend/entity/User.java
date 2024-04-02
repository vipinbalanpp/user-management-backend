package com.vipin.usermanagementbackend.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;


@Document(collection = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private Roles role;
    private String profileImageUrl;


    public User(String username, String password, String profileImageUrl) {
        this.username=username;
        this.password=password;
        this.profileImageUrl=profileImageUrl;
    }
}