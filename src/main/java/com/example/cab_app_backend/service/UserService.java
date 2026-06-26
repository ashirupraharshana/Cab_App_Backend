package com.example.cab_app_backend.service;

import com.example.cab_app_backend.model.User;
import com.example.cab_app_backend.repository.UserRepository;
import com.example.cab_app_backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // Register a new user
    public String register(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return "Email already in use!";
        }

        user.setUserrole(2);

        userRepository.save(user);
        return "User registered successfully!";
    }
    public List<User> getStaffUsers() {
        return userRepository.findByUserrole(1); // Fetch only staff users
    }

    //staff reg
    public String registerstaff(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return "Email already in use!";
        }

        user.setUserrole(1);

        userRepository.save(user);
        return "User registered successfully!";
    }

    public Map<String, Object> login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);


        if (user.isPresent() && user.get().getPassword().equals(password)) {

            String token= jwtUtil.generateToken(user.get());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful!");
            response.put("userrole", user.get().getUserrole());
            response.put("userid", user.get().getId());
            response.put("token", token);
            return response;
        }

        // Return an error message
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", "Invalid email or password!");
        return errorResponse;
    }


    // Get all users
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
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

}

