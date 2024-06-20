package com.orangeKloudTask.demo.controller;

import com.orangeKloudTask.demo.Entities.User;
import com.orangeKloudTask.demo.UserDto;
import com.orangeKloudTask.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        if (userService.findByEmail(userDto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("User with this email already exists.");
        }
        if (userService.findByUsername(userDto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("User with this username already exists.");
        }
        User user = userService.registerUser(userDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserDto userDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("Login successful");
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // Get the logged-in user's username
        return ResponseEntity.ok(currentUsername);
    }

    @PostMapping("/{email}/follow")
    public ResponseEntity<?> followUser(@PathVariable String email, @RequestBody String followerEmail) {
        userService.followUser(email, followerEmail);
        return ResponseEntity.ok("Successfully followed user.");
    }

    @PostMapping("/{email}/unfollow")
    public ResponseEntity<?> unfollowUser(@PathVariable String email, @RequestBody String followerEmail) {
        userService.unfollowUser(email, followerEmail);
        return ResponseEntity.ok("Successfully unfollowed user.");
    }

    @GetMapping("/{email}/followers")
    public ResponseEntity<?> getFollowers(@PathVariable String email) {
        List<User> followers = userService.getFollowers(email);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/{email}/following")
    public ResponseEntity<?> getFollowing(@PathVariable String email) {
        List<User> following = userService.getFollowing(email);
        return ResponseEntity.ok(following);
    }
}