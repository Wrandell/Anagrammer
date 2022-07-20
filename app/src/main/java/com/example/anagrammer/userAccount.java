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

public class userAccount extends AppCompatActivity {
    private FirebaseUser mUser;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String userID;


    Button logout, changePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        final TextView userNameGame = findViewById(R.id.userNameGame);
        final TextView emailIngame = findViewById(R.id.emailInGame);
        logout = (Button) findViewById(R.id.logoutn);
        changePass = (Button)findViewById(R.id.resetPass);

        ImageView backBtn = findViewById(R.id.backBtn);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        userID = mUser.getUid();
        Query query = reference.orderByChild("email").equalTo(mUser.getEmail());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userAccount.this, home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(userAccount.this, change.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
            }
        });

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    // Retrieving Data from firebase
                    String name = "" + dataSnapshot1.child("name").getValue();
                    String email = "" + dataSnapshot1.child("email").getValue();
                    // setting data to our text view
                    userNameGame.setText(name);
                    emailIngame.setText(email);
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
                Toast.makeText(userAccount.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(logout.getContext());
                builder.setTitle("Are you Sure?");
                builder.setMessage("want to logout ANAGRAMMER?");

                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        sigOutUser();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(logout.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }

        });
    }

    private void sigOutUser() {
        Intent intent = new Intent(userAccount.this, login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(userAccount.this, home.class));
    }
}
