package me.zurdo.beatbridge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import me.zurdo.beatbridge.music.Song;
import me.zurdo.beatbridge.music.SongApi;

import java.util.List;

public class SongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        ImageView backButton = findViewById(R.id.back);

        // Obtener referencia al LinearLayout donde se agregarán las filas
        LinearLayout linearLayout = findViewById(R.id.linearLayout);

        // Lista de canciones para rellenar dinámicamente
        List<Song> songs = SongApi.getSongs();

        // Inflar y agregar las canciones dinámicamente
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Song song : songs) {
            // Inflar el diseño de la fila
            View songRow = inflater.inflate(R.layout.row_song, linearLayout, false);

            // Configurar los elementos de la fila
            TextView nameTextView = songRow.findViewById(R.id.tvSongName);
            nameTextView.setText(song.name());

            ImageView infoButton = songRow.findViewById(R.id.ivInfo);
            infoButton.setOnClickListener(v -> {
                // Acción al hacer clic en Info
            });

            ImageView playButton = songRow.findViewById(R.id.ivPlay);
            playButton.setOnClickListener(v -> {
                Uri uri = Uri.parse(song.link());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            });

            // Agregar la fila al LinearLayout principal
            linearLayout.addView(songRow);
        }

        // Configurar el botón de "volver"
        backButton.setOnClickListener(v -> finish());
    }
}

