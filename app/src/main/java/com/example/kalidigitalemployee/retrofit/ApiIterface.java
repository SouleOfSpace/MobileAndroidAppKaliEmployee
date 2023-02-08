package com.example.kalidigitalemployee.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiIterface {

    @FormUrlEncoded
    @POST("/api/users")
    Call<User> getUserInformation(
            @Field("id") String id,
            @Field("username") String username,
            @Field("email") String email,
            @Field("date_joined") String date_joined
    );
}
