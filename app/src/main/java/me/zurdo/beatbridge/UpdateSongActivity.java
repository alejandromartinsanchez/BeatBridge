package me.zurdo.beatbridge;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import me.zurdo.beatbridge.music.Song;
import me.zurdo.beatbridge.music.SongApi;

public class UpdateSongActivity extends AppCompatActivity {
    private EditText editTextName, editTextLyrics, editTextLink;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_song);

        // Recuperar los datos de la canción enviados desde UpdateContentActivity
        String songName = getIntent().getStringExtra("song_name");
        String songLyrics = getIntent().getStringExtra("song_lyrics");
        String songLink = getIntent().getStringExtra("song_link");

        // Rellenar los campos con la información recibida
        editTextName = findViewById(R.id.editTextName);
        editTextLyrics = findViewById(R.id.editTextLetra);
        editTextLink = findViewById(R.id.editTextLink);

        editTextName.setText(songName);
        editTextLyrics.setText(songLyrics);
        editTextLink.setText(songLink);
        btnUpdate = findViewById(R.id.buttonUpdate);
        ImageView backButton = findViewById(R.id.back);

        // Configurar el botón actualizar
        btnUpdate.setOnClickListener(v -> updateSong());

        // Configurar el botón volver
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, UpdateContentActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void updateSong() {
        // Obtener los datos del formulario
        long songId = getIntent().getLongExtra("song_id", -1);
        String updatedName = editTextName.getText().toString();
        String updatedLyrics = editTextLyrics.getText().toString();
        String updatedLink = editTextLink.getText().toString();
        Long albumId = getIntent().getLongExtra("song_album", -1);


        // Validar que los campos no estén vacíos
        if (updatedName.isEmpty() || updatedLyrics.isEmpty() || updatedLink.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar el albumId
        albumId = (albumId <= 0) ? null : albumId;

        // Crear el objeto Song con los nuevos datos
        Song updatedSong = new Song(songId, updatedName, updatedLyrics, LoginActivity.registeredUser.id(), albumId, updatedLink);

        // Llamar a la función de actualización de la API

        updatedSong = SongApi.updateSong(updatedSong);

        // Mostrar un mensaje de éxito
        Toast.makeText(this, "Canción actualizada correctamente", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, ContentActivity.class);
        startActivity(intent);
        finish();
    }
}
