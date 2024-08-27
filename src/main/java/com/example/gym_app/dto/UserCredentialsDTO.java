package com.example.gym_app.dto;

import com.example.gym_app.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCredentialsDTO {
    private String username;
    private String password;

    public UserCredentialsDTO(User user) {
        this.username = user.getUserName();
        this.password = user.getPassword();
    }
}
