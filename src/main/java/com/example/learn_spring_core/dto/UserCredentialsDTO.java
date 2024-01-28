package com.example.learn_spring_core.dto;

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
