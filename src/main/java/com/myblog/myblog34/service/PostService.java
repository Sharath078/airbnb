package com.myblog.myblog34.service;

import com.myblog.myblog34.payload.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostDto getPostById(Long id);

    List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
}
