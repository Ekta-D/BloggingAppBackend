package com.orangeKloudTask.demo.services;

import com.orangeKloudTask.demo.Entities.User;
import com.orangeKloudTask.demo.model.Posts;
import com.orangeKloudTask.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Posts createPost(Posts post) {
        return postRepository.save(post);
    }

    public List<Posts> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Posts> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public List<Posts> getPostsByAuthor(User author) {
        return postRepository.findByAuthorId(author.getId());
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public Posts updatePost(Long id, Posts newPost) {
        return postRepository.findById(id).map(post -> {
            post.setTitle(newPost.getTitle());
            post.setContent(newPost.getContent());
            return postRepository.save(post);
        }).orElseThrow(() -> new RuntimeException("Post not found"));
    }
}
