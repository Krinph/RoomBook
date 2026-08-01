package org.manage.roombook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.manage.roombook.entity.User;

public class UserLoginDTO {
    @NotBlank(message = "Password shouldn't be null")
    private String password;
    @NotBlank(message = "Telephone shouldn't be null")
    @Pattern(regexp = "^1[3-9]\\d{9}$")
    private String telephone;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public User toEntity() {
        User user = new User();
        user.setPassword(password);
        user.setTelephone(telephone);
        return user;
    }
}
