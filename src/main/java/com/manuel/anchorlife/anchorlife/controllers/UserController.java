package com.manuel.anchorlife.anchorlife.controllers;

import com.manuel.anchorlife.anchorlife.models.dto.request.UserRegisterRequest;
import com.manuel.anchorlife.anchorlife.models.dto.response.UserRegisterResponse;
import com.manuel.anchorlife.anchorlife.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private IUserService userService;


    @PostMapping
    public ResponseEntity<UserRegisterResponse> register(@RequestBody UserRegisterRequest userRegisterRequest) {

        UserRegisterResponse response = userService.register(userRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
