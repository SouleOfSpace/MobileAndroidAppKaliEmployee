package com.example.kalidigitalemployee.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {
    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("firstname")
    @Expose
    private String firstname;

    @SerializedName("lastname")
    @Expose
    private String lastname;

    @SerializedName("surname")
    @Expose
    private String surname;

    @SerializedName("birthday")
    @Expose
    private String birthday;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("tabel")
    @Expose
    private String tabel;

    @SerializedName("work_post")
    @Expose
    private String work_post;

    @SerializedName("work_place")
    @Expose
    private String work_place;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("family_status")
    @Expose
    private String family_status;

    @SerializedName("kids")
    @Expose
    private String kids;

    public String getUserId() {
        return userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPhone() {
        return phone;
    }

    public String getTabel() {
        return tabel;
    }

    public String getWork_post() {
        return work_post;
    }

    public String getWork_place() {return work_place;}

    public String getAddress() {
        return address;
    }

    public String getFamily_status() {
        return family_status;
    }

    public String getKids() {
        return kids;
    }

    public Profile(String userId, String firstname, String lastname, String surname, String birthday, String phone, String tabel, String work_post, String work_place, String address, String family_status, String kids) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.surname = surname;
        this.birthday = birthday;
        this.phone = phone;
        this.tabel = tabel;
        this.work_post = work_post;
        this.work_place = work_place;
        this.address = address;
        this.family_status = family_status;
        this.kids = kids;
    }
}
