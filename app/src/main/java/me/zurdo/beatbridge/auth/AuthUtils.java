package me.zurdo.beatbridge.auth;

import me.zurdo.beatbridge.LoginActivity;
import okhttp3.Cookie;

public class AuthUtils {

    // Crear una cookie de autenticación
    public static Cookie createAuthCookie(String token) {
        return new Cookie.Builder()
                .name("auth")
                .value(token)
                .domain("localhost")
                .path("/")
                .httpOnly()
                .build();
    }

    // Obtener la cookie de autenticación
    public static String getAuthCookie() {
        Cookie authCookie = LoginActivity.cookies.getCookieByName("auth");
        return authCookie != null ? authCookie.value() : null;
    }
}



