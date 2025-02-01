package me.zurdo.beatbridge.stats;

import static me.zurdo.beatbridge.Config.API_URL;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import me.zurdo.beatbridge.auth.AuthUtils;
import me.zurdo.beatbridge.auth.User;
import me.zurdo.beatbridge.music.Album;
import me.zurdo.beatbridge.music.Song;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PlayApi {
    private static final String PLAYS_URL = API_URL + "/stats/play";
    private static final String GLOBAL_URL = API_URL + "/stats/global";
    private static final String USER_URL = API_URL + "/stats/user";
    private static final Gson gson = new Gson();
    private static final OkHttpClient httpClient = new OkHttpClient();
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    //Incrementar una play
    public static void increment(long song) {
        String authToken = AuthUtils.getAuthCookie();
        if (authToken == null) {
            System.out.println("No se encontró la cookie de autenticación.");
            return;
        }

        executor.execute(() -> {
            Request request = new Request.Builder()
                    .url(PLAYS_URL + "/" + song)
                    .addHeader("Authorization", "Bearer " + authToken)
                    .put(RequestBody.create(gson.toJson(song), null))
                    .build();
            try (Response response = httpClient.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    System.out.println("Play incremented successfully.");
                } else {
                    System.err.println("Failed to increment play: " + response.code() + " - " + response.message());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // Obtener la canción más escuchada por usuario
    public static Song getMostPlayedSongFromUser() {
        String authToken = AuthUtils.getAuthCookie();
        if (authToken == null) {
            System.out.println("No se encontró la cookie de autenticación.");
            return null;
        }

        FutureTask<Song> futureTask = new FutureTask<>(() -> {
            Request request = new Request.Builder()
                    .url(USER_URL)
                    .get()
                    .addHeader("Authorization", "Bearer " + authToken)  // Asegura que se envíe el token
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    return gson.fromJson(responseBody, new TypeToken<Song>() {
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
            return new Song(-1, "", "", -1, null, "");
        }
    }

    // Obtener el álbum más escuchado por usuario
    public static Album getMostPlayedAlbumFromUser() {
        String authToken = AuthUtils.getAuthCookie();
        if (authToken == null) {
            System.out.println("No se encontró la cookie de autenticación.");
            return null;
        }

        FutureTask<Album> futureTask = new FutureTask<>(() -> {
            Request request = new Request.Builder()
                    .url(USER_URL + "/album")
                    .get()
                    .addHeader("Authorization", "Bearer " + authToken)
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
            return new Album(-1, "", -1, -1);
        }
    }

    // Obtener el artista más escuchado por usuario
    public static User getMostPlayedArtistFromUser() {
        String authToken = AuthUtils.getAuthCookie();
        if (authToken == null) {
            System.out.println("No se encontró la cookie de autenticación.");
            return null;
        }

        FutureTask<User> futureTask = new FutureTask<>(() -> {
            Request request = new Request.Builder()
                    .url(USER_URL + "/artist")
                    .get()
                    .addHeader("Authorization", "Bearer " + authToken)
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    return gson.fromJson(responseBody, new TypeToken<User>() {}.getType());
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
            return new User(-1, "", "", User.Role.ARTIST, -1 );
        }
    }

    // Obtener la canción más escuchada global
    public static Song getMostPlayedSong() {
        String authToken = AuthUtils.getAuthCookie();
        if (authToken == null) {
            System.out.println("No se encontró la cookie de autenticación.");
            return null;
        }

        FutureTask<Song> futureTask = new FutureTask<>(() -> {
            Request request = new Request.Builder()
                    .url(GLOBAL_URL)
                    .get()
                    .addHeader("Authorization", "Bearer " + authToken)  // Asegura que se envíe el token
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    return gson.fromJson(responseBody, new TypeToken<Song>() {
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
            return new Song(-1, "", "", -1, null, "");
        }
    }

    // Obtener el album más escuchado global
    public static Album getMostPlayedAlbum() {
        String authToken = AuthUtils.getAuthCookie();
        if (authToken == null) {
            System.out.println("No se encontró la cookie de autenticación.");
            return null;
        }

        FutureTask<Album> futureTask = new FutureTask<>(() -> {
            Request request = new Request.Builder()
                    .url(GLOBAL_URL + "/album")
                    .get()
                    .addHeader("Authorization", "Bearer " + authToken)
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
            return new Album(-1, "", -1, -1);
        }
    }

    // Obtener el artista más escuchado global
    public static User getMostPlayedArtist() {
        String authToken = AuthUtils.getAuthCookie();
        if (authToken == null) {
            System.out.println("No se encontró la cookie de autenticación.");
            return null;
        }

        FutureTask<User> futureTask = new FutureTask<>(() -> {
            Request request = new Request.Builder()
                    .url(GLOBAL_URL + "/artist")
                    .get()
                    .addHeader("Authorization", "Bearer " + authToken)
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    return gson.fromJson(responseBody, new TypeToken<User>() {}.getType());
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
            return new User(-1, "", "", User.Role.ARTIST, -1 );
        }
    }


}
