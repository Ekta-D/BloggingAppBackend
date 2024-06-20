package com.orangeKloudTask.demo.services;

import com.orangeKloudTask.demo.Entities.User;
import com.orangeKloudTask.demo.UserDto;
import com.orangeKloudTask.demo.repository.UserRepository;
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
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void followUser(String email, String followerEmail) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found: " + email));
        User follower = userRepository.findByEmail(followerEmail).orElseThrow(() -> new RuntimeException("Follower not found: " + followerEmail));

        user.getFollowers().add(follower);
        userRepository.save(user);
    }

    public void unfollowUser(String email, String followerEmail) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found: " + email));
        User follower = userRepository.findByEmail(followerEmail).orElseThrow(() -> new RuntimeException("Follower not found: " + followerEmail));

        user.getFollowers().remove(follower);
        userRepository.save(user);
    }

    public List<User> getFollowers(String email) {
        return userRepository.findFollowersByEmail(email);
    }

    public List<User> getFollowing(String email) {
        return userRepository.findFollowingByEmail(email);
    }
}
