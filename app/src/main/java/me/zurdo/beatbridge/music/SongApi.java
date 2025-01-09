package me.zurdo.beatbridge.music;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SongApi {
    private static final String API_URL = "http://10.0.2.2:7070/api/songs";
    private static final Gson gson = new Gson();
    private static final OkHttpClient httpClient = new OkHttpClient();
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();


    public static void createSong(Song song) {
        RequestBody body = RequestBody.create(gson.toJson(song), MediaType.parse("application/json"));
        executor.execute(() -> {
            Request request = new Request.Builder()
                    .url(API_URL)
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

    public static List<Song> getSongs() {
        FutureTask<List<Song>> futureTask = new FutureTask<>(new Callable<List<Song>>() {
            @Override
            public List<Song> call() throws Exception {
                Request request = new Request.Builder()
                        .url(API_URL)
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

    public static Song getSong(String id) {
        FutureTask<Song> futureTask = new FutureTask<>(new Callable<Song>() {
            @Override
            public Song call() throws Exception {
                Request request = new Request.Builder()
                        .url(API_URL + "/" + id)
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


    public static void deleteSong(Song song) {
        executor.execute(() -> {
            Request request = new Request.Builder()
                    .url(API_URL + "/" + song.id())
                    .delete()
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

    public static Song updateSong(Song song) {
        RequestBody body = RequestBody.create(gson.toJson(song), MediaType.parse("application/json"));
        FutureTask<Song> futureTask = new FutureTask<>(new Callable<Song>() {
            @Override
            public Song call() throws Exception {
                Request request = new Request.Builder()
                        .url(API_URL + "/" + song.id())
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