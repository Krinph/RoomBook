package org.manage.roombook.controller;

import jakarta.validation.Valid;
import org.manage.roombook.dto.UserRegisterDTO;
import org.manage.roombook.entity.Result;
import org.manage.roombook.entity.User;
import org.manage.roombook.exception.BusinessException;
import org.manage.roombook.service.UserService;
import org.manage.roombook.util.ErrorType;
import org.manage.roombook.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class RegisController {

    @Autowired
    UserService userService;

//    @GetMapping("/register")
//    public String register() {
//        return "registerPage";
//    }

    @PostMapping("/regis")
    @ResponseBody
    public Result<UserVO> regis(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        User user = userRegisterDTO.toEntity();
        if (!userService.selectUser(user).isEmpty()) {
            throw new BusinessException(ErrorType.USER_EXISTS);
        } else if (userService.insertUser(user) == 1) {
            UserVO userVO = new UserVO(user);
            return Result.success(userVO);
        } else {
            throw new BusinessException(ErrorType.SYSTEM_ERROR);
        }
    }
}
