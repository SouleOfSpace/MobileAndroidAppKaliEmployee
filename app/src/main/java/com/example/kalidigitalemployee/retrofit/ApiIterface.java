package com.example.kalidigitalemployee.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiIterface {

    @FormUrlEncoded
    @POST("/api/token/")
    Call<Token> getToken(
            @Field("email") String email,
            @Header("password") String password,
            @Header("username") String username
    );

    @FormUrlEncoded
    @POST("/api/register/")
    Call<User> registerUser(
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password,
            @Field("number_of_passport") String number_of_passport
    );

    @GET("/api/user")
    Call<User> getCurrentUser(
            @Header("Authorization") String Authorization,
            @Header("Content-Type") String ContentType
    );


    @GET("/api/profile")
    Call<Profile> getCurrentProfile(
            @Header("Authorization") String Authorization,
            @Header("Content-Type") String ContentType
    );

    @FormUrlEncoded
    @POST("/api/profiles")
    Call<List<Profile>> getListProfiles(
            @Header("Authorization") String Authorization,
            @Header("Content-Type") String ContentType,
            @Field("flag") String flag
    );


    @GET("/api/contract")
    Call<Contract> getCurrentContract(
            @Header("Authorization") String Authorization,
            @Header("Content-Type") String ContentType
    );


    @GET("/api/passport")
    Call<Passport> getCurrentPassport(
            @Header("Authorization") String Authorization,
            @Header("Content-Type") String ContentType
    );


    @GET("/api/clothes")
    Call<List<Clothes>> getListClothes(
            @Header("Authorization") String Authorization,
            @Header("Content-Type") String ContentType
    );

    @GET("/api/medicals")
    Call<List<MedicalList>> getListMedicals(
            @Header("Authorization") String Authorization,
            @Header("Content-Type") String ContentType
    );

    @GET("/api/tools")
    Call<List<Tool>> getListTools(
            @Header("Authorization") String Authorization,
            @Header("Content-Type") String ContentType
    );

    @GET("/api/posts")
    Call<List<Post>> getListPosts(
            @Header("Authorization") String Authorization,
            @Header("Content-Type") String ContentType
    );

    @GET("/api/violates")
    Call<List<Violate>> getListViolates(
            @Header("Authorization") String Authorization,
            @Header("Content-Type") String ContentType
    );

}
