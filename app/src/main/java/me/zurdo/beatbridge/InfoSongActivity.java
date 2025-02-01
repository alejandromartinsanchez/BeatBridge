package me.zurdo.beatbridge;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class InfoSongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_song);

        // Obtener referencias a los TextView
        TextView textViewNombreValor = findViewById(R.id.textViewNameValue);
        TextView textViewLetraValor = findViewById(R.id.textViewLyricsValue);
        TextView textViewArtistaValor = findViewById(R.id.textViewArtistValue);
        ImageView backButton = findViewById(R.id.back);

        // Obtener los datos pasados desde SongActivity
        String nombre = getIntent().getStringExtra("nombre");
        String letra = getIntent().getStringExtra("letra");
        String artista = getIntent().getStringExtra("artista");

        // Asignar los datos a los TextView
        textViewNombreValor.setText(nombre);
        textViewLetraValor.setText(letra);
        textViewArtistaValor.setText(artista);

        // Botón de retroceso
        // Configurar el botón volver
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SongActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
