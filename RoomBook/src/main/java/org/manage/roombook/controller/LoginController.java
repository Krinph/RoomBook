package org.manage.roombook.controller;

import jakarta.validation.Valid;
import org.manage.roombook.dto.UserLoginDTO;
import org.manage.roombook.entity.Result;
import org.manage.roombook.entity.User;
import org.manage.roombook.exception.BusinessException;
import org.manage.roombook.service.UserService;
import org.manage.roombook.util.ErrorType;
import org.manage.roombook.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

//    @GetMapping ("/")
//    public String index() {
//        return "redirect:/login";
//    }
//
//    @GetMapping("/login")
//    public String login() {
//        return "loginPage";
//    }

    @PostMapping("/match")
    @ResponseBody
    public Result<HashMap<String, Object>> match(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        User user = userLoginDTO.toEntity();
        List<User> users = userService.selectUser(user);
        if (users.isEmpty() || !passwordEncoder.matches(user.getPassword(), users.get(0).getPassword())) {
            throw new BusinessException(ErrorType.LOGIN_FAIL);
        } else {
            User dbUser = users.get(0);
            String token = jwtUtil.generateToken(dbUser.getId(), dbUser.getName(), dbUser.IsAdmin());
            HashMap<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("userId", dbUser.getId());
            map.put("isAdmin", dbUser.IsAdmin());
            return Result.success(map);
        }
    }
   }