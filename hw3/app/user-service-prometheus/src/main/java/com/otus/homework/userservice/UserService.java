package com.otus.homework.userservice;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        UserDto result = new UserDto();
        BeanUtils.copyProperties(user, result);
        return result;
    }

    @Transactional
    public UserDto create(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user, "id");
        User saved = userRepository.save(user);
        UserDto result = new UserDto();
        BeanUtils.copyProperties(saved, result);
        return result;
    }

    @Transactional
    public UserDto update(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow();
        BeanUtils.copyProperties(userDto, user, "id");
        User saved = userRepository.save(user);
        UserDto result = new UserDto();
        BeanUtils.copyProperties(saved, result);
        return result;
    }

    @Transactional
    public void delete(Long id){
        userRepository.deleteById(id);
    }
}
