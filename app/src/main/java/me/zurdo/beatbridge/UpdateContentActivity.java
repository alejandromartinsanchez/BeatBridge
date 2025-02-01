package me.zurdo.beatbridge;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import me.zurdo.beatbridge.music.Song;
import me.zurdo.beatbridge.music.SongApi;

public class UpdateContentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_content);
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        ImageView backButton = findViewById(R.id.back);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContentActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Obtener las canciones del usuario
        long userId = LoginActivity.registeredUser.id();
        List<Song> songs = SongApi.getSongsFromUser(userId);

        // Rellenar la tabla si hay canciones
        if (songs != null && !songs.isEmpty()) {
            fillTableWithSongs(linearLayout, songs);
        }
    }

    private void fillTableWithSongs(LinearLayout linearLayout, List<Song> songs) {
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Song song : songs) {
            // Inflar la fila correcta
            View songRow = inflater.inflate(R.layout.row_update_content, linearLayout, false);

            // Configurar el nombre de la canción
            TextView nameTextView = songRow.findViewById(R.id.tvSongName);
            nameTextView.setText(song.name());

            // Configurar el botón de actualización
            ImageView updateButton = songRow.findViewById(R.id.ivUpdate);
            updateButton.setOnClickListener(v -> {
                // Actualizar la canción
                Intent intent = new Intent(this, UpdateSongActivity.class);
                // Pasar los datos de la canción
                intent.putExtra("song_id", song.id());
                intent.putExtra("song_name", song.name());
                intent.putExtra("song_lyrics", song.lyrics());
                intent.putExtra("song_link", song.link());
                intent.putExtra("song_album", song.album());
                startActivity(intent);
            });

            // Agregar la fila al LinearLayout
            linearLayout.addView(songRow);
        }
    }

}
