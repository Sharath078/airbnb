package com.myblog.myblog34.service.impl;

import com.myblog.myblog34.entity.Comment;
import com.myblog.myblog34.entity.Post;
import com.myblog.myblog34.exception.ResourseNotFoundException;
import com.myblog.myblog34.payload.CommentDto;
import com.myblog.myblog34.repository.CommentRepository;
import com.myblog.myblog34.repository.PostRepository;
import com.myblog.myblog34.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourseNotFoundException("Post not found with id " + postId)
        );

        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourseNotFoundException("Comment not found with id " + id)
        );
        commentRepository.delete(comment);
    }

    @Override
    public CommentDto updateComment(long id, CommentDto commentDto, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourseNotFoundException("Post not found with id " + postId)
        );

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourseNotFoundException("Comment not found with id " + id)
        );

        comment.setEmail(commentDto.getEmail());
        comment.setText(commentDto.getText());
        comment.setPost(post);

        Comment updatedComment = commentRepository.save(comment);
        return modelMapper.map(updatedComment, CommentDto.class);
    }
}
