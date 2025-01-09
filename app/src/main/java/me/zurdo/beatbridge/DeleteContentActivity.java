package me.zurdo.beatbridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import me.zurdo.beatbridge.music.Song;
import me.zurdo.beatbridge.music.SongApi;

import java.util.List;

public class DeleteContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_content);
        ImageView backButton = findViewById(R.id.back);

        // Obtener referencia al LinearLayout donde se agregarán las filas
        LinearLayout linearLayout = findViewById(R.id.linearLayout);

        // Lista de canciones (sin filtrar por usuario por ahora)
        List<Song> songs = SongApi.getSongs();

        // Inflar y agregar las canciones dinámicamente
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Song song : songs) {
            // Inflar el diseño de la fila
            View songRow = inflater.inflate(R.layout.row_delete_content, linearLayout, false);

            // Configurar los elementos de la fila
            TextView nameTextView = songRow.findViewById(R.id.tvSongName);
            nameTextView.setText(song.name());

            CheckBox songCheckBox = songRow.findViewById(R.id.songCheckBox);
            songCheckBox.setTag(song);  // Asociar el objeto canción al checkbox

            // Agregar la fila al LinearLayout principal
            linearLayout.addView(songRow);
        }

        // Configurar el botón de "volver"
        backButton.setOnClickListener(v -> finish());
    }
}

