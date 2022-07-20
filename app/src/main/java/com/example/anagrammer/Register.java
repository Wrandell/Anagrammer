package com.example.anagrammer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ImageView backBtn;
    EditText gameName,user, pass, repass;
    FirebaseUser mUser;
    Button registerBtn;
    boolean passVisible;

    DatabaseReference seanDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        backBtn = (ImageView) findViewById(R.id.backBtn);
        gameName = (EditText) findViewById(R.id.ingamename);
        user = (EditText) findViewById(R.id.user);
        repass = (EditText) findViewById(R.id.repassword);
        pass = (EditText) findViewById(R.id.password);
        mUser = mAuth.getCurrentUser();
        registerBtn = findViewById(R.id.registration);


        seanDb = FirebaseDatabase.getInstance().getReference().child("User");

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
                Intent intent = new Intent(Register.this, login.class);
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
        String name1 = gameName.getText().toString();
        String email1 = user.getText().toString();
        String password1 = pass.getText().toString();
        String reTypePass1 = repass.getText().toString();

        if(name1.isEmpty()){
            user.setError("Name is Required");
            user.requestFocus();
            return;
        }

        if(email1.isEmpty()){
            user.setError("Email is Required");
            user.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
            user.setError("Provide a Valid Email");
            user.requestFocus();
            return;
        }

        if(password1.isEmpty()){
            pass.setError("Provide a Valid Email");
            pass.requestFocus();
            return;
        }

        if(password1.length()< 6){
            pass.setError("Minimum of Six Characters");
            pass.requestFocus();
            return;
        }

        if(!password1.equals(reTypePass1)){
            repass.setError("Password not matched");
            repass.requestFocus();
            return;
        }



        mAuth.createUserWithEmailAndPassword(email1,password1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Users user = new Users(name1,email1,password1,reTypePass1);

                            FirebaseDatabase.getInstance().getReference("users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        nextActivity();
                                    }
                                }
                            });


                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(registerBtn.getContext());
                            builder.setTitle("Email has Taken");
                            builder.setMessage("Please use another email Address");

                            builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();

                                }
                            });
                            builder.show();
                        }
                    }
                });

    }

    private void nextActivity() {
        Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this, welcome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Register.this, login.class));
    }

}