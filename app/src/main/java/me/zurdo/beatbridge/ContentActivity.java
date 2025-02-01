package me.zurdo.beatbridge;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ImageView backButton = findViewById(R.id.back);

        // Configurar el botón volver
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Configurar el botón añadir contenido
        ImageView addContentButton = findViewById(R.id.imageViewAdd);
        addContentButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContentActivity.this, AddContentActivity.class);
            startActivity(intent);
        });

        // Configurar el botón eliminar contenido
        ImageView deleteContentButton = findViewById(R.id.imageViewDelete);
        deleteContentButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContentActivity.this, DeleteContentActivity.class);
            startActivity(intent);
        });

        // Configurar el botón actualizar contenido
        ImageView updateContentButton = findViewById(R.id.imageViewUpdate);
        updateContentButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContentActivity.this, UpdateContentActivity.class);
            startActivity(intent);
        });
    }
}