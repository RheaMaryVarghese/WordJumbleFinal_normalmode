package com.example.wordjumblefinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class home extends AppCompatActivity {


    Button Beginbutton;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME="mypref";
    private static final String KEY_SCORE="score";


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        int score = sharedPreferences.getInt(KEY_SCORE,0);
        TextView best_score = findViewById(R.id.best_score);
        best_score.setText("Best Score:" + score);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.click);
        Beginbutton = (Button) findViewById(R.id.begin);
        Beginbutton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                openMainActivity1();

            }
        });
    }
    public void openMainActivity1() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}


