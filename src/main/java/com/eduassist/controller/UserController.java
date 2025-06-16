package com.eduassist.controller;

import com.eduassist.entity.User;
import com.eduassist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param user 用户信息（JSON）
     * @return 注册成功的提示
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        userService.register(user);
        return ResponseEntity.ok("注册成功");
    }

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return JWT 令牌
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        String token = userService.login(username, password);
        return ResponseEntity.ok(token);
    }

    /**
     * 获取用户信息
     * @param username 用户名
     * @return 用户对象
     */
    @GetMapping("/info")
    public ResponseEntity<User> getUserInfo(@RequestParam String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }
}
