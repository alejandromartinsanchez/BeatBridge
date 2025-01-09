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
        selectedLayout = Main.registeredUser.role() == User.Role.LISTENER
                ? findViewById(R.id.layout_listener)
                : findViewById(R.id.layout_artist);
        setupUI();
    }

    private void setupUI() {
        toggleVisibility(selectedLayout);
        if (Main.registeredUser.role() == User.Role.LISTENER) {
            setupListenerUI();
        } else if (Main.registeredUser.role() == User.Role.ARTIST) {
            setupArtistUI();
        }
    }

    private void setupListenerUI() {
        titleText = selectedLayout.findViewById(R.id.textViewTitle);
        imageViewProfile = selectedLayout.findViewById(R.id.imageViewProfile);
        ImageView imageViewSongs = selectedLayout.findViewById(R.id.imageViewSongs);
        ImageView imageViewStats = selectedLayout.findViewById(R.id.imageViewStats);

        titleText.setText("Bienvenido, Listener");

        imageViewProfile.setOnClickListener(v -> navigateToProfile());
        imageViewSongs.setOnClickListener(v -> navigateToSongs());
        imageViewStats.setOnClickListener(v -> navigateToStats());
    }

    private void setupArtistUI() {
        Log.d("HomeActivity", "setupArtistUI called");
        titleText = selectedLayout.findViewById(R.id.textViewTitle);
        imageViewProfile = selectedLayout.findViewById(R.id.imageViewProfile);
        ImageView imageViewSongs = selectedLayout.findViewById(R.id.imageViewSongs);
        ImageView imageViewStats = selectedLayout.findViewById(R.id.imageViewStats);
        ImageView imageViewAddContent = selectedLayout.findViewById(R.id.imageContent);

        titleText.setText("Bienvenido, Artista");

        imageViewProfile.setOnClickListener(v -> navigateToProfile());
        imageViewSongs.setOnClickListener(v -> navigateToSongs());
        imageViewStats.setOnClickListener(v -> navigateToStats());
        imageViewAddContent.setOnClickListener(v -> navigateToAddContent());
    }

    private void toggleVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void navigateToProfile() {
        System.out.println("Navegando a perfil");
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void navigateToSongs() {
        System.out.println("Navegando a canciones");
        Intent intent = new Intent(this, SongActivity.class);
        startActivity(intent);
    }

    private void navigateToStats() {
        System.out.println("Navegando a estadisticas");
        Intent intent = new Intent(this, StatsActivity.class);
        startActivity(intent);
    }

    private void navigateToAddContent() {
        System.out.println("Navegando a añadir contenido");
        Intent intent = new Intent(this, ContentActivity.class);
        startActivity(intent);
    }
}