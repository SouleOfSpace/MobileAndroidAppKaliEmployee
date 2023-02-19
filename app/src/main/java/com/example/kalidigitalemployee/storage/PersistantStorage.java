package com.example.kalidigitalemployee.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class PersistantStorage {
    private SharedPreferences sharedPreferences;
    private Context context;

//    public PersistantStorage(Context context) {
//        this.context = context;
//        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.login_preference), Context.MODE_PRIVATE);
//
//    }
//
//    public void writeToken(String token)
//    {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("jwt",token);
//    }
//    public String readToken()
//    {
//        String token=sharedPreferences.getString("jwt","");
//        return token;
//    }
}
