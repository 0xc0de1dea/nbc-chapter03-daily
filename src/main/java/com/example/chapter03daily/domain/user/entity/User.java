package com.example.chapter03daily.domain.user.entity;

import com.example.chapter03daily.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "users"
)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 4, message = "이름은 최대 4글자까지 입력할 수 있습니다.")
    @Column(nullable = false, length = 4)
    private String name;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8글자 이상입니다,")
    @Column(nullable = false, length = 8, columnDefinition = "TEXT")
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void update(String name, String email, String password) {
        this.name = name == null ? this.name : name;
        this.email = email == null ? this.email : email;
        this.password = password == null ? this.password : password;
    }
}
