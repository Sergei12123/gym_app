package com.example.gym_app.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseEntity {

    private String firstName;

    private String lastName;

    private String userName;

    private String password;

    private Boolean isActive;
}
