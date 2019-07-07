package com.demo.auth.user.application.dto;

import lombok.Data;

@Data
public class UserDTO {

    private String password;

    private String name;

    private String email;

    private String imageUrl;

    private Boolean emailVerified;

    private String provider;

    private String providerId;
}
