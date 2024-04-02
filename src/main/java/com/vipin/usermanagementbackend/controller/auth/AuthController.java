package com.vipin.usermanagementbackend.controller.auth;

import com.vipin.usermanagementbackend.dto.AuthResponseDto;
import com.vipin.usermanagementbackend.dto.LoginDto;
import com.vipin.usermanagementbackend.dto.UserDto;
import com.vipin.usermanagementbackend.entity.User;
import com.vipin.usermanagementbackend.service.UserService;
import com.vipin.usermanagementbackend.service.auth.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Autowired
    UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        if(userService.existsByUsername(userDto.getUsername())) return new ResponseEntity<>("Username exists",HttpStatus.CONFLICT);
        userService.register(userDto);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        }
        catch (Exception error){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateToken(authentication);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        return new ResponseEntity<>
                (new AuthResponseDto(token,user.getUsername(),user.getRole().name().replace("ROLE_","")), HttpStatus.OK);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        new SecurityContextLogoutHandler().logout(request, response, null);
        return new ResponseEntity<>("Logout successful", HttpStatus.OK);
    }
    @GetMapping("/connection")
    public ResponseEntity<String> checkConnection() {
        return ResponseEntity.ok("Connection successful");
    }

}
