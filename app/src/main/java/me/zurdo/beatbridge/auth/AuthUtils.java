package me.zurdo.beatbridge.auth;

import me.zurdo.beatbridge.MainActivity;
import okhttp3.Cookie;

public class AuthUtils {

    public static Cookie createAuthCookie(String token) {
        return new Cookie.Builder()
                .name("auth")
                .value(token)
                .domain("localhost")
                .path("/")
                .httpOnly()
                .build();
    }

    public static String getAuthCookie() {
        Cookie authCookie = MainActivity.cookies.getCookieByName("auth");
        return authCookie != null ? authCookie.value() : null;
    }
}



