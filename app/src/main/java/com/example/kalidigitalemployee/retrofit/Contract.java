package com.example.kalidigitalemployee.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contract {
    @SerializedName("profile")
    @Expose
    private String profileId;

    @SerializedName("start_time")
    @Expose
    private String start_time;

    @SerializedName("finish_time")
    @Expose
    private String finish_time;

    @SerializedName("work_class")
    @Expose
    private String work_class;

    public String getProfileId() {
        return profileId;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public String getWork_class() {
        return work_class;
    }

    public Contract(String profileId, String start_time, String finish_time, String work_class) {
        this.profileId = profileId;
        this.start_time = start_time;
        this.finish_time = finish_time;
        this.work_class = work_class;
    }
}
