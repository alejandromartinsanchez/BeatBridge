package me.zurdo.beatbridge;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ImageView backButton = findViewById(R.id.back);
        backButton.setOnClickListener(v -> finish());
    }
}
