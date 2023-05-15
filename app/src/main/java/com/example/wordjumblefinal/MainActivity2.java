package com.example.wordjumblefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;




public class MainActivity2 extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ImageButton ImageButton;
    Button Okbutton,reset,check,home,playagain;

    String guess = "";

    int score=300,count=3,len,blanks_filled=0,k=0;
    List<Integer> ids,click_ids,button_ids;
    List<Character> letters,alphabets;

    private static final String SHARED_PREF_NAME="mypref";
    private static final String KEY_SCORE="score";
    MediaPlayer mediaPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_main);

        mediaPlayer = MediaPlayer.create(this,R.raw.click);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        ImageButton = (ImageButton) findViewById(R.id.info);
        ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        Intent intent = getIntent();
        String word = intent.getStringExtra(MainActivity.EXTRA_TEXT1);


        GridLayout gridLayout = findViewById(R.id.gridLayout);
        LinearLayout lm = (LinearLayout) findViewById(R.id.linearmain);
        LinearLayout.LayoutParams paramslin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        letters = new ArrayList<>();
        for (char letter : word.toCharArray()) {
            letters.add(letter);
        }


        len = word.length();
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        alphabets = new ArrayList<>();
        for (char letter : alpha.toCharArray()) {
            alphabets.add(letter);
        }
        Collections.shuffle(alphabets);

        for (int i = 0; i < 16 - len; i++) {
            letters.add(alphabets.get(i));
        }
        Collections.shuffle(letters);

        ids = new ArrayList<>();
        click_ids = new ArrayList<>();
        button_ids = new ArrayList<>();

        for (int i = 0; i < 16; i++) {
            Button button = new Button(this);
            int buttonId = View.generateViewId();
            button.setId(buttonId);
            button_ids.add(buttonId);
            button.setText(String.valueOf(letters.get(i % letters.size())));
            button.setTextSize(24);
            button.setAllCaps(true);
            button.setBackgroundColor(Color.parseColor("#E4A6EF"));
            button.setOnClickListener(new View.OnClickListener() {
                private void setWorkings(CharSequence buttonText) {

                        guess = guess + buttonText;
                        TextView ans = findViewById(ids.get(k));
                        ans.setText(buttonText);
                        k+=1;

                }

                @Override
                public void onClick(View v) {
                    if (k==len)
                    {
                        Toast.makeText(MainActivity2.this, "You've entered maximum no. of letters!", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        mediaPlayer.start();
                        button.setTextColor(Color.parseColor("#ffffff"));
                        button.setBackgroundColor(Color.parseColor("#6e4887"));
                        button.setEnabled(false);
                        //int buttonId = View.generateViewId();
                        //button.setId(buttonId);
                        click_ids.add(buttonId);
                        CharSequence buttonText = button.getText();
                        setWorkings(buttonText);
                        blanks_filled+=1;
                    }

                }

            });


            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(i % 4, 1f);
            params.rowSpec = GridLayout.spec(i / 4, 1f);
            params.setMargins(20,20,20,20);
            button.setLayoutParams(params);
            gridLayout.addView(button);
        }


        for (int i = 0; i < len; i++) {
            TextView tv = new TextView(this);
            int tvId = View.generateViewId();
            tv.setId(tvId);
            ids.add(tvId);
            //tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv.setPadding(20, 20, 20, 20);
            tv.setTextSize(30);
            tv.setTextColor(Color.parseColor("#ffffff"));
            tv.setAllCaps(true);
            tv.setBackgroundColor(Color.parseColor("#6e4887"));
            tv.setText("_");
            lm.addView(tv);

        }

    }

    public void resetOnClick (View view)
    {
        mediaPlayer.start();
        guess = "";
        Toast.makeText(this, "Reset Successful", Toast.LENGTH_SHORT).show();
        for (int b=0;b<k;b++) {
            TextView ans = findViewById(ids.get(b));
            ans.setText("_");
        }
        for (int i=0;i<click_ids.size();i++) {
            Button button = findViewById(click_ids.get(i));
            button.setTextColor(Color.parseColor("#000000"));
            button.setBackgroundColor(Color.parseColor("#E4A6EF"));
            button.setEnabled(true);
        }
        click_ids.clear();
        k=0;
        blanks_filled=0;
    }

    public void checkOnClick(View view) {
        mediaPlayer.start();
        Intent intent = getIntent();
        String word = intent.getStringExtra(MainActivity.EXTRA_TEXT1);
        if (blanks_filled != len) {
            Toast.makeText(this, "Fill all the blanks!!", Toast.LENGTH_SHORT).show();
        } else if (guess.equals(word)) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            if (score > sharedPreferences.getInt(KEY_SCORE, 0)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_SCORE, score);
                editor.apply();
            }
            showDialog();
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
            //Shuffle grid
            Collections.shuffle(letters);
            for (int i = 0; i < 16; i++) {
                Button button2 = findViewById(button_ids.get(i));
                button2.setText(String.valueOf(letters.get(i % letters.size())));
            }

            guess = "";
            k = 0;
            score = score - 100;
            count = count - 1;
            for (int i = 0; i < click_ids.size(); i++) {
                Button button = findViewById(click_ids.get(i));
                button.setTextColor(Color.parseColor("#000000"));
                button.setBackgroundColor(Color.parseColor("#E4A6EF"));
                button.setEnabled(true);
            }
            click_ids.clear();
            for (int b = 0; b < len; b++) {
                TextView ans = findViewById(ids.get(b));
                ans.setText("_");
            }
            blanks_filled = 0;

            if (count == 2) {
                ImageView heart = (ImageView) findViewById(R.id.heart3);
                int color = Color.parseColor("#046134");
                heart.setColorFilter(color);

            } else if (count == 1) {
                ImageView heart = (ImageView) findViewById(R.id.heart2);
                int color = Color.parseColor("#046134");
                heart.setColorFilter(color);
            } else if (count == 0) {
                ImageView heart = (ImageView) findViewById(R.id.heart1);
                int color = Color.parseColor("#046134");
                heart.setColorFilter(color);
                if (score > sharedPreferences.getInt(KEY_SCORE, 0)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(KEY_SCORE, score);
                    editor.apply();
                }
                showDialog();
            }
        }
    }




        private void openDialog() {
            Intent intent = getIntent();
            String clue = intent.getStringExtra(MainActivity.EXTRA_TEXT2);
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.clue);
            TextView hint = dialog.findViewById(R.id.clue_string);
            dialog.setCancelable(false);
            dialog.show();
            hint.setText(clue);
            dialog.show();
            Okbutton = (Button) dialog.findViewById(R.id.Okbutton);
            Okbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }

            });
        }

    private void showDialog() {
        Dialog dialog1 = new Dialog(this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.gameover);
        TextView result = dialog1.findViewById(R.id.finalscore);
        result.setText(String.valueOf(score));
        dialog1.setCancelable(false);
        dialog1.show();
        home = (Button) dialog1.findViewById(R.id.homebutton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                openhome();
            }

        });
        playagain = (Button) dialog1.findViewById(R.id.playagain);
        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                openMainActivity();
            }

        });

    }

    public void openMainActivity() {

        Intent intent1 = new Intent(this, MainActivity.class);
        startActivity(intent1);
    }
    public void openhome() {

        Intent intent2 = new Intent(this, home.class);
        startActivity(intent2);
    }


        }




