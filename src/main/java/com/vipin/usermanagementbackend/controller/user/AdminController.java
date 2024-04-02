package com.vipin.usermanagementbackend.controller.user;
import com.vipin.usermanagementbackend.dto.UserDetailsDto;
import com.vipin.usermanagementbackend.dto.UserDto;
import com.vipin.usermanagementbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    public final UserService userService;


    @GetMapping("/get-users")
    public ResponseEntity<?> getAllUsers(){
        try {
            List<UserDetailsDto>users = userService.getAllUsers();
            return ResponseEntity.of(Optional.ofNullable(users));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while fetching users. Please try again later.");
        }
    }
    @PutMapping("/edit-user/{id}")
    public ResponseEntity<String> editUser(@PathVariable(name = "id") String id,
                                      @RequestBody UserDto userDto) {
        if(!userService.existsById(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        if(userService.existsByUsername(userDto.getUsername(),id)) return ResponseEntity.status(HttpStatus.CONFLICT).body("Username exits");
        userService.editUser(id,userDto);
        return new ResponseEntity<>("success",HttpStatus.OK);
    }
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String> deleteUser (@PathVariable("id") String id){
        if(!userService.existsById(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        userService.deleteUser(id);
        return new ResponseEntity<>("success",HttpStatus.OK);
    }
}
