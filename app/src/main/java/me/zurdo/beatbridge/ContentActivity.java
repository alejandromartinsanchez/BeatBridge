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

        // Configurar el botón de retroceso
        ImageView backButton = findViewById(R.id.back);
        backButton.setOnClickListener(v -> finish());

        // Imagen para añadir contenido
        ImageView addContentButton = findViewById(R.id.imageViewAdd);
        addContentButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContentActivity.this, AddContentActivity.class);
            startActivity(intent);
        });

        // Imagen para eliminar contenido
        ImageView deleteContentButton = findViewById(R.id.imageViewDelete);
        deleteContentButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContentActivity.this, DeleteContentActivity.class);
            startActivity(intent);
        });

        // Imagen para actualizar contenido
        ImageView updateContentButton = findViewById(R.id.imageViewUpdate);
        updateContentButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContentActivity.this, UpdateContentActivity.class);
            startActivity(intent);
        });
    }
}