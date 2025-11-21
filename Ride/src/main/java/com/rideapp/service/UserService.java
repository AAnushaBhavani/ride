package com.rideapp.service;

import com.rideapp.dto.RegisterRequest;
import com.rideapp.model.User;
import com.rideapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User registerUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPhone(registerRequest.getPhone());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(User.UserRole.valueOf(registerRequest.getRole().toUpperCase()));
        
        // Set driver-specific fields if role is DRIVER
        if (user.getRole() == User.UserRole.DRIVER) {
            user.setCarModel(registerRequest.getCarModel());
            user.setLicensePlate(registerRequest.getLicensePlate());
            user.setCarCapacity(registerRequest.getCarCapacity());
        }
        
        return userRepository.save(user);
    }
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
    
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }
    
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    public User updateUser(Long id, User userDetails) {
        User user = findById(id);
        
        user.setName(userDetails.getName());
        user.setPhone(userDetails.getPhone());
        user.setProfileImage(userDetails.getProfileImage());
        
        if (user.getRole() == User.UserRole.DRIVER) {
            user.setCarModel(userDetails.getCarModel());
            user.setLicensePlate(userDetails.getLicensePlate());
            user.setCarCapacity(userDetails.getCarCapacity());
        }
        
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }
}
