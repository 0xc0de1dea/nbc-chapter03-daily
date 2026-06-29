package com.example.chapter03daily.domain.comment.repository;

import com.example.chapter03daily.domain.comment.entity.Daily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyRepository extends JpaRepository<Daily, Long> {


}
