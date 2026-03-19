package com.manuel.anchorlife.anchorlife.services.impl;

import com.manuel.anchorlife.anchorlife.models.entities.User;
import com.manuel.anchorlife.anchorlife.models.dto.request.UserRegisterRequest;
import com.manuel.anchorlife.anchorlife.models.dto.response.UserRegisterResponse;
import com.manuel.anchorlife.anchorlife.models.mappers.UserMapper;
import com.manuel.anchorlife.anchorlife.repositories.UserRepository;
import com.manuel.anchorlife.anchorlife.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
        User userEntity = userMapper.toEntity(userRegisterRequest);
        User userDb = userRepository.save(userEntity);
        return userMapper.toResponse(userDb);
    }
}
