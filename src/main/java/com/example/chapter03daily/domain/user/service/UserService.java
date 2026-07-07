package com.example.chapter03daily.domain.user.service;

import com.example.chapter03daily.common.config.PasswordEncoder;
import com.example.chapter03daily.common.exception.ErrorCode;
import com.example.chapter03daily.common.exception.ServiceException;
import com.example.chapter03daily.domain.user.dto.UserDto;
import com.example.chapter03daily.domain.user.entity.User;
import com.example.chapter03daily.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto.Response create(UserDto.Request request) {
        User saved = userRepository.saveAndFlush(
                new User(
                        request.getName(),
                        request.getEmail(),
                        passwordEncoder.encode(request.getPassword())
                )
        );

        return UserDto.Response.build(
                saved.getName(),
                saved.getEmail(),
                saved.getCreatedAt(),
                null
        );
    }

    @Transactional(readOnly = true)
    public UserDto.Response findOne(long id) {
        User saved = userRepository.findById(id)
                .orElseThrow(
                        () -> new ServiceException(ErrorCode.USER_NOT_FOUND)
                );

        return UserDto.Response.build(
                saved.getName(),
                saved.getEmail(),
                saved.getCreatedAt(),
                saved.getModifiedAt()
        );
    }

    @Transactional
    public UserDto.Response update(long id, UserDto.Request request, String password) {
        User saved = userRepository.findById(id)
                .orElseThrow(
                        () -> new ServiceException(ErrorCode.USER_NOT_FOUND)
                );

        if (!passwordEncoder.matches(password, saved.getPassword())) {
            throw new ServiceException(ErrorCode.INVALID_PASSWORD);
        }

        saved.update(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        return UserDto.Response.build(
                saved.getName(),
                saved.getEmail(),
                null,
                saved.getModifiedAt()
        );
    }

    @Transactional
    public void delete(long id, String password) {
        User saved = userRepository.findById(id)
                .orElseThrow(
                        () -> new ServiceException(ErrorCode.USER_NOT_FOUND)
                );

        if (!passwordEncoder.matches(password, saved.getPassword())) {
            throw new ServiceException(ErrorCode.INVALID_PASSWORD);
        }

        userRepository.deleteById(id);
    }
}
