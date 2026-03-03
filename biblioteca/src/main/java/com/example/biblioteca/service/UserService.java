package com.example.biblioteca.service;

import com.example.biblioteca.dto.UserCreateDto;
import com.example.biblioteca.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(UserCreateDto dto);
    List<UserDto> listAll();
    UserDto getById(Long id);
    void delete(Long id);
}
