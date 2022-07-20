package com.example.anagrammer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class home extends AppCompatActivity {

    private FirebaseUser mUser;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String userID;
    Button startgame, history, vocabulary;
    TextView scoreBoard, userNameGame;
    ImageView help, userAccount;

    SharedPreferences namePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        scoreBoard = (TextView) findViewById(R.id.yourScore);
        userNameGame = (TextView) findViewById(R.id.nameUser);

        startgame = (Button) findViewById(R.id.startgame);
        history = (Button) findViewById(R.id.history);
        vocabulary = (Button) findViewById(R.id.Vocabulary);

        userAccount = (ImageView) findViewById(R.id.userAccount);
        help = (ImageView) findViewById(R.id.help);

        namePref = getSharedPreferences("namePref", Context.MODE_PRIVATE);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("Score", Context.MODE_PRIVATE);
        String score = sp.getString("finScore", "");
        scoreBoard.setText(score);

        kuninYungPangalan();

        vocabulary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent5 = new Intent(getApplicationContext(), vocabulary.class);
                newIntent5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(newIntent5);
            }
        });

        userAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent1 = new Intent(getApplicationContext(), userAccount.class);
                newIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(newIntent1);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent2 = new Intent(getApplicationContext(), listView.class);
                newIntent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(newIntent2);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent3 = new Intent(home.this, help.class);
                newIntent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent3);
            }
        });

        startgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String finScore = userNameGame.getText().toString();
                Intent senderIntent = new Intent(getApplicationContext(), beforeStart.class);
                SharedPreferences.Editor editor = namePref.edit();
                editor.putString("nameOfUser", finScore);
                editor.commit();
                startActivity(senderIntent);
            }
        });

    }

    private void kuninYungPangalan(){
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        userID = mUser.getUid();
        Query query = reference.orderByChild("email").equalTo(mUser.getEmail());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    // Retrieving Data from firebase
                    String name = "" + dataSnapshot1.child("name").getValue();
                    // setting data to our text view
                    userNameGame.setText(name);
                }

                /*if(user != null){
                    String name = "" + snapshot1.child("name").getValue();
                    String emaill = "" + snapshot1.child("email").getValue();
                    userNameGame.setText("Name: " + name);
                    emailIngame.setText("Email: " + email);
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(home.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(home.this);
        builder.setTitle("Are you Sure?");
        builder.setMessage("want to Exit ANAGRAMMER?");

        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                System.exit(0);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(home.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
}