package me.zurdo.beatbridge;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import me.zurdo.beatbridge.stats.PlayApi;

public class StatsActivity extends AppCompatActivity {

    private TextView textViewTopSongValue, textViewTopAlbumValue, textViewTopArtistValue;
    private TextView textViewGlobalTopSongValue, textViewGlobalTopAlbumValue, textViewGlobalTopArtistValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ImageView backButton = findViewById(R.id.back);


        textViewTopSongValue = findViewById(R.id.textViewTopSongValue);
        textViewTopAlbumValue = findViewById(R.id.textViewTopAlbumValue);
        textViewTopArtistValue = findViewById(R.id.textViewTopArtistValue);

        textViewGlobalTopSongValue = findViewById(R.id.textViewGlobalTopSongValue);
        textViewGlobalTopAlbumValue = findViewById(R.id.textViewGlobalTopAlbumValue);
        textViewGlobalTopArtistValue = findViewById(R.id.textViewGlobalTopArtistValue);

        // Cargar los datos
        loadStats();

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void loadStats() {
        String cancionMasEscuchadaGlobal = PlayApi.getMostPlayedSong().name();
        String albumMasEscuchadoGlobal = PlayApi.getMostPlayedAlbum().name();
        String artistaMasEscuchadoGlobal = PlayApi.getMostPlayedArtist().username();
        String cancionMasEscuchadaUsuario = PlayApi.getMostPlayedSongFromUser().name();
        String albumMasEscuchadoUsuario = PlayApi.getMostPlayedAlbumFromUser().name();
        String artistaMasEscuchadoUsuario = PlayApi.getMostPlayedArtistFromUser().username();


        // Actualizar la UI con los datos obtenidos
        runOnUiThread(() -> {
            textViewGlobalTopSongValue.setText(cancionMasEscuchadaGlobal);
            textViewGlobalTopAlbumValue.setText(albumMasEscuchadoGlobal);
            textViewGlobalTopArtistValue.setText(artistaMasEscuchadoGlobal);
            textViewTopSongValue.setText(cancionMasEscuchadaUsuario);
            textViewTopAlbumValue.setText(albumMasEscuchadoUsuario);
            textViewTopArtistValue.setText(artistaMasEscuchadoUsuario);
        });

    }

}