package com.video.utils;

import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kotabek on 4/20/17.
 */
public final class CookiesHelper {
    public static final String SESSION_COOKIE = "v_session";

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        if (request == null
            || response == null
            || cookieName == null
            || StrUtil.isEmpty(cookieName)) {
            return;
        }

        Cookie[] cookies = request.getCookies();

        if (cookies != null) { // Yes, this can return null! The for loop would otherwise throw NPE.
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase(cookieName)) {
                    response.addCookie(createCookie(cookie.getName(), "", null, 0));
                    break;
                }
            }
        }
    }

    public static String getCookieVal(String name, Cookie[] cookies) {
        String val = null;
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    val = cookie.getValue();
                }
            }
        }
        return val;
    }

    public static Cookie createCookie(String name, String value) {
        return createCookie(name, value, null, null);
    }

    public static Cookie createCookie(String name, String value, String path, Integer maxAge) {
        Assert.hasText(name, "Name can not be empty or NULL");
        if (path == null) {
            path = "/";
        }
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);
        if (maxAge != null) {
            cookie.setMaxAge(maxAge);
        }
        return cookie;
    }

}
