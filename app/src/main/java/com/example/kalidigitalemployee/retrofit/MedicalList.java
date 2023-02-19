package com.example.kalidigitalemployee.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicalList {
    @SerializedName("profile")
    @Expose
    private String profileId;

    @SerializedName("date_get")
    @Expose
    private String date_get;

    @SerializedName("date_out")
    @Expose
    private String date_out;

    public String getProfileId() {
        return profileId;
    }

    public String getDate_get() {
        return date_get;
    }

    public String getDate_out() {
        return date_out;
    }

    public MedicalList(String profileId, String date_get, String date_out) {
        this.profileId = profileId;
        this.date_get = date_get;
        this.date_out = date_out;
    }
}
