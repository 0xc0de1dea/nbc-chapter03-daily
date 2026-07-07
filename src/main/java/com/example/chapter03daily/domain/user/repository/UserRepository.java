package com.example.chapter03daily.domain.user.repository;

import com.example.chapter03daily.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
