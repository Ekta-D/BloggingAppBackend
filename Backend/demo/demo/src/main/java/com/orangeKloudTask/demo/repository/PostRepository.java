package com.orangeKloudTask.demo.repository;

import com.orangeKloudTask.demo.Entities.User;
import com.orangeKloudTask.demo.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Posts, Long> {
    List<Posts> findByAuthor(User author);

    List<Posts> findByAuthorId(Long id);
}