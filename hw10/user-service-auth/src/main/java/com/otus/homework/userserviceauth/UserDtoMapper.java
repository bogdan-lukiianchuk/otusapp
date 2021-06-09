package com.otus.homework.userserviceauth;

import com.otus.homework.userserviceauth.dto.response.UserDto;
import com.otus.homework.userserviceauth.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public UserDto map(User user) {
        return new UserDto(user.getId(), user.getLogin());
    }
}
