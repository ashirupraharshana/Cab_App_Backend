package com.example.cab_app_backend.controller;

import com.example.cab_app_backend.dto.AuthResponse;
import com.example.cab_app_backend.dto.LoginRequest;
import com.example.cab_app_backend.dto.RegisterRequest;
import com.example.cab_app_backend.model.User;
import com.example.cab_app_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/userregister")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {

        String message = userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                java.util.Map.of(
                        "success", true,
                        "message", message
                )
        );
    }

    @PostMapping("/staffregister")
    public ResponseEntity<?> registerStaff(@Valid @RequestBody RegisterRequest request) {

        String message = userService.registerStaff(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                java.util.Map.of(
                        "success", true,
                        "message", message
                )
        );
    }

    @PostMapping("/userlogin")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

        AuthResponse response = userService.login(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/staff")
    public ResponseEntity<?> getStaffUsers() {
        return ResponseEntity.ok(userService.getStaffUsers());
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {

        userService.deleteUser(id);

        return ResponseEntity.ok(
                java.util.Map.of(
                        "success", true,
                        "message", "User deleted successfully"
                )
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User updatedUser) {

        User existingUser = userService.getUserById(id)
                .orElseThrow(() -> new com.example.cab_app_backend.exception.UserNotFoundException("User not found"));

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhonenumber(updatedUser.getPhonenumber());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            existingUser.setPassword(updatedUser.getPassword());
        }

        userService.saveUser(existingUser);

        return ResponseEntity.ok(
                java.util.Map.of(
                        "success", true,
                        "message", "User updated successfully"
                )
        );
    }
}