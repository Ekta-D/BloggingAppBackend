package com.orangeKloudTask.demo.repository;

import com.orangeKloudTask.demo.Entities.User;
import com.orangeKloudTask.demo.model.Followers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Followers, Long> {
    List<Followers> findByUser(User user);
    List<Followers> findByFollower(User follower);
}