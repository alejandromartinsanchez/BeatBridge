package me.zurdo.beatbridge.auth;

import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterApi {
    private static final String REGISTER_API_URL = "http://10.0.2.2:7070/api/auth/register";
    private static final Gson gson = new Gson();
    private static final OkHttpClient httpClient = new OkHttpClient();

    private static class RegisterPayload {
        String username;
        String password;
        String email;
        String role;

        RegisterPayload(String username, String password, String email, String role) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.role = role;
        }
    }

    private static class RegisterResponse {
        String response;
    }

    // Registrarse
    public static void register(String username, String password, String email, String role) {
        RegisterPayload payload = new RegisterPayload(username, password, email, role);
        String jsonPayload = gson.toJson(payload);

        RequestBody body = RequestBody.create(jsonPayload, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(REGISTER_API_URL)
                .post(body)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                System.out.println("Error al conectar con el servidor: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    RegisterResponse registerResponse = gson.fromJson(responseBody, RegisterResponse.class);
                    System.out.println("Respuesta del servidor: " + registerResponse.response);
                } else {
                    System.out.println("Error de registro. Código de respuesta: " + response.code());
                }
            }
        });
    }
}
