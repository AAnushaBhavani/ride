package com.rideapp.controller;

import com.rideapp.dto.AuthRequest;
import com.rideapp.dto.AuthResponse;
import com.rideapp.dto.RegisterRequest;
import com.rideapp.model.User;
import com.rideapp.security.JwtUtil;
import com.rideapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            if (userService.existsByEmail(registerRequest.getEmail())) {
                return ResponseEntity.badRequest()
                    .body("Error: Email is already taken!");
            }
            
            if (userService.existsByPhone(registerRequest.getPhone())) {
                return ResponseEntity.badRequest()
                    .body("Error: Phone number is already taken!");
            }
            
            User user = userService.registerUser(registerRequest);
            
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
            
            return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getEmail(), 
                user.getName(), user.getRole().name()));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            User user = userService.findByEmail(authRequest.getEmail());
            
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
            
            return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getEmail(), 
                user.getName(), user.getRole().name()));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Error: Invalid email or password");
        }
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        
        User user = userService.findByEmail(email);
        
        return ResponseEntity.ok(user);
    }
}
