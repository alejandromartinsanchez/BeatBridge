package me.zurdo.beatbridge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import me.zurdo.beatbridge.music.Album;
import me.zurdo.beatbridge.music.AlbumApi;
import me.zurdo.beatbridge.music.Song;
import me.zurdo.beatbridge.music.SongApi;

public class AddContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);

        // Configurar el botón volver
        ImageView backButton = findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContentActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Referencias a los elementos de la vista
        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextLink = findViewById(R.id.editTextLink);
        EditText editTextLyrics = findViewById(R.id.editTextLyrics);
        CheckBox checkBoxAlbum = findViewById(R.id.checkBoxAlbum);
        EditText editTextAlbumName = findViewById(R.id.editTextAlbumName);
        EditText editTextAlbumYear = findViewById(R.id.editTextAlbumYear);
        LinearLayout albumFieldsContainer = findViewById(R.id.albumFieldsContainer);
        Button buttonAddContent = findViewById(R.id.buttonAddContent);

        // Mostrar u ocultar los campos del álbum
        albumFieldsContainer.setVisibility(View.GONE);
        checkBoxAlbum.setOnCheckedChangeListener((buttonView, isChecked) -> {
            albumFieldsContainer.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        // Lógica para añadir la canción
        buttonAddContent.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String link = editTextLink.getText().toString().trim();
            String lyrics = editTextLyrics.getText().toString().trim();

            if (name.isEmpty() || link.isEmpty()) {
                Toast.makeText(this, "El nombre y el enlace son obligatorios.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (checkBoxAlbum.isChecked()) {
                String albumName = editTextAlbumName.getText().toString().trim();
                String albumYearText = editTextAlbumYear.getText().toString().trim();

                if (albumName.isEmpty() || albumYearText.isEmpty()) {
                    Toast.makeText(this, "Completa el nombre y año del álbum.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int albumYear;
                try {
                    albumYear = Integer.parseInt(albumYearText);
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "El año del álbum debe ser un número entero.", Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread(() -> {
                    Album createdAlbum = AlbumApi.getOrCreateAlbum(albumName, albumYear);
                    if (createdAlbum != null) {
                        Song song = new Song(-1, name, lyrics, LoginActivity.registeredUser.id(), createdAlbum.id(), link);
                        SongApi.createSong(song);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Canción añadida con álbum.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, ContentActivity.class);
                            startActivity(intent);
                            finish();
                        });
                    } else {
                        runOnUiThread(() -> Toast.makeText(this, "Error al crear el álbum.", Toast.LENGTH_SHORT).show());
                    }
                }).start();

            } else {
                new Thread(() -> {
                    Song song = new Song(-1, name, lyrics, LoginActivity.registeredUser.id(), null, link);
                    SongApi.createSong(song);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Canción añadida sin álbum.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, ContentActivity.class);
                        startActivity(intent);
                        finish();
                    });
                }).start();
            }
        });
    }
}

