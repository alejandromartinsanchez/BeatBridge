package me.zurdo.beatbridge;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class AddContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);

        // Configuración del botón de retroceso
        ImageView backButton = findViewById(R.id.back);
        backButton.setOnClickListener(v -> finish());

        // Referencias a los elementos de la vista
        CheckBox checkBoxAlbum = findViewById(R.id.checkBoxAlbum);
        LinearLayout albumFieldsContainer = findViewById(R.id.albumFieldsContainer);

        // Listener para el CheckBox
        albumFieldsContainer.setVisibility(View.GONE);
        checkBoxAlbum.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                albumFieldsContainer.setVisibility(View.VISIBLE);
            } else {
                albumFieldsContainer.setVisibility(View.GONE);
            }
        });
    }
}