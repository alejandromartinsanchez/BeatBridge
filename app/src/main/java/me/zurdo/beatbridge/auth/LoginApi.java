package me.zurdo.beatbridge.auth;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import me.zurdo.beatbridge.Main;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginApi {
    private static final String LOGIN_API_URL = "http://10.0.2.2:7070/api/login";
    private static final Gson gson = new Gson();
    private static final OkHttpClient httpClient = new OkHttpClient.Builder()
            .cookieJar(Main.cookies) // Usa el almacenamiento global de cookies
            .build();

    private static class LoginPayload {
        String username;
        String password;

        LoginPayload(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    private static class LoginResponse {
        String token;
    }

    public static void login(String username, String password, LoginCallback callback) {
        System.out.println("Iniciando proceso de login...");
        System.out.println("Usuario: " + username);

        LoginPayload payload = new LoginPayload(username, password);
        String jsonPayload = gson.toJson(payload);

        RequestBody body = RequestBody.create(jsonPayload, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(LOGIN_API_URL)
                .post(body)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                callback.onFailure("Error al conectar con el servidor: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    LoginResponse loginResponse = gson.fromJson(responseBody, LoginResponse.class);

                    // Guardar el token como cookie
                    Cookie authCookie = AuthUtils.createAuthCookie(loginResponse.token);
                    Main.cookies.saveFromResponse(null, List.of(authCookie));

                    callback.onSuccess();
                } else {
                    callback.onFailure("Error de login. Código de respuesta: " + response.code());
                }
            }
        });
    }

    public interface LoginCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

}
