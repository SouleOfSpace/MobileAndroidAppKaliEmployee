package com.example.kalidigitalemployee;


import static android.preference.PreferenceManager.getDefaultSharedPreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.kalidigitalemployee.retrofit.ApiIterface;
import com.example.kalidigitalemployee.retrofit.RetrofitClient;
import com.example.kalidigitalemployee.retrofit.Token;
import com.example.kalidigitalemployee.retrofit.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Login extends AppCompatActivity {

    EditText Email, Password;
    Button mLoginBtn;
    TextView mCreateText;
    ProgressBar progressBar;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginBtn = (Button) findViewById(R.id.loginBtn);
        mCreateText = (TextView) findViewById(R.id.createText);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        checkAccessToken();

        //Adding click listener to log in button
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createToken();
                progressBar.setVisibility(View.INVISIBLE);
                checkAccessToken();
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

//    @Override
//    protected void onStart(){
//        super.onStart();
//        //removeAuthSateListner is used  in onStart function just for checking purposes,it helps in logging you out
//        mAuth.removeAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    protected void onStop(){
//        super.onStop();
//        mAuth.removeAuthStateListener(mAuthListener);
//    }

    @Override
    public void onBackPressed() {
        Login.super.finish();
    }

    private void createToken(){
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();

        if (!checkIsNotEmptyEmailField(email))return;
        else if (!checkIsNotEmptyPasswordField(password)) return;

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiIterface apiIterface = retrofit.create(ApiIterface.class);

        Call<Token> call = apiIterface.getToken(email, password, "admin");
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(!checkServerConnect(response.code())) return;

                Token token = response.body();
                progressBar.setVisibility(View.VISIBLE);

                if (!validIsToken(token)) return;

                progressBar.setVisibility(View.INVISIBLE);

                Log.d("TOKEN FROM DJANGO", token.getJwtAccess());
                saveTokenInStorage(token.getJwtAccess());
                Log.d("TOKEN FROM STORAGE", readTokenFromStorage());
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(Login.this, "Data was not sent. Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    private void saveTokenInStorage(String token) {
        SharedPreferences preferences = getSharedPreferences("tokenStorage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("AccessToken", token);
        editor.apply();
    }

    private String readTokenFromStorage() {
        SharedPreferences sharedPreferences = getSharedPreferences("tokenStorage", Context.MODE_PRIVATE);
        String tokenFromsStorage = sharedPreferences.getString("AccessToken", "");
        return tokenFromsStorage;
    }

    private void checkAccessToken(){
        String token = "Bearer " + readTokenFromStorage();
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiIterface apiIterface = retrofit.create(ApiIterface.class);
        Call<User> call = apiIterface.getCurrentUser(token, "application/json");

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 404){
                    Toast.makeText(Login.this, "Server error", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.code() == 200){
                    Intent intent = new Intent(Login.this, MenuActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                return;
            }
        });
    }

    private Boolean validIsToken(Token token) {
        if (token.getJwtAccess() == "Email is not right"){
            Toast.makeText(Login.this, "Email not successfull", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (token.getJwtAccess() == "Password is not right"){
            Toast.makeText(Login.this, "Password not successfull", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private Boolean checkServerConnect(Integer code){
        if(code == 404){
            Toast.makeText(Login.this, "Server error", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Boolean checkIsNotEmptyEmailField(String email){
        if (TextUtils.isEmpty(email)){
            Toast.makeText(Login.this, "Enter the correct email", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Boolean checkIsNotEmptyPasswordField(String password){
        if (TextUtils.isEmpty(password)){
            Toast.makeText(Login.this, "Enter the correct password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}