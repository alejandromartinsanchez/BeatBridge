package me.zurdo.beatbridge;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterTypeActivity extends AppCompatActivity {

    private ImageView imageViewListener, imageViewArtist, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_type);

        imageViewListener = findViewById(R.id.imageViewListener);
        imageViewArtist = findViewById(R.id.imageViewArtist);
        backButton = findViewById(R.id.back);

        // Configurar el boton oyente
        imageViewListener.setOnClickListener(v -> navigateToDetails("Listener"));
        // Configura el boton artista
        imageViewArtist.setOnClickListener(v -> navigateToDetails("Artist"));

        // Configura el boton volver
        backButton.setOnClickListener(v -> finish());
    }


    private void navigateToDetails(String userType) {
        Intent intent = new Intent(RegisterTypeActivity.this, RegisterDetailsActivity.class);
        intent.putExtra("USER_TYPE", userType);
        startActivity(intent);
        finish();
    }
}