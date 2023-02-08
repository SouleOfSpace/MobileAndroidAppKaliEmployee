package com.example.kalidigitalemployee;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.kalidigitalemployee.Modals.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;


public class Register extends AppCompatActivity implements View.OnClickListener {


    EditText name, email, password, phone;
    Button mRegisterBtn;
    TextView mloginBtn;
    ProgressBar mProgressBar;
    ConstraintLayout root;

    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference users;

    DatabaseReference mDatabase;
    String Name, Email, Password, Phone;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText)findViewById(R.id.fullname);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        phone = (EditText) findViewById(R.id.phone);

        mRegisterBtn = (Button)findViewById(R.id.registerBtn);
        mloginBtn = (TextView)findViewById(R.id.createText);

        //for authentifcation using Firebase
        mAuth = FirebaseAuth.getInstance();
        mRegisterBtn.setOnClickListener(this);
        mloginBtn.setOnClickListener(this);
        mProgressBar = new ProgressBar(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

    }

    @Override
    public void onClick(View v){
        if(v==mRegisterBtn){
            UserRegister();
        }else if (v==mloginBtn){
            startActivity(new Intent(Register.this, Login.class));
        }
    }

    private void UserRegister(){
        Name = name.getText().toString().trim();
        Email = email.getText().toString().trim();
        Password = password.getText().toString().trim();
        Phone = phone.getText().toString().trim();

        if (TextUtils.isEmpty(Name)){
            Toast.makeText(Register.this, "Enter name", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(Email)){
            Toast.makeText(Register.this, "Enter email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(Password)){
            Toast.makeText(Register.this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(Phone)){
            Toast.makeText(Register.this, "Enter phone", Toast.LENGTH_SHORT).show();
            return;
        }

        mProgressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            sendEmailVerification();
                            mProgressBar.setVisibility(View.INVISIBLE);
                            OnAuth(task.getResult().getUser());
                            mAuth.signOut();
                        }else{
                            Toast.makeText(Register.this, "error on creating user", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
//Email verification code using FirebaseUser object and using is successful() function
    private void sendEmailVerification(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Register.this, "Check your email for verification", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                }
            });
        }
    }

    private void OnAuth(FirebaseUser user){
        createAnewUser(user.getUid());
    }

    private void createAnewUser(String uid){
        User user = BuildNewuser();
        mDatabase.child(uid).setValue(user);
    }

    private User BuildNewuser(){
        return new User(
                getDisplayName(),
                getUserEmail(),
                getUserPhone(),
                new Date().getTime()
        );
    }

    private String getDisplayName(){
        return  Name;
    }

    private String getUserEmail(){
        return Email;
    }

    private String getUserPhone(){
        return Phone;
    }
}