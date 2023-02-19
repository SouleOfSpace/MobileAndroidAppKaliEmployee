package com.example.kalidigitalemployee.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Passport {
    @SerializedName("profile")
    @Expose
    private String profileId;

    @SerializedName("number")
    @Expose
    private String number;

    @SerializedName("number_id")
    @Expose
    private String number_id;

    public String getProfileId() {
        return profileId;
    }

    public String getNumber() {
        return number;
    }

    public String getNumber_id() {
        return number_id;
    }

    public String getCreator() {
        return creator;
    }

    public Passport(String profileId, String number, String number_id, String creator) {
        this.profileId = profileId;
        this.number = number;
        this.number_id = number_id;
        this.creator = creator;
    }

    @SerializedName("creator")
    @Expose
    private String creator;


}
