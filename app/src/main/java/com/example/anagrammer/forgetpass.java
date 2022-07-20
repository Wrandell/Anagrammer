package com.example.anagrammer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class forgetpass extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText user;
    ImageView back;
    FirebaseUser mUser;
    Button changepass;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);

        back = (ImageView) findViewById(R.id.backBtn);
        mAuth = FirebaseAuth.getInstance();
        user = (EditText) findViewById(R.id.usern);
        mUser = mAuth.getCurrentUser();
        changepass = (Button) findViewById(R.id.loginBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(forgetpass.this, login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user.getText().toString().trim();

                if(email.isEmpty()){
                    user.setError("Full Name is Required");
                    user.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    user.setError("Provide a Valid Email");
                    user.requestFocus();
                    return;
                }

                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(forgetpass.this, "Check your email", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(forgetpass.this, "try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

    }
}