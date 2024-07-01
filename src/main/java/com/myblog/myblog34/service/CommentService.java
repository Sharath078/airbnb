package com.myblog.myblog34.service;

import com.myblog.myblog34.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, long postId);
    void deleteComment(long id);
    CommentDto updateComment(long id, CommentDto commentDto, long postId);
}
