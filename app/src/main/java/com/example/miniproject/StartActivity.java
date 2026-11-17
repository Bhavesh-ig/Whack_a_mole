package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    TextView startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startBtn = findViewById(R.id.startBtn);

        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.button_pulse);
        startBtn.startAnimation(pulse);

        startBtn.setOnClickListener(v -> {

            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);

        });
    }
}