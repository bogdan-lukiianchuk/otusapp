package com.otus.homework.userservice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
}
