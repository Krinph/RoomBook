package org.manage.roombook.service;

import org.manage.roombook.entity.User;
import org.manage.roombook.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<User> selectUser(User user) {
        String telephone = user.getTelephone();
        return userMapper.selectUser(telephone);
    }

    public int insertUser(User user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        return userMapper.insertUser(user);
    }
}
