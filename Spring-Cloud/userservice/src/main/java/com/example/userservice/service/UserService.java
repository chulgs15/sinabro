package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import org.springframework.stereotype.Service;


public interface UserService {
    UserEntity createUser(UserDto userDto);
}
