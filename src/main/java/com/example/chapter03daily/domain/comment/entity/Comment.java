package com.example.chapter03daily.domain.comment.entity;

import com.example.chapter03daily.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "comments"
)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long dailyId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    public Comment(Long dailyId, String content, String author, String password) {
        this.dailyId = dailyId;
        this.content = content;
        this.author = author;
        this.password = password;
    }
}
