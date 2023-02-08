package com.example.kalidigitalemployee;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText Email, Password;
    Button mLoginBtn;
    TextView mCreateText;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser mUser;
    String email, password;
    public static final String userEmail="";

    public static final String TAG="LOGIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginBtn = (Button) findViewById(R.id.loginBtn);

        mCreateText = (TextView) findViewById(R.id.createText);

        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mUser!=null){
                    Intent intent = new Intent(Login.this, MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    Log.d(TAG, "AuthStateChanged: Logout");
                }
            }
        };

        //Adding click listener to log in button
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Calling EditText is empty or no method
                userSign();
            }
        });

        //Adding click listener to register button
        mCreateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        //removeAuthSateListner is used  in onStart function just for checking purposes,it helps in logging you out
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop(){
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    public void onBackPressed() {
        Login.super.finish();
    }

    private void userSign(){
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(Login.this, "Enter the correct email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)){
            Toast.makeText(Login.this, "Enter the correct pssword", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Login.this, "Login not successfull", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    checkIfEmailVerified();
                }
            }
        });
    }

    //This function helps in verifying whether the email is verified or not
    private void checkIfEmailVerified(){
        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        boolean emailVerified = users.isEmailVerified();
        if (!emailVerified){
            Toast.makeText(this, "Verify the Email Id", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            finish();
        }
        else {
            Log.d("UNLOG", "FALSE");
            Email.getText().clear();
            Password.getText().clear();
            Intent intent = new Intent(Login.this, MenuActivity.class);

            //Sending Email to MenuActivity using intent
            intent.putExtra(userEmail, email);

            startActivity(intent);
        }
    }


//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        mEmail = findViewById(R.id.email);
//        mPassword = findViewById(R.id.password);
//        progressBar = findViewById(R.id.progressBar);
//        mLoginBtn = findViewById(R.id.loginBtn);
//        mCreateText = findViewById(R.id.createText);
//        root = findViewById(R.id.root_login);
//
//        auth = FirebaseAuth.getInstance();
//
//        mCreateText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), Register.class));
//            }
//        });
//
//        mLoginBtn.setOnClickListener(this);
//
//
//
//        mLoginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final String email = mEmail.getText().toString();
//                final String password = mPassword.getText().toString();
//
//                if(TextUtils.isEmpty(email)){
//                    mEmail.setError("Email is required");
//                    return;
//                }
//
//                if (TextUtils.isEmpty(password)){
//                    mPassword.setError("Password is required");
//                    return;
//                }
//
//                if (password.length() < 6){
//                    mPassword.setError("Password must be >= 6 character");
//                    return;
//                }
//
//
//                auth.signInWithEmailAndPassword(email, password)
//                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                            @Override
//                            public void onSuccess(AuthResult authResult) {
//                                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
//                                finish();
//                                progressBar.setVisibility(View.VISIBLE);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
////                                auth.
//                                Snackbar.make(root, "Error auth" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
//                                progressBar.setVisibility(View.VISIBLE);
//                            }
//                        });
//
//            }
//        });
//    }
}