package com.example.anagrammer;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MediaPlayer mediaPlayer;
    private TextView wordTv, scoreG, countdown, date, recentAnswer, nameUser;
    private EditText wordEnteredTv;
    private Button validate;
    private String wordToFind;
    int score1 = 0;
    int timer = 60000;
    SharedPreferences sp;
    DatabaseReference seanDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE , MM-dd-yyyy , hh:mm:ss a");
        String currentDate = simpleDateFormat.format(calendar.getTime());

        mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.background);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        recentAnswer = (TextView) findViewById(R.id.tv_info);
        wordTv = (TextView) findViewById(R.id.wordTv);
        wordEnteredTv = (EditText) findViewById(R.id.wordEnteredEt);
        validate = (Button) findViewById(R.id.validate);
        validate.setOnClickListener(this);
        scoreG = (TextView)findViewById(R.id.score);
        countdown = (TextView)findViewById(R.id.timer);
        seanDb = FirebaseDatabase.getInstance().getReference().child("Score");
        date = (TextView) findViewById(R.id.date);

        nameUser = (TextView)findViewById(R.id.nameUser);
        
        sp = getSharedPreferences("Score", Context.MODE_PRIVATE);

        newGame();

        scoreG.setText("" + score1);
        date.setText("" + currentDate);

        SharedPreferences namePref = getApplicationContext().getSharedPreferences("namePref", Context.MODE_PRIVATE);
        String name2 = namePref.getString("nameOfUser", "");
        nameUser.setText(name2);

        new CountDownTimer(timer, 1000) {
            @Override
            public void onTick(long l) {
                countdown.setText("" + l/1000);
            }

            @Override
            public void onFinish() {
                insertScore();

                String finScore = scoreG.getText().toString();
                Intent senderIntent = new Intent(getApplicationContext(), home.class);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("finScore", finScore);
                editor.commit();
                startActivity(senderIntent);
            }
        }.start();
    }
    @Override
    public void onClick(View view) {
        if (view == validate) {
            validate();
        }
    }

    private void validate() {
        String w = wordEnteredTv.getText().toString();
        if (wordToFind.equals(w)) {

            String answerRecent = wordEnteredTv.getText().toString();
            score1++;
            scoreG.setText("" + score1);
            recentAnswer.setText(answerRecent);
            recentAnswer.setTextColor(Color.GREEN);
            newGame();
        } else {
            Toast.makeText(this, "Retry !", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertScore(){
        String nameOfUser = nameUser.getText().toString();
        String finalScore = scoreG.getText().toString();
        String dateTime = date.getText().toString();
        finalScoreGame finalScoreGame = new finalScoreGame(nameOfUser,finalScore, dateTime);
        seanDb.push().setValue(finalScoreGame);
    }

    private void newGame() {
        wordToFind = Anagram.randomWord();
        String wordShuffled = Anagram.shuffleWord(wordToFind);
        wordTv.setText(wordShuffled);
        wordEnteredTv.setText("");

    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Are you Sure?");
        builder.setMessage("want to Exit the Game?");

        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(MainActivity.this, home.class));
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
}

