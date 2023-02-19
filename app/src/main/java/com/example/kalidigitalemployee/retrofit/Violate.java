package com.example.kalidigitalemployee.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Violate {
    @SerializedName("profile")
    @Expose
    private String profileId;

    public String getProfileId() {
        return profileId;
    }

    public String getTitle() {
        return title;
    }

    public String getCreated() {
        return created;
    }

    public Violate(String profileId, String title, String created) {
        this.profileId = profileId;
        this.title = title;
        this.created = created;
    }

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("created")
    @Expose
    private String created;
}
