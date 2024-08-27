package com.example.gym_app.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserCredentialsDTO {
    private String username;
    private String password;
}
