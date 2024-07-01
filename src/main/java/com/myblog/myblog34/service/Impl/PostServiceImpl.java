package com.myblog.myblog34.service.Impl;

import com.myblog.myblog34.exception.ResourseNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.myblog.myblog34.entity.Post;
import com.myblog.myblog34.payload.PostDto;
import com.myblog.myblog34.repository.PostRepository;
import com.myblog.myblog34.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private ModelMapper modelMapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);
        return mapToDto(savedPost);
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourseNotFoundException("Post not found with id: " + id)
        );
        return mapToDto(post);
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        if (pageSize < 1) {
            throw new IllegalArgumentException("Page size must not be less than one");
        }

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        List<Post> posts = postRepository.findAll(pageable).getContent();
        return posts.stream().map(this::mapToDto).collect(Collectors.toList());
    }


    private PostDto mapToDto(Post post) {
        PostDto dto = modelMapper.map(post, PostDto.class);
        return dto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);

        return post;
    }
}
