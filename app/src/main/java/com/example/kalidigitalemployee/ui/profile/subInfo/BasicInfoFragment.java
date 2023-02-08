package com.example.kalidigitalemployee.ui.profile.subInfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kalidigitalemployee.R;
import com.example.kalidigitalemployee.retrofit.ApiIterface;
import com.example.kalidigitalemployee.retrofit.Profile;
import com.example.kalidigitalemployee.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class BasicInfoFragment extends Fragment {
    private TextView mTabel, mWorkPost, mWorkPlace, mAddress, mContractFinish;

    private Retrofit retrofit = RetrofitClient.getRetrofitInstance();
    private ApiIterface apiIterface = retrofit.create(ApiIterface.class);


    public BasicInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_basic_info, container, false);

        mTabel = (TextView) v.findViewById(R.id.tabel_number);
        mWorkPost = (TextView) v.findViewById(R.id.workpost);
        mWorkPlace = (TextView) v.findViewById(R.id.workplace);
        mAddress = (TextView) v.findViewById(R.id.address);

        getCurrnetProfile("Bearer " + readAccessTokenFromStorage());

        return v;
    }

    private String readAccessTokenFromStorage() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("tokenStorage", Context.MODE_PRIVATE);
        String tokenFromsStorage = sharedPreferences.getString("AccessToken", "");
        return tokenFromsStorage;
    }

    private void getCurrnetProfile(String accessToken) {
        Call<Profile> call = apiIterface.getCurrentProfile(accessToken, "application/json");
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (!checkResponse(response.isSuccessful(), response.code())) return;

                Profile profile = response.body();
                setTabel(profile.getTabel());
                setWorkPost(profile.getWork_post());
                setWorkPlace(profile.getWork_place());
                setAddress(profile.getAddress());


                return;
            }
            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                return;
            }
        });
    }

    private void setTabel(String flag){
        mTabel.setText(flag);
    }

    private void setWorkPost(String flag){
        mWorkPost.setText(flag);
    }

    private void setWorkPlace(String flag) {
        mWorkPlace.setText(flag);
    }

    private void setAddress(String flag) {
        mAddress.setText(flag);
    }

    private Boolean checkResponse(Boolean flag, Integer code){
        if(! flag){
            Log.e("TAG RESPONSE1", "respunse unsuccess: " + code);
            return false;
        }
        return true;
    }
}