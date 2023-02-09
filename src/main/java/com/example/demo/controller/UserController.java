package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id){
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("User %s not found", id)));
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("User %s not found", id)));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        return userRepository.save(existingUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("User %s not found", id)));
        userRepository.delete(existingUser);
        return ResponseEntity.ok().build();
    }
}
