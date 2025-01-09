package me.zurdo.beatbridge.auth;

import com.google.gson.Gson;

import java.io.IOException;

import me.zurdo.beatbridge.LoginActivity;
import me.zurdo.beatbridge.Main;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ValidateApi {
    private static final String VALIDATE_API_URL = "http://10.0.2.2:7070/api/validate";
    private static final OkHttpClient httpClient = new OkHttpClient.Builder()
            .cookieJar(Main.cookies)
            .build();
    private static final Gson gson = new Gson();

    private static class ValidateResponse {
        User user;
    }

    public static User validateToken() {
        String authToken = AuthUtils.getAuthCookie();
        if (authToken == null) {
            System.out.println("No se encontró la cookie de autenticación.");
            return null;
        }

        Request request = new Request.Builder()
                .url(VALIDATE_API_URL)
                .addHeader("Authorization", "Bearer " + authToken)
                .get()
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                User user = gson.fromJson(responseBody, User.class);
                System.out.println("Token válido. Usuario autenticado: " + user);
                return user;
            } else {
                System.out.println("El token no es válido. Código de respuesta: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al validar el token: " + e.getMessage());
        }
        return null;
    }
}
