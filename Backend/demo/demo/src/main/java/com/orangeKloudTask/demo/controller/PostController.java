package com.orangeKloudTask.demo.controller;
import com.orangeKloudTask.demo.Entities.User;
import com.orangeKloudTask.demo.model.Posts;
import com.orangeKloudTask.demo.services.PostService;
import com.orangeKloudTask.demo.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Posts createPost(@RequestBody Posts post, Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userService.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));

        post.setAuthor(user);
        return postService.createPost(post);
    }
    @GetMapping
    public List<Posts> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Posts getPostById(@PathVariable Long id) {
        return postService.getPostById(id).orElseThrow(() -> new RuntimeException("Post not found"));
    }


    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    @PutMapping("/{id}")
    public Posts updatePost(@PathVariable Long id, @RequestBody Posts post) {
        return postService.updatePost(id, post);
    }
}