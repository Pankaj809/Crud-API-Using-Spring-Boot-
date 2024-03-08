package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return userMapper.toDto(user);
        }
        return null;
    }

    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    public UserDto updateUser(Long id, UserDto updatedUserDto) {
        if (userRepository.existsById(id)) {
            User updatedUser = userMapper.toEntity(updatedUserDto);
            updatedUser.setId(id);
            updatedUser = userRepository.save(updatedUser);
            return userMapper.toDto(updatedUser);
        }
        return null; // Return null if user with given ID doesn't exist
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
