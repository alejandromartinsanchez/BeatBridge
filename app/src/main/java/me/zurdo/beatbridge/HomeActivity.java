package me.zurdo.beatbridge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import me.zurdo.beatbridge.auth.User;

public class HomeActivity extends AppCompatActivity {

    private TextView titleText;
    private ImageView imageViewProfile;
    private View selectedLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Configurar la interfaz según el rol
        selectedLayout = LoginActivity.registeredUser.role() == User.Role.LISTENER
                ? findViewById(R.id.layout_listener)
                : findViewById(R.id.layout_artist);
        setupUI();
    }

    // Configurar la interfaz según el diseño y los elementos correspondientes al rol
    private void setupUI() {
        toggleVisibility(selectedLayout);
        if (LoginActivity.registeredUser.role() == User.Role.LISTENER) {
            setupListenerUI();
        } else if (LoginActivity.registeredUser.role() == User.Role.ARTIST) {
            setupArtistUI();
        }
    }

    //Configurar Oyente
    private void setupListenerUI() {
        titleText = selectedLayout.findViewById(R.id.textViewTitle);
        imageViewProfile = selectedLayout.findViewById(R.id.imageViewProfile);
        ImageView imageViewSongs = selectedLayout.findViewById(R.id.imageViewSongs);
        ImageView imageViewAlbums = selectedLayout.findViewById(R.id.imageViewAlbums);
        ImageView imageViewStats = selectedLayout.findViewById(R.id.imageViewStats);

        titleText.setText("BeatBridge");

        imageViewProfile.setOnClickListener(v -> navigateToProfile());
        imageViewSongs.setOnClickListener(v -> navigateToSongs());
        imageViewAlbums.setOnClickListener(v -> navigateToAlbums());
        imageViewStats.setOnClickListener(v -> navigateToStats());
    }

    //Configurar Artista
    private void setupArtistUI() {
        Log.d("HomeActivity", "setupArtistUI called");
        titleText = selectedLayout.findViewById(R.id.textViewTitle);
        imageViewProfile = selectedLayout.findViewById(R.id.imageViewProfile);
        ImageView imageViewSongs = selectedLayout.findViewById(R.id.imageViewSongs);
        ImageView imageViewAlbums = selectedLayout.findViewById(R.id.imageViewAlbums);
        ImageView imageViewStats = selectedLayout.findViewById(R.id.imageViewStats);
        ImageView imageViewAddContent = selectedLayout.findViewById(R.id.imageContent);

        titleText.setText("BeatBridge");

        imageViewProfile.setOnClickListener(v -> navigateToProfile());
        imageViewSongs.setOnClickListener(v -> navigateToSongs());
        imageViewAlbums.setOnClickListener(v -> navigateToAlbums());
        imageViewStats.setOnClickListener(v -> navigateToStats());
        imageViewAddContent.setOnClickListener(v -> navigateToAddContent());
    }

    // Alternar la visibilidad
    private void toggleVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
    // Configurar el botón perfil
    private void navigateToProfile() {
        System.out.println("Navegando a perfil");
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    //Configurar el botón canciones
    private void navigateToSongs() {
        System.out.println("Navegando a canciones");
        Intent intent = new Intent(this, SongActivity.class);
        startActivity(intent);
    }

    //Configurar el boton álbumes
    private void navigateToAlbums() {
        System.out.println("Navegando a álbumes");
        Intent intent = new Intent(this, AlbumActivity.class);
        startActivity(intent);
    }

    //Configurar el boton estadisticas
    private void navigateToStats() {
        System.out.println("Navegando a estadisticas");
        Intent intent = new Intent(this, StatsActivity.class);
        startActivity(intent);
    }

    //Configurar el boton contenido
    private void navigateToAddContent() {
        System.out.println("Navegando a añadir contenido");
        Intent intent = new Intent(this, ContentActivity.class);
        startActivity(intent);
    }
}