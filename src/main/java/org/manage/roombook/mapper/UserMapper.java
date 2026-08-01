package org.manage.roombook.mapper;

import org.apache.ibatis.annotations.*;
import org.manage.roombook.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM userinfo WHERE telephone = #{telephone}")
    List<User> selectUser(String telephone);

    @Transactional
    @Insert("INSERT INTO userinfo (telephone, name, password) VALUES (#{telephone}, #{name}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(User user);
}
