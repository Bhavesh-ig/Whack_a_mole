package com.example.miniproject;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView[] moles = new ImageView[9];

    TextView scoreText;
    TextView timerText;

    int score = 0;
    int activeMole = -1;

    Random random = new Random();
    Handler handler = new Handler();

    MediaPlayer hitSound;
    MediaPlayer popSound;
    MediaPlayer bgMusic;

    Runnable moleRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreText = findViewById(R.id.scoreText);
        timerText = findViewById(R.id.timerText);

        moles[0] = findViewById(R.id.mole1);
        moles[1] = findViewById(R.id.mole2);
        moles[2] = findViewById(R.id.mole3);
        moles[3] = findViewById(R.id.mole4);
        moles[4] = findViewById(R.id.mole5);
        moles[5] = findViewById(R.id.mole6);
        moles[6] = findViewById(R.id.mole7);
        moles[7] = findViewById(R.id.mole8);
        moles[8] = findViewById(R.id.mole9);

        hitSound = MediaPlayer.create(this, R.raw.hit);
        popSound = MediaPlayer.create(this, R.raw.pop);

        bgMusic = MediaPlayer.create(this, R.raw.bg_music);
        bgMusic.setLooping(true);
        bgMusic.start();

        for (int i = 0; i < moles.length; i++) {

            int index = i;

            moles[i].setOnClickListener(v -> {

                if (index == activeMole) {

                    score++;
                    scoreText.setText("Score: " + score);

                    hitSound.start();

                    moles[index].setVisibility(View.GONE);

                    activeMole = -1;
                }
            });
        }

        startGame();
    }

    private void startGame() {

        moleRunnable = new Runnable() {
            @Override
            public void run() {

                for (ImageView mole : moles) {
                    mole.setVisibility(View.GONE);
                }

                activeMole = random.nextInt(9);

                moles[activeMole].setVisibility(View.VISIBLE);

                Animation pop = AnimationUtils.loadAnimation(MainActivity.this, R.anim.mole_pop);
                moles[activeMole].startAnimation(pop);

                popSound.start();

                handler.postDelayed(this, 700);
            }
        };

        handler.post(moleRunnable);

        new CountDownTimer(30000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                timerText.setText("Time: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {

                handler.removeCallbacks(moleRunnable);

                showGameOver();
            }

        }.start();
    }

    private void showGameOver() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Game Over");
        builder.setMessage("Your Score: " + score);

        builder.setPositiveButton("Restart", (d, i) -> recreate());
        builder.setNegativeButton("Exit", (d, i) -> finish());

        builder.show();
    }
}