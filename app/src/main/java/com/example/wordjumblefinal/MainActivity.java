package com.example.wordjumblefinal;

import static android.app.Notification.EXTRA_TEXT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT1 = "com.example.application.example.EXTRA_TEXT1";
    public static final String EXTRA_TEXT2 = "com.example.application.example.EXTRA_TEXT2";
    String word,clue;
    EditText wordin,cluein;
    Button startbutton;
    int rows,columns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordin = (EditText) findViewById(R.id.wordin);
        cluein = (EditText) findViewById(R.id.cluein);

        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.click);
        startbutton = (Button) findViewById(R.id.startbutton);
        startbutton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();

                    word=wordin.getText().toString();
                    clue=cluein.getText().toString();
                    if (word.equals(""))
                    {
                        Toast.makeText(MainActivity.this,"Enter a word", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        openMainActivity2();
                    }


            }
        });
    }
    public void openMainActivity2() {

        wordin = (EditText) findViewById(R.id.wordin);
        cluein = (EditText) findViewById(R.id.cluein);
        word = wordin.getText().toString();
        clue = cluein.getText().toString();

        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra(EXTRA_TEXT1,word);
        intent.putExtra(EXTRA_TEXT2,clue);
        startActivity(intent);
    }
}