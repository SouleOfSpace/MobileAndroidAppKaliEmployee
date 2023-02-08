package com.example.kalidigitalemployee;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.inputmethodservice.ExtractEditText;
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
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.kalidigitalemployee.retrofit.ApiIterface;
import com.example.kalidigitalemployee.retrofit.RetrofitClient;
import com.example.kalidigitalemployee.retrofit.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Register extends AppCompatActivity implements View.OnClickListener {


    EditText mUsername, mEmail, mPassword, mNumber_of_passport;
    Button mRegisterBtn;
    TextView mloginBtn;
    ProgressBar mProgressBar;

    String Username, Email, Password, Number_of_passport;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_register);

        mUsername = (EditText)findViewById(R.id.username);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mNumber_of_passport = (EditText) findViewById(R.id.number_of_passport);
        mRegisterBtn = (Button)findViewById(R.id.registerBtn);
        mloginBtn = (TextView)findViewById(R.id.createText);

        mRegisterBtn.setOnClickListener(this);
        mloginBtn.setOnClickListener(this);
        mProgressBar = new ProgressBar(this);

    }

    @Override
    public void onClick(View v){
        if(v==mRegisterBtn)UserRegister();
        else if (v==mloginBtn) startActivity(new Intent(Register.this, Login.class));
    }

    private void UserRegister(){
        Username = mUsername.getText().toString().trim();
        Email = mEmail.getText().toString().trim();
        Password = mPassword.getText().toString().trim();
        Number_of_passport = mNumber_of_passport.getText().toString().trim();

        if (!checkIsNotEmptyUsernameField(Username)) return;
        if (!checkIsNotEmptyEmailField(Email)) return;
        if (!checkIsNotEmptyPasswordField(Password)) return;
        if (!checkIsNotEmptyPassportField(Number_of_passport)) return;
        if (!checkIsValidLengthPassportField(Number_of_passport)) return;

        mProgressBar.setVisibility(View.VISIBLE);

        sendUserData();
    }

    private void sendUserData() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiIterface apiIterface = retrofit.create(ApiIterface.class);
        Call<User> call = apiIterface.registerUser(Email, Password, Username, Number_of_passport);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!checkServerConnect(response.code())) return;

                mProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Register.this, "Register is successed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                return;
            }
        });
    }

    private Boolean checkServerConnect(Integer code){
        if(code == 404){
            Toast.makeText(Register.this, "Server error", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Boolean checkIsNotEmptyEmailField(String email){
        if (TextUtils.isEmpty(email)){
            Toast.makeText(Register.this, "Enter email", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Boolean checkIsNotEmptyPasswordField(String password){
        if (TextUtils.isEmpty(password)){
            Toast.makeText(Register.this, "Enter password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Boolean checkIsNotEmptyUsernameField(String username){
        if (TextUtils.isEmpty(username)){
            Toast.makeText(Register.this, "Enter username", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Boolean checkIsNotEmptyPassportField(String passport){
        if (TextUtils.isEmpty(passport)){
            Toast.makeText(Register.this, "Enter passport", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Boolean checkIsValidLengthPassportField(String passport){
        if (passport.length() != 14){
            Toast.makeText(Register.this, "Enter needed length of passport (14 symbols)", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}