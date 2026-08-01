package org.manage.roombook.util;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

public class SecurityUtil {
    public static Claims getChaim(HttpServletRequest request) {
        return (Claims) request.getAttribute("claims");
    }
}
