package com.example.kalidigitalemployee.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("profile")
    @Expose
    private String profileId;

    @SerializedName("title")
    @Expose
    private String title;

    public String getProfileId() {
        return profileId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getPublished() {
        return published;
    }

    public Post(String profileId, String title, String description, String status, String published) {
        this.profileId = profileId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.published = published;
    }

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("published")
    @Expose
    private String published;
}
