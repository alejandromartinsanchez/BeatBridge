package me.zurdo.beatbridge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import me.zurdo.beatbridge.auth.RegisterApi;

public class RegisterDetailsActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword, editTextArtistName;
    private Button buttonRegister;
    private ImageView backArrow;
    private String userType;  // Variable para almacenar el tipo de usuario ("Artist" u "Oyente")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_details);

        // Referencias a elementos de la interfaz
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextArtistName = findViewById(R.id.editTextArtistName);
        buttonRegister = findViewById(R.id.buttonRegister);
        backArrow = findViewById(R.id.backArrow);

        // Configura la funcionalidad de la flecha de retroceso
        backArrow.setOnClickListener(v -> navigateBackToRegisterType());

        // Obtiene el valor del tipo de usuario ("Artist" u "Oyente") pasado desde otra actividad
        userType = getIntent().getStringExtra("USER_TYPE");

        if ("Artist".equals(userType)) {
            // Muestra solo el campo del nombre del artista y configura el hint correspondiente
            editTextArtistName.setVisibility(View.VISIBLE);
            editTextArtistName.setHint("Nombre del Artista");
        } else {
            // Para el caso del oyente, configura el hint como "Nombre de Usuario"
            editTextArtistName.setVisibility(View.VISIBLE);
            editTextArtistName.setHint("Nombre de Usuario");
        }

        // Configura el evento del botón de registro
        buttonRegister.setOnClickListener(v -> completeRegistration());
    }

    // Método para navegar de regreso a la actividad RegisterTypeActivity
    private void navigateBackToRegisterType() {
        Intent intent = new Intent(RegisterDetailsActivity.this, RegisterTypeActivity.class);
        startActivity(intent);
        finish();
    }

    // Completar el registro y mostrar información del usuario
    private void completeRegistration() {
        String username = editTextArtistName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        RegisterApi.register(username, email, password, userType.toUpperCase());
        if ("Artist".equals(userType)) {
            String artistName = editTextArtistName.getText().toString().trim();
            System.out.println("Registrado como Artista: " + artistName + ", Email: " + email);
        } else {
            System.out.println("Registrado como Oyente: " + username + ", Email: " + email);
        }
    }
}