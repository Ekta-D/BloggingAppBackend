package com.orangeKloudTask.demo.repository;


import com.orangeKloudTask.demo.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    @Query("SELECT u.followers FROM User u WHERE u.email = :email")
    List<User> findFollowersByEmail(String email);

    @Query("SELECT u.following FROM User u WHERE u.email = :email")
    List<User> findFollowingByEmail(String email);
}
