package me.zurdo.beatbridge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import me.zurdo.beatbridge.music.Album;
import me.zurdo.beatbridge.music.AlbumApi;
import me.zurdo.beatbridge.music.Song;

import java.util.List;

public class AlbumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        ImageView backButton = findViewById(R.id.back);

        // Obtener referencia y rellenar lista de álbumes
        LinearLayout linearLayout = findViewById(R.id.linearLayoutAlbums);
        List<Album> albums = AlbumApi.getAlbums();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Album album : albums) {
            View albumRow = inflater.inflate(R.layout.row_album, linearLayout, false);

            // Configurar los elementos de la fila para el álbum
            TextView nameTextView = albumRow.findViewById(R.id.tvAlbumName);
            nameTextView.setText(album.name());
            ImageView desplegarButton = albumRow.findViewById(R.id.ivDesplegar);
            LinearLayout linearLayoutSongs = albumRow.findViewById(R.id.linearLayoutSongs);

            // Configurar el botón desplegar
            desplegarButton.setOnClickListener(v -> {
                Log.d("AlbumActivity", "Album ID: " + album.id());
                // Verificamos si las canciones están visibles, y las mostramos/ocultamos
                if (linearLayoutSongs.getVisibility() == View.GONE) {
                    linearLayoutSongs.setVisibility(View.VISIBLE);

                    List<Song> songs = AlbumApi.getSongs(album.id());

                    // Rellenar los elementos de la fila con las canciones
                    LayoutInflater songInflater = LayoutInflater.from(this);
                    for (Song song : songs) {
                        View songRow = songInflater.inflate(R.layout.row_song_album, linearLayoutSongs, false);
                        TextView songTextView = songRow.findViewById(R.id.tvSongName);
                        songTextView.setText(song.name());
                        linearLayoutSongs.addView(songRow);
                    }
                } else {
                    // Si ya está visible, lo ocultamos
                    linearLayoutSongs.setVisibility(View.GONE);

                    // Limpiar las canciones para evitar duplicados
                    linearLayoutSongs.removeAllViews();
                }
            });

            // Agregar la fila del álbum al LinearLayout principal
            linearLayout.addView(albumRow);
        }

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
