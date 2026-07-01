package com.example.chapter03daily.domain.comment.entity;

import com.example.chapter03daily.common.entity.BaseEntity;
import com.example.chapter03daily.domain.daily.entity.Daily;
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

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_id", nullable = false)
    private Daily daily;

    public Comment(String content, String author, String password, Daily daily) {
        this.content = content;
        this.author = author;
        this.password = password;
        this.daily = daily;
    }
}
