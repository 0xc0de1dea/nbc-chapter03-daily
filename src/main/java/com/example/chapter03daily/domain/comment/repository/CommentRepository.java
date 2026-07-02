package com.example.chapter03daily.domain.comment.repository;

import com.example.chapter03daily.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    long countByDailyId(Long dailyId);
}
