package me.zurdo.beatbridge.auth;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookieStorage implements CookieJar {
    public static List<Cookie> cookieStore = new ArrayList<>();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        // Reemplazar cookies con el mismo nombre
        for (Cookie newCookie : cookies) {
            cookieStore.removeIf(cookie -> cookie.name().equals(newCookie.name()));
            cookieStore.add(newCookie);
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return new ArrayList<>(cookieStore); // Retorna todas las cookies almacenadas
    }

    public Cookie getCookieByName(String name) {
        for (Cookie cookie : cookieStore) {
            if (cookie.name().equals(name)) {
                return cookie;
            }
        }
        return null; // Devuelve null si no se encuentra la cookie
    }
}

