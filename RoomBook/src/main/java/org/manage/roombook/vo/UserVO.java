package org.manage.roombook.vo;

import org.manage.roombook.entity.User;

public class UserVO {
    private Integer id;
    private String name;
    private String telephone;
    private boolean isAdmin;

    public UserVO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.telephone = user.getTelephone();
        this.isAdmin = user.IsAdmin();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
