package me.zurdo.beatbridge;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewNombreValor;
    private TextView textViewCorreoValor;
    private Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Inicializar vistas
        ImageView backButton = findViewById(R.id.back);
        textViewNombreValor = findViewById(R.id.textViewNameValue);
        textViewCorreoValor = findViewById(R.id.textViewEmailValue);
        btnCerrarSesion = findViewById(R.id.buttonLogout);

        // Configurar el botón volver
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        loadUserData();

        // Configurar boton cerrar sesión
        btnCerrarSesion.setOnClickListener(v -> {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookies(null);
            cookieManager.flush();

            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();

            // Redirigir a LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void loadUserData() {
        textViewNombreValor.setText(LoginActivity.registeredUser.username());
        textViewCorreoValor.setText(LoginActivity.registeredUser.email());


    }
}
