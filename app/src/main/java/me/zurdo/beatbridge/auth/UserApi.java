package me.zurdo.beatbridge.auth;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserApi {
    private static final String API_URL = "http://10.0.2.2:7070/api/auth/";
    private static final Gson gson = new Gson();
    private static final OkHttpClient httpClient = new OkHttpClient.Builder().build();
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    // Sacar el nombre del usuario
    public static String getUsername(long id) {
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Request request = new Request.Builder()
                        .url(API_URL + id )
                        .get()
                        .build();

                try (Response response = httpClient.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        return gson.fromJson(responseBody, new TypeToken<String>(){}.getType());
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                }
            }
        });
        executor.execute(futureTask);
        try {
            return futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }

    }
}
