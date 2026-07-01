package com.example.chapter03daily.domain.daily.entity;

import com.example.chapter03daily.common.entity.BaseEntity;
import com.example.chapter03daily.domain.comment.entity.Comment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "dailies"
)
public class Daily extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @OneToMany(
            mappedBy = "daily",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> comments = new ArrayList<>();

    public Daily(String title, String content, String author, String password) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.password = password;
    }

    public void update(
            String title, String content, String author
    ) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
