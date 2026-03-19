package com.manuel.anchorlife.anchorlife.services;

import com.manuel.anchorlife.anchorlife.models.dto.request.UserRegisterRequest;
import com.manuel.anchorlife.anchorlife.models.dto.response.UserRegisterResponse;

public interface IUserService {
    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest);

}
