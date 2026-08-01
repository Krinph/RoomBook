package org.manage.roombook.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Text {
    public static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public static void main(String[] args) {
        System.out.println(passwordEncoder.encode("123456"));
    }
}
