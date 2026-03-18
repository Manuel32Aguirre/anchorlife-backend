package com.manuel.anchorlife.anchorlife.services.impl;

import com.manuel.anchorlife.anchorlife.models.dto.request.UserRegisterRequest;
import com.manuel.anchorlife.anchorlife.models.dto.response.UserRegisterResponse;
import com.manuel.anchorlife.anchorlife.repositories.UserRepository;
import com.manuel.anchorlife.anchorlife.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {

    }
}
