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
    private EditText editTextEmail, editTextPassword, editTextName;
    private Button buttonRegister;
    private ImageView backArrow;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_details);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextArtistName);
        buttonRegister = findViewById(R.id.buttonRegister);
        backArrow = findViewById(R.id.back);

        // Configurar el boton volver
        backArrow.setOnClickListener(v -> navigateBackToRegisterType());

        // Obtener el tipo de usuario
        userType = getIntent().getStringExtra("USER_TYPE");

        if ("Artist".equals(userType)) {
            editTextName.setVisibility(View.VISIBLE);
            editTextName.setHint("Nombre del Artista");
        } else {
            editTextName.setVisibility(View.VISIBLE);
            editTextName.setHint("Nombre de Usuario");
        }

        // Configurar el boton registro
        buttonRegister.setOnClickListener(v -> completeRegistration());
    }

    private void navigateBackToRegisterType() {
        Intent intent = new Intent(this, RegisterTypeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    // Completar el registro y mostrar información del usuario
    private void completeRegistration() {
        String username = editTextName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        RegisterApi.register(username, password, email, userType.toUpperCase());
    }
}