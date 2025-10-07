package com.example.academictracker.service;

import com.example.academictracker.model.Role;
import com.example.academictracker.model.User;
import com.example.academictracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        // Simple validation
        if (user.getEmail() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("Email and Password are required");
        }
        // Default role as STUDENT
        if (user.getRole() == null) {
            user.setRole(Role.STUDENT);
        }
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
