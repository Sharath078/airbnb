package com.myblog.myblog34.controller;

import com.myblog.myblog34.payload.PostDto;
import com.myblog.myblog34.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // Endpoint to get a post by ID
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // Endpoint to get all posts with pagination and sorting
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "3") int pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortDir
    ) {
        List<PostDto> postDtos = postService.getAllPosts(pageNo, pageSize, sortBy,sortDir);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }
}

//http://localhost:8080/api/posts?pageNo=0&pageSize=8
//http://localhost:8080/api/posts?pageNo=0&pageSize=8&sortBy=title&sortDir=desc
