package me.zurdo.beatbridge;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterTypeActivity extends AppCompatActivity {

    private ImageView imageViewListener, imageViewArtist, backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_type);

        // Obtiene referencias a las imágenes que representan las opciones de tipo de usuario
        imageViewListener = findViewById(R.id.imageViewListener);
        imageViewArtist = findViewById(R.id.imageViewArtist);
        backArrow = findViewById(R.id.back);

        // Configura el evento de clic para el ImageView del oyente
        imageViewListener.setOnClickListener(v -> navigateToDetails("Listener"));
        // Configura el evento de clic para el ImageView del artista
        imageViewArtist.setOnClickListener(v -> navigateToDetails("Artist"));

        // Configura el evento de clic para la flecha de retroceso
        backArrow.setOnClickListener(v -> finish());
    }

    // Método privado para navegar a la actividad RegisterDetailsActivity y pasar el tipo de usuario
    private void navigateToDetails(String userType) {
        // Crea un Intent para iniciar la actividad RegisterDetailsActivity
        Intent intent = new Intent(RegisterTypeActivity.this, RegisterDetailsActivity.class);
        intent.putExtra("USER_TYPE", userType);  // Pasa el tipo de usuario ("Listener" o "Artist") como extra en el Intent

        startActivity(intent);  // Inicia la nueva actividad
        finish();  // Finaliza la actividad actual (RegisterTypeActivity) para evitar volver atrás en el historial de navegación
    }
}