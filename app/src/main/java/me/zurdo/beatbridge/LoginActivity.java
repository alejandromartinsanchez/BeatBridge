package me.zurdo.beatbridge;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import me.zurdo.beatbridge.auth.LoginApi;
import me.zurdo.beatbridge.auth.User;
import me.zurdo.beatbridge.auth.ValidateApi;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        //Configurar los insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configurar el botón Login
        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(v -> {
            System.out.println("cookie:" + Main.cookies.getCookieByName("auth"));
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            LoginApi.login(username, password, new LoginApi.LoginCallback() {
                @Override
                public void onSuccess() {
                    // Ejecutar la validación del token en un hilo de fondo
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // Validar token en segundo plano
                            Main.registeredUser = ValidateApi.validateToken();

                            // Usar runOnUiThread para interactuar con la UI
                            runOnUiThread(() -> {
                                if (Main.registeredUser != null) {
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Validación del token fallida.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                }

                @Override
                public void onFailure(String errorMessage) {
                    runOnUiThread(() -> {
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });



        // Configurar el botón Register
        Button buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterTypeActivity.class);
            startActivity(intent);
        });
    }
}
