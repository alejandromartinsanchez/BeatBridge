package me.zurdo.beatbridge;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import me.zurdo.beatbridge.music.Song;
import me.zurdo.beatbridge.music.SongApi;
import java.util.ArrayList;
import java.util.List;

public class DeleteContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_content);

        ImageView backButton = findViewById(R.id.back);
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        Button deleteButton = findViewById(R.id.buttonDelete);

        // Configurar el botón volver
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContentActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Obtener las canciones del usuario
        long userId = LoginActivity.registeredUser.id();
        List<Song> songs = SongApi.getSongsFromUser(userId);

        // Asegurarse de que la lista de canciones no sea nula
        if (songs != null) {
            fillTableWithSongs(linearLayout, songs);
        }

        // Configurar el botón eliminar
        deleteButton.setOnClickListener(v -> deleteSelectedSongs(linearLayout));
    }

    private void fillTableWithSongs(LinearLayout linearLayout, List<Song> songs) {
        LayoutInflater inflater = LayoutInflater.from(this);

        // Añadir el encabezado a la tabla
        if (linearLayout.getChildCount() == 0) {
            LinearLayout headerRow = findViewById(R.id.headerRow);
            linearLayout.addView(headerRow);
        }

        // Rellenar la tabla con las canciones
        for (Song song : songs) {
            View songRow = inflater.inflate(R.layout.row_delete_content, linearLayout, false);
            TextView nameTextView = songRow.findViewById(R.id.tvSongName);
            nameTextView.setText(song.name());

            CheckBox songCheckBox = songRow.findViewById(R.id.songCheckBox);
            songCheckBox.setTag(song);
            linearLayout.addView(songRow);
        }
    }

    private void deleteSelectedSongs(LinearLayout linearLayout) {
        List<Song> selectedSongs = new ArrayList<>();

        // Iterar sobre cada fila del LinearLayout y comprobar qué CheckBox están seleccionados
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View songRow = linearLayout.getChildAt(i);
            CheckBox songCheckBox = songRow.findViewById(R.id.songCheckBox);

            if (songCheckBox == null) {
                continue;
            }
            if (songCheckBox.isChecked()) {
                // Si el CheckBox está marcado, agregar la canción a la lista de canciones seleccionadas
                Song song = (Song) songCheckBox.getTag();
                selectedSongs.add(song);
            }
        }

        if (selectedSongs.isEmpty()) {
            return;
        }

        // Llamar al método para eliminar las canciones en el servidor
        SongApi.deleteSongs(selectedSongs);

        // Eliminar las canciones seleccionadas
        for (Song selectedSong : selectedSongs) {
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                View songRow = linearLayout.getChildAt(i);
                CheckBox songCheckBox = songRow.findViewById(R.id.songCheckBox);
                if (songCheckBox != null) {
                    Song song = (Song) songCheckBox.getTag();
                    if (song.id() == selectedSong.id()) {
                        linearLayout.removeView(songRow);
                        break;
                    }
                }
            }
        }

        // Recargar las canciones desde el servidor
        long userId = LoginActivity.registeredUser.id();
        List<Song> updatedSongs = SongApi.getSongsFromUser(userId);
        if (updatedSongs != null) {
            // Eliminar las filas de canciones
            for (int i = linearLayout.getChildCount() - 1; i >= 0; i--) {
                View childView = linearLayout.getChildAt(i);
                if (childView.findViewById(R.id.songCheckBox) != null) {
                    linearLayout.removeViewAt(i);
                }
            }
            // Añadir las filas de canciones actualizadas
            fillTableWithSongs(linearLayout, updatedSongs);
        }
    }


}
