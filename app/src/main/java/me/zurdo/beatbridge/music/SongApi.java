package me.zurdo.beatbridge.music;

import static me.zurdo.beatbridge.Config.API_URL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.zurdo.beatbridge.auth.AuthUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SongApi {
    private static final String SONGS_URL = API_URL + "/songs";
    private static final Gson gson = new Gson();
    private static final OkHttpClient httpClient = new OkHttpClient();
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    // Crear una canción
    public static void createSong(Song song) {
        String authToken = AuthUtils.getAuthCookie();
        if (authToken == null) {
            System.out.println("No se encontró la cookie de autenticación.");
            return;
        }
        RequestBody body = RequestBody.create(gson.toJson(song), MediaType.parse("application/json"));
        executor.execute(() -> {
            Request request = new Request.Builder()
                    .url(SONGS_URL)
                    .addHeader("Authorization", "Bearer " + authToken)
                    .put(body)
                    .build();
            try (Response response = httpClient.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    System.out.println("Song created successfully.");
                } else {
                    System.err.println("Failed to create song: " + response.code() + " - " + response.message());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // Obtener todas las canciones
    public static List<Song> getSongs() {
        FutureTask<List<Song>> futureTask = new FutureTask<>(new Callable<List<Song>>() {
            @Override
            public List<Song> call() throws Exception {
                Request request = new Request.Builder()
                        .url(SONGS_URL)
                        .get()
                        .build();

                try (Response response = httpClient.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        return gson.fromJson(responseBody, new TypeToken<List<Song>>(){}.getType());
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

    // Obtener una canción
    public static Song getSong(String id) {
        FutureTask<Song> futureTask = new FutureTask<>(new Callable<Song>() {
            @Override
            public Song call() throws Exception {
                Request request = new Request.Builder()
                        .url(SONGS_URL + "/" + id)
                        .get()
                        .build();

                try (Response response = httpClient.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        return gson.fromJson(responseBody, new TypeToken<Song>(){}.getType());
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

    // Obtener las canciones de un usuario
    public static List<Song> getSongsFromUser(long id) {
        String authToken = AuthUtils.getAuthCookie();
        if (authToken == null) {
            System.out.println("No se encontró la cookie de autenticación.");
            return null;
        }
        FutureTask<List<Song>> futureTask = new FutureTask<>(new Callable<List<Song>>() {
            @Override
            public List<Song> call() throws Exception {
                Request request = new Request.Builder()
                        .url(API_URL + "/user/songs")
                        .addHeader("Authorization", "Bearer " + authToken)
                        .get()
                        .build();

                try (Response response = httpClient.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        return gson.fromJson(responseBody, new TypeToken<List<Song>>(){}.getType());
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


    // Eliminar una canción
    public static void deleteSongs(List<Song> songs) {
        System.out.println("EliminandoCanciones");
        String authToken = AuthUtils.getAuthCookie();
        if (authToken == null) {
            System.out.println("No se encontró la cookie de autenticación.");
            return;
        }
        List<Long> songIds = new ArrayList<>();
        for (Song song : songs) {
            songIds.add(song.id());
        }

        RequestBody body = RequestBody.create(gson.toJson(songIds), MediaType.parse("application/json"));

        executor.execute(() -> {
            Request request = new Request.Builder()
                    .url(SONGS_URL)
                    .addHeader("Authorization", "Bearer " + authToken)
                    .delete(body)
                    .build();
            try (Response response = httpClient.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    // Manejar éxito: eliminar las canciones de la interfaz, mostrar mensaje, etc.
                    System.out.println("Canciones eliminadas correctamente.");
                } else {
                    // Manejar error
                    System.out.println("Error al eliminar canciones: " + response.code());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    // Actualizar una canción
    public static Song updateSong(Song song) {
        String authToken = AuthUtils.getAuthCookie();
        if (authToken == null) {
            System.out.println("No se encontró la cookie de autenticación.");
            return null;
        }
        RequestBody body = RequestBody.create(gson.toJson(song), MediaType.parse("application/json"));
        FutureTask<Song> futureTask = new FutureTask<>(new Callable<Song>() {
            @Override
            public Song call() throws Exception {
                Request request = new Request.Builder()
                        .url(SONGS_URL + "/" + song.id())
                        .addHeader("Authorization", "Bearer " + authToken)
                        .patch(body)
                        .build();

                try (Response response = httpClient.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        return gson.fromJson(responseBody, new TypeToken<Song>(){}.getType());
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