package com.manuel.anchorlife.anchorlife.models.mappers;

import com.manuel.anchorlife.anchorlife.models.entities.User;
import com.manuel.anchorlife.anchorlife.models.dto.request.UserRegisterRequest;
import com.manuel.anchorlife.anchorlife.models.dto.response.UserRegisterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //Para el registro
    //De Request a entidad
    public User toEntity(UserRegisterRequest userRegisterRequest);
    //De entidad a response
    public UserRegisterResponse toResponse(User user);

}
