package me.zurdo.beatbridge.music;

import static me.zurdo.beatbridge.Config.API_URL;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import me.zurdo.beatbridge.LoginActivity;
import me.zurdo.beatbridge.auth.AuthUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AlbumApi {
    private static final String ALBUMS_URL = API_URL + "/albums";
    private static final Gson gson = new Gson();
    private static final OkHttpClient httpClient = new OkHttpClient();
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    // Crear o obtener un álbum
    public static Album getOrCreateAlbum(String name, int year) {
        String authToken = AuthUtils.getAuthCookie();
        if (authToken == null) {
            System.out.println("No se encontró la cookie de autenticación.");
            return null;
        }

        Album album = new Album(-1, name, LoginActivity.registeredUser.id(), year);

        RequestBody body = RequestBody.create(gson.toJson(album), MediaType.parse("application/json"));
        FutureTask<Album> futureTask = new FutureTask<>(() -> {
            Request request = new Request.Builder()
                    .url(ALBUMS_URL)
                    .put(body)
                    .addHeader("Authorization", "Bearer " + authToken)  // Asegura que se envíe el token
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    return gson.fromJson(responseBody, new TypeToken<Album>() {
                    }.getType());
                } else {
                    throw new IOException("Código inesperado: " + response);
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

    // Obtener todos los álbumes
    public static List<Album> getAlbums() {
        FutureTask<List<Album>> futureTask = new FutureTask<>(new Callable<List<Album>>() {
            @Override
            public List<Album> call() throws Exception {
                Request request = new Request.Builder()
                        .url(ALBUMS_URL)
                        .get()
                        .build();

                try (Response response = httpClient.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        return gson.fromJson(responseBody, new TypeToken<List<Album>>(){}.getType());
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

    // Obtener las canciones de un álbum
    public static List<Song> getSongs(long albumId) {
        FutureTask<List<Song>> futureTask = new FutureTask<>(new Callable<List<Song>>() {
            @Override
            public List<Song> call() throws Exception {
                // Construir la URL con el ID del álbum
                String url = ALBUMS_URL + "/" + albumId + "/songs";

                // Crear la solicitud GET
                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();

                // Ejecutar la solicitud y procesar la respuesta
                try (Response response = httpClient.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        return gson.fromJson(responseBody, new TypeToken<List<Song>>() {}.getType());
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                }
            }
        });

        // Ejecutar la tarea en el executor
        executor.execute(futureTask);

        // Obtener el resultado de la tarea
        try {
            return futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
}
