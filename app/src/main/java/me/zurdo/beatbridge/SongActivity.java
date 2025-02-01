package me.zurdo.beatbridge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import me.zurdo.beatbridge.auth.UserApi;
import me.zurdo.beatbridge.music.Song;
import me.zurdo.beatbridge.music.SongApi;
import me.zurdo.beatbridge.stats.PlayApi;
import java.util.List;

public class SongActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        ImageView backButton = findViewById(R.id.back);

        // Obtener referencia al LinearLayout
        LinearLayout linearLayout = findViewById(R.id.linearLayout);

        // Agregar las canciones a la tabla
        List<Song> songs = SongApi.getSongs();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Song song : songs) {
            // Inflar el diseño de la fila
            View songRow = inflater.inflate(R.layout.row_song, linearLayout, false);

            // Configurar los elementos de la fila
            TextView nameTextView = songRow.findViewById(R.id.tvSongName);
            nameTextView.setText(song.name());

            ImageView infoButton = songRow.findViewById(R.id.ivInfo);
            infoButton.setOnClickListener(v -> {
                Intent intent = new Intent(this, InfoSongActivity.class);
                intent.putExtra("nombre", song.name());
                intent.putExtra("letra", song.lyrics());
                intent.putExtra("artista", UserApi.getUsername(song.artist()));
                startActivity(intent);
            });

            ImageView playButton = songRow.findViewById(R.id.ivPlay);
            playButton.setOnClickListener(v -> {
                try {
                    // Abrir la URL en un navegador externo
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(song.link()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    // Incrementar la canción
                    PlayApi.increment(song.id());
                } catch (Exception e) {
                    Log.e("AppError", "Error al abrir el navegador", e);
                }
            });

            // Agregar la fila al LinearLayout principal
            linearLayout.addView(songRow);
        }

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}

