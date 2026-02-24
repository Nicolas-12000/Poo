package com.example.biblioteca.mapper;

import com.example.biblioteca.dto.UserCreateDto;
import com.example.biblioteca.dto.UserDto;
import com.example.biblioteca.model.User;

public class UserMapper {
    public static UserDto toDto(User u) {
        if (u == null) return null;
        UserDto d = new UserDto();
        d.setId(u.getId());
        d.setName(u.getName());
        d.setEmail(u.getEmail());
        return d;
    }

    public static User toEntity(UserCreateDto d) {
        if (d == null) return null;
        return User.builder()
                .name(d.getName())
                .email(d.getEmail())
                .build();
    }
}
