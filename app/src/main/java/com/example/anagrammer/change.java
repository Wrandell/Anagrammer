package com.example.anagrammer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class change extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ImageView backBtn;
    EditText pass, repass;
    FirebaseUser mUser;
    Button registerBtn;
    boolean passVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        mAuth = FirebaseAuth.getInstance();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        repass = (EditText) findViewById(R.id.repassword);
        pass = (EditText) findViewById(R.id.password);
        mUser = mAuth.getCurrentUser();
        registerBtn = findViewById(R.id.registration);

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


        repass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                final int Right = 2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=repass.getRight()-repass.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = repass.getSelectionEnd();
                        if (passVisible){
                            repass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);
                            repass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passVisible = false;
                        }else{
                            repass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24,0);
                            repass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passVisible = true;
                        }
                        repass.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), userAccount.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrationForm();
            }
        });

    }

    private void registrationForm() {
        String password = pass.getText().toString();
        String reTypePass = repass.getText().toString();


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

        if(!password.equals(reTypePass)){
            repass.setError("Password not matched");
            repass.requestFocus();
            return;
        }

        mAuth.getCurrentUser().updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Successfully change pass", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(change.this, userAccount.class));
    }
}