package com.example.kalidigitalemployee.ui.profile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.kalidigitalemployee.R;
import com.example.kalidigitalemployee.databinding.FragmentProfileBinding;
import com.example.kalidigitalemployee.retrofit.ApiIterface;
import com.example.kalidigitalemployee.retrofit.RetrofitClient;
import com.example.kalidigitalemployee.retrofit.User;
import com.example.kalidigitalemployee.ui.profile.subInfo.AboutMeFragment;
import com.example.kalidigitalemployee.ui.profile.subInfo.BasicInfoFragment;
import com.example.kalidigitalemployee.ui.profile.subInfo.WorkInfoFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.StringWriter;

public class ProfileFragment extends Fragment{

    TextView mBasicInfo, mAboutMe, mWorkInfo;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase db;

    public ProfileFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        mBasicInfo = (TextView) v.findViewById(R.id.btn_main_info);
        mAboutMe = (TextView) v.findViewById(R.id.btn_about_me);
        mWorkInfo = (TextView) v.findViewById(R.id.btn_working_info);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (mUser != null) {
            Log.d("USER CUR", mUser.getEmail());
//            Log.d("USER CUR", mUser.getPhoneNumber());
            sendPosReques();
        }


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

    private void sendPosReques(){
        ApiIterface apiIterface = RetrofitClient.getRetrofitInstance().create(ApiIterface.class);
        Call<com.example.kalidigitalemployee.retrofit.User> call = apiIterface.getUserInformation("Id", "username", "email", "date_joined");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<com.example.kalidigitalemployee.retrofit.User> call, Response<User> response) {
                Log.e("TAG RESPONSE","OnResponse" + response.code());
                Log.e("TAG RESPONSE", "Id: " + response.body().getId());
                Log.e("TAG RESPONSE", "Username: " + response.body().getUsername());
                Log.e("TAG RESPONSE", "Email: " + response.body().getEmail());
                Log.e("TAG RESPONSE", "Date joined" + response.body().getDate_joined());
            }

            @Override
            public void onFailure(Call<com.example.kalidigitalemployee.retrofit.User> call, Throwable t) {
                Log.e("TAG RESPONSE", "onFailure: " + t.getMessage());
            }
        });
    }

//    @Override
//    public void onClick(View v){
//        if (v == mBasicInfo){
//            BasicInfoFragment basicInfoFragment = new BasicInfoFragment();
//            fragmentTransaction.replace(R.id.fragment_profile_container_sub_info, basicInfoFragment);
//            fragmentTransaction.commit();
//            Log.d("BUTTON BASIC INFO", "TRUE TRUE");
//        } else if (v == mAboutMe){
//            AboutMeFragment aboutMeFragment = new AboutMeFragment();
//            fragmentTransaction.replace(R.id.fragment_profile_container_sub_info, aboutMeFragment);
//            fragmentTransaction.commit();
//        }
//    }
}


class UserProfile {
    private String username;
    private String phone;
    private String firstName;
    private String lastName;

}
interface IUserProfile {

    UserProfile saveUserProfile(UserProfile profile);
    UserProfile getUserProfile(String username);
}

//class UserProfileImpl implements IUserProfile {
//
//
//
//    @Override
//    public UserProfile saveUserProfile(UserProfile profile) {
//        HttpClient client = new DefaultHttpClient();
//        HttpGet request = new HttpGet("http://restUrl");
//        try {
//            HttpResponse response = client.execute(request);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    @Override
//    public UserProfile getUserProfile(String username) {
//        HttpClient client = new DefaultHttpClient();
//        HttpGet request = new HttpGet("http://localhost:8080/users/profile");
//        try {
//            Gson gson = new Gson();
//            HttpResponse response = client.execute(request);
//            String profileJson = IOUtils.toString(response.getEntity().getContent(), "utf-8");
//            return gson.fromJson(profileJson, UserProfile.class);
//        } catch (IOException e) {
//            throw new RuntimeException("Profile wasn't found");
//        }
//
//    }
//}