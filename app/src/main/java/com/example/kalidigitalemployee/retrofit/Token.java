package com.example.kalidigitalemployee.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("access")
    @Expose
    String jwtAccess;

    @SerializedName("refresh")
    @Expose
    String jwtRefresh;


    public String getJwtAccess() {
        return jwtAccess;
    }

    public void setJwtAccess(String jwtAccess) {
        this.jwtAccess = jwtAccess;
    }

    public String getJwtRefresh() {
        return jwtRefresh;
    }

    public void setJwtRefresh(String jwtRefresh) {
        this.jwtRefresh = jwtRefresh;
    }

    public Token(String jwtAccess, String jwtRefresh) {

        this.jwtAccess = jwtAccess;
        this.jwtRefresh = jwtRefresh;
    }
}
