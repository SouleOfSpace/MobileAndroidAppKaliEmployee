package com.example.kalidigitalemployee.ui.profile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.example.kalidigitalemployee.retrofit.Contract;
import com.example.kalidigitalemployee.retrofit.Profile;
import com.example.kalidigitalemployee.retrofit.RetrofitClient;
import com.example.kalidigitalemployee.retrofit.User;
import com.example.kalidigitalemployee.ui.profile.subInfo.AboutMeFragment;
import com.example.kalidigitalemployee.ui.profile.subInfo.BasicInfoFragment;
import com.example.kalidigitalemployee.ui.profile.subInfo.WorkInfoFragment;

import java.util.List;


public class ProfileFragment extends Fragment{

    private TextView mBasicInfo, mAboutMe, mWorkInfo, mProfileName,
    mBirthday, mContractFinish;
    private Retrofit retrofit = RetrofitClient.getRetrofitInstance();
    private ApiIterface apiIterface = retrofit.create(ApiIterface.class);

    public ProfileFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        mBasicInfo = (TextView) v.findViewById(R.id.btn_main_info);
        mAboutMe = (TextView) v.findViewById(R.id.btn_about_me);
        mWorkInfo = (TextView) v.findViewById(R.id.btn_working_info);
        mProfileName = (TextView) v.findViewById(R.id.profile_name);
        mBirthday = (TextView) v.findViewById(R.id.birthday_title);
        mContractFinish = (TextView) v.findViewById(R.id.contract_title);

        mBasicInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColoroBtns(view);

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                BasicInfoFragment basicInfoFragment = new BasicInfoFragment();
                fragmentTransaction.replace(R.id.fragment_profile_container_sub_info, basicInfoFragment);
                fragmentTransaction.commit();
            }
        });
        mBasicInfo.getLinksClickable();

        mAboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColoroBtns(view);

                AboutMeFragment aboutMeFragment = new AboutMeFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_profile_container_sub_info, aboutMeFragment);
                fragmentTransaction.commit();
            }
        });

        mWorkInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColoroBtns(view);

                WorkInfoFragment workInfoFragment = new WorkInfoFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_profile_container_sub_info, workInfoFragment);
                fragmentTransaction.commit();
            }
        });

        getCurrnetProfile("Bearer " + readAccessTokenFromStorage());
        getCurrentContract("Bearer " + readAccessTokenFromStorage());

        return v;
    }

    private void changeColoroBtns(View v){
        if (v == mBasicInfo){
            mBasicInfo.setBackground(getResources().getDrawable(R.drawable.btn_of_profiles_active));
            mAboutMe.setBackground(getResources().getDrawable(R.drawable.btn_of_profiles));
            mWorkInfo.setBackground(getResources().getDrawable(R.drawable.btn_of_profiles));
        }
        if (v == mAboutMe){
            mBasicInfo.setBackground(getResources().getDrawable(R.drawable.btn_of_profiles));
            mAboutMe.setBackground(getResources().getDrawable(R.drawable.btn_of_profiles_active));
            mWorkInfo.setBackground(getResources().getDrawable(R.drawable.btn_of_profiles));
        }
        if (v == mWorkInfo){
            mBasicInfo.setBackground(getResources().getDrawable(R.drawable.btn_of_profiles));
            mAboutMe.setBackground(getResources().getDrawable(R.drawable.btn_of_profiles));
            mWorkInfo.setBackground(getResources().getDrawable(R.drawable.btn_of_profiles_active));
        }
    }

    private void getCurrnetProfile(String accessToken) {
        Call<Profile> call = apiIterface.getCurrentProfile(accessToken, "application/json");
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (!checkResponse(response.isSuccessful(), response.code())) return;

                Profile profile = response.body();
                setFullName(profile.getLastname(), profile.getFirstname(), profile.getSurname());
                setBirthday(profile.getBirthday());
                return;
            }
            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                return;
            }
        });
    }

    private void getCurrentContract(String acceessToken) {
        Call<Contract> call = apiIterface.getCurrentContract(acceessToken, "application/json");
        call.enqueue(new Callback<Contract>() {
            @Override
            public void onResponse(Call<Contract> call, Response<Contract> response) {
                if (!checkResponse(response.isSuccessful(), response.code())) return;

                Contract contract = response.body();
                setContractFinish(contract.getFinish_time());
                return;
            }

            @Override
            public void onFailure(Call<Contract> call, Throwable t) {
                return;
            }
        });
    }

    private String readAccessTokenFromStorage() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("tokenStorage", Context.MODE_PRIVATE);
        String tokenFromsStorage = sharedPreferences.getString("AccessToken", "");
        return tokenFromsStorage;
    }

    private Boolean checkResponse(Boolean flag, Integer code){
        if(! flag){
            Log.e("TAG RESPONSE1", "respunse unsuccess: " + code);
            return false;
        }
        return true;
    }

    private void setFullName(String lastname, String firstname, String surname){
        mProfileName.setText(lastname + " " + firstname + " " + surname);
    }

    private void setBirthday(String birthday){
        mBirthday.setText(birthday);
    }

    private void setContractFinish(String contract){
        mContractFinish.setText(contract);
    }
}