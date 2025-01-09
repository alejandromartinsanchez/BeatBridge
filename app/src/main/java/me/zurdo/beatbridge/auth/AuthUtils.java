package me.zurdo.beatbridge.auth;

import me.zurdo.beatbridge.LoginActivity;
import me.zurdo.beatbridge.Main;
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
        Cookie authCookie = Main.cookies.getCookieByName("auth");
        return authCookie != null ? authCookie.value() : null;
    }
}



