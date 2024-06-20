package com.myblog.myblog34.repository;

import com.myblog.myblog34.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>{
}
