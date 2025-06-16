package com.eduassist.service;

import com.eduassist.entity.User;
import com.eduassist.mapper.UserMapper;
import com.eduassist.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 注册新用户
     * @param user 用户对象
     * @throws RuntimeException 如果用户名已存在
     */
    public void register(User user) {
        User existingUser = userMapper.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("用户名已存在");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
    }

    /**
     * 用户登录并返回JWT令牌
     * @param username 用户名
     * @param password 密码
     * @return JWT令牌
     * @throws RuntimeException 如果用户名或密码错误
     */
    public String login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return jwtTokenUtil.generateToken(user.getUsername());
        } else {
            throw new RuntimeException("用户名或密码错误");
        }
    }

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户对象
     * @throws RuntimeException 如果用户不存在
     */
    public User getUserByUsername(String username) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return user;
    }
}
