package com.example.cab_app_backend.controller;

import com.example.cab_app_backend.model.User;
import com.example.cab_app_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/users")

public class UserController {

    @Autowired
    private UserService userService;

    // Register a new user
    @PostMapping("/userregister")
    public String register(@RequestBody User user) {
        return userService.register(user);
    }


    @PostMapping("/staffregister")
    public String registerstaff(@RequestBody User user) {
        return userService.registerstaff(user);
    }
    @GetMapping("/staff")
    public List<User> getStaffUsers() {
        return userService.getStaffUsers();
    }



    // Login user
    @PostMapping("/userlogin")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        Map<String, Object> response = userService.login(email, password);

        if (response.containsKey("userrole")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }


    // Delete user by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    // Update user details
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        Optional<User> existingUserOptional = userService.getUserById(id);

        if (!existingUserOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }

        User existingUser = existingUserOptional.get();

        // Update fields
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhonenumber(updatedUser.getPhonenumber());
        existingUser.setPassword(updatedUser.getPassword());  // Hash password if required


        // Save updated user
        userService.saveUser(existingUser);

        return ResponseEntity.ok("User updated successfully!");
    }


    // Get all users

    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }
       }




