package me.zurdo.beatbridge.auth;

import com.google.gson.Gson;

import java.io.IOException;

import me.zurdo.beatbridge.MainActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ValidateApi {
    private static final String VALIDATE_API_URL = "http://localhost:7070/api/validate";
    private static final OkHttpClient httpClient = new OkHttpClient.Builder()
            .cookieJar(MainActivity.cookies)
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
                ValidateResponse validateResponse = gson.fromJson(responseBody, ValidateResponse.class);
                System.out.println("Token válido. Usuario autenticado: " + validateResponse.user);
                return validateResponse.user;
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
