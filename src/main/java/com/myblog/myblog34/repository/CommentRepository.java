package com.myblog.myblog34.repository;

import com.myblog.myblog34.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
