package com.example.biblioteca.service.impl;

import com.example.biblioteca.dto.UserCreateDto;
import com.example.biblioteca.dto.UserDto;
import com.example.biblioteca.mapper.UserMapper;
import com.example.biblioteca.model.User;
import com.example.biblioteca.repository.UserRepository;
import com.example.biblioteca.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDto create(UserCreateDto dto) {
        User u = UserMapper.toEntity(dto);
        return UserMapper.toDto(repo.save(u));
    }

    @Override
    public List<UserDto> listAll() {
        return repo.findAll().stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getById(Long id) {
        return repo.findById(id).map(UserMapper::toDto).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
