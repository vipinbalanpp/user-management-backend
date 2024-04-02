package com.vipin.usermanagementbackend.controller.user;
import com.vipin.usermanagementbackend.dto.UserDetailsDto;
import com.vipin.usermanagementbackend.dto.UserDto;
import com.vipin.usermanagementbackend.entity.User;
import com.vipin.usermanagementbackend.service.UserService;
import com.vipin.usermanagementbackend.service.auth.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    public final UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    @Autowired
    UserDetailsService userDetailsService;
    @GetMapping("/message")
    public String message(){
        return "hello ";
    }
    @GetMapping("/get-user-info/{username}")
    public ResponseEntity<?> getUserInfo(@PathVariable(name = "username") String username){
        if(!userService.existsByUsername(username)) return ResponseEntity.status(HttpStatus.CONFLICT).body("Username exits");
        UserDetailsDto user = userService.getUserDetails(username);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @PostMapping("/check-username-exists")
    public ResponseEntity<?> checkUsernameExists(@RequestBody String username){
        Boolean isExist = userService.existsByUsername(username,userService.getCurrentUser().getId());
        return new ResponseEntity<>(isExist,HttpStatus.OK);
    }

    @PutMapping("/edit-profile")
    public ResponseEntity<String> editProfile(@RequestBody UserDto userDto){
         String id = userService.getCurrentUser().getId();
        if(userService.existsByUsername(userDto.getUsername(),id)) return ResponseEntity.status(HttpStatus.CONFLICT).body("Username exits");
        userService.editProfile(userDto);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        String token = jwtService.generateToken(authToken);
return new ResponseEntity<>(token,HttpStatus.OK);
    }

}
