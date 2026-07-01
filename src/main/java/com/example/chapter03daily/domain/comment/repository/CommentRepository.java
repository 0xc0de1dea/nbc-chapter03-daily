package com.example.chapter03daily.domain.comment.repository;

import com.example.chapter03daily.domain.comment.entity.Comment;
import com.example.chapter03daily.domain.daily.entity.Daily;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    long countByDailyId(Long dailyId);
}
