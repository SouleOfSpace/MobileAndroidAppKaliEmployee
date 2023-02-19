package com.example.kalidigitalemployee.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tool {
    @SerializedName("profile")
    @Expose
    private String profileId;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("number_inventar")
    @Expose
    private String number_inventar;

    public String getProfileId() {
        return profileId;
    }

    public String getTitle() {
        return title;
    }

    public String getNumber_inventar() {
        return number_inventar;
    }

    public String getDate_get() {
        return date_get;
    }

    public String getDate_out() {
        return date_out;
    }

    @SerializedName("date_get")
    @Expose
    private String date_get;

    public Tool(String profileId, String title, String number_inventar, String date_get, String date_out) {
        this.profileId = profileId;
        this.title = title;
        this.number_inventar = number_inventar;
        this.date_get = date_get;
        this.date_out = date_out;
    }

    @SerializedName("date_out")
    @Expose
    private String date_out;
}
