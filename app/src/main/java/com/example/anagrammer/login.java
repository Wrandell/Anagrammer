package com.example.anagrammer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView signup, attempts,forget;
    EditText user, pass;
    FirebaseUser mUser;
    boolean passVisible;
    Button login;
    int trials = 3;
    int timer = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup = (TextView) findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();
        user = (EditText) findViewById(R.id.usern);
        pass = (EditText) findViewById(R.id.passw);
        mUser = mAuth.getCurrentUser();
        login = (Button) findViewById(R.id.loginBtn);
        attempts = (TextView) findViewById(R.id.attempts);

        forget = (TextView) findViewById(R.id.forgetpass);

        attempts.setText("Attempts: " + trials);

        if(mUser != null){
            startActivity(new Intent(login.this, welcome.class));
        }

        pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                final int Right = 2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=pass.getRight()-pass.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = pass.getSelectionEnd();
                        if (passVisible){
                            pass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);
                            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passVisible = false;
                        }else{
                            pass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24,0);
                            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passVisible = true;
                        }
                        pass.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this,Register.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,forgetpass.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = user.getText().toString().trim();
                String password = pass.getText().toString().trim();

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

                if(password.isEmpty()){
                    pass.setError("Provide a Valid Email");
                    pass.requestFocus();
                    return;
                }

                if(password.length()< 6){
                    pass.setError("Minimum of Six Characters");
                    pass.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            nextToGame();
                            Toast.makeText(login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(login.this, "Login Failed ", Toast.LENGTH_SHORT).show();
                            trials--;
                            attempts.setText("Attempts: " + trials);
                            attempts.setVisibility(View.VISIBLE);
                            if (trials == 0){
                                login.setEnabled(false);
                                signup.setEnabled(false);
                                forget.setEnabled(false);
                                AlertDialog.Builder builder = new AlertDialog.Builder(login.getContext());
                                builder.setTitle("Account not Found");
                                builder.setMessage("Please wait for 60 seconds to Re-login or change Password");

                                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        new CountDownTimer(timer, 1000) {
                                            @Override
                                            public void onTick(long l) {
                                                attempts.setTextColor(Color.RED);
                                                attempts.setText("Wait until: " + l/1000 + "s");
                                            }

                                            @Override
                                            public void onFinish() {
                                                trials = 3;
                                                attempts.setVisibility(View.INVISIBLE);
                                                login.setEnabled(true);
                                                signup.setEnabled(true);
                                                forget.setEnabled(true);
                                            }
                                        }.start();
                                    }
                                });
                                builder.show();
                            }
                        }

                    }
                });
            }
        });
    }



    private void nextToGame() {
       Intent intent = new Intent(login.this, welcome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}