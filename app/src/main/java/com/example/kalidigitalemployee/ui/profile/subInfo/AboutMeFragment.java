package com.example.kalidigitalemployee.ui.profile.subInfo;

<<<<<<< HEAD
=======
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
>>>>>>> f9c8ea5 (Initial commit)
import android.os.Bundle;

import androidx.fragment.app.Fragment;

<<<<<<< HEAD
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kalidigitalemployee.R;


public class AboutMeFragment extends Fragment {
=======
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kalidigitalemployee.R;
import com.example.kalidigitalemployee.retrofit.ApiIterface;
import com.example.kalidigitalemployee.retrofit.Passport;
import com.example.kalidigitalemployee.retrofit.Profile;
import com.example.kalidigitalemployee.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AboutMeFragment extends Fragment {
    private TextView mPassportNumber, mPassportNumberId, mPassportCreator, mFamilyStatus, mKids, mPhone;

    private Retrofit retrofit = RetrofitClient.getRetrofitInstance();
    private ApiIterface apiIterface = retrofit.create(ApiIterface.class);
>>>>>>> f9c8ea5 (Initial commit)

    public AboutMeFragment() {
        // Required empty public constructor
    }

<<<<<<< HEAD
=======
    @SuppressLint("MissingInflatedId")
>>>>>>> f9c8ea5 (Initial commit)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about_me, container, false);

<<<<<<< HEAD
        return v;
    }
=======
        mFamilyStatus = (TextView) v.findViewById(R.id.about_me_family_status);
        mKids = (TextView) v.findViewById(R.id.about_me_kids);
        mPhone = (TextView) v.findViewById(R.id.about_phone);
        mPassportNumber = (TextView) v.findViewById(R.id.about_me_passport_number);
        mPassportNumberId = (TextView) v.findViewById(R.id.about_me_passport_id);
        mPassportCreator = (TextView) v.findViewById(R.id.about_me_passport_host);

        getCurrnetProfile("Bearer " + readAccessTokenFromStorage());
        getCurrentPassport("Bearer " + readAccessTokenFromStorage());
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
                setFamilyStatus(profile.getFamily_status());
                setKids(profile.getKids());
                setPhone(profile.getPhone());

                return;
            }
            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                return;
            }
        });
    }

    private void getCurrentPassport(String accessToken) {
        Call<Passport> call = apiIterface.getCurrentPassport(accessToken, "application/json");
        call.enqueue(new Callback<Passport>() {
            @Override
            public void onResponse(Call<Passport> call, Response<Passport> response) {
                if (!checkResponse(response.isSuccessful(), response.code())) return;

                Passport passport = response.body();
                setPassportNumber(passport.getNumber());
                setPassportNumberId(passport.getNumber_id());
                setPassportCreator(passport.getCreator());

                return;
            }

            @Override
            public void onFailure(Call<Passport> call, Throwable t) {
                return;
            }
        });
    }

    private void setFamilyStatus(String flag){
        mFamilyStatus.setText(flag);
    }

    private void setKids(String flag){
        mKids.setText(flag);
    }

    private void setPhone(String flag) {
        mPhone.setText(flag);
    }

    private void setPassportNumber(String flag) {
        mPassportNumber.setText(flag);
    }

    private void setPassportNumberId(String flag) {
        mPassportNumberId.setText(flag);
    }

    private void setPassportCreator(String flag) {
        mPassportCreator.setText(flag);
    }

    private Boolean checkResponse(Boolean flag, Integer code){
        if(! flag){
            Log.e("TAG RESPONSE1", "respunse unsuccess: " + code);
            return false;
        }
        return true;
    }
>>>>>>> f9c8ea5 (Initial commit)
}