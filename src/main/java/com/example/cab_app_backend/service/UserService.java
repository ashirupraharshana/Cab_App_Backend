package com.example.cab_app_backend.service;

import com.example.cab_app_backend.dto.AuthResponse;
import com.example.cab_app_backend.dto.LoginRequest;
import com.example.cab_app_backend.dto.RegisterRequest;
import com.example.cab_app_backend.exception.DuplicateEmailException;
import com.example.cab_app_backend.exception.InvalidCredentialsException;
import com.example.cab_app_backend.exception.UserNotFoundException;
import com.example.cab_app_backend.model.User;
import com.example.cab_app_backend.repository.UserRepository;
import com.example.cab_app_backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already in use");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhonenumber(request.getPhonenumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserrole(2);
        user.setStatus(0);

        userRepository.save(user);

        return "User registered successfully";
    }

    public String registerStaff(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already in use");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhonenumber(request.getPhonenumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserrole(1);
        user.setStatus(0);

        userRepository.save(user);

        return "Staff registered successfully";
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!passwordMatches) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user);

        return new AuthResponse(
                "Login successful",
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getUserrole(),
                token
        );
    }

    public List<User> getStaffUsers() {
        return userRepository.findByUserrole(1);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean deleteUser(String id) {

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found");
        }

        userRepository.deleteById(id);
        return true;
    }
}