package com.example.kalidigitalemployee.ui.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.kalidigitalemployee.R;
import com.example.kalidigitalemployee.retrofit.ApiIterface;
import com.example.kalidigitalemployee.retrofit.Profile;
import com.example.kalidigitalemployee.retrofit.RetrofitClient;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SearchFragment extends Fragment {
    private Retrofit retrofit = RetrofitClient.getRetrofitInstance();
    private ApiIterface apiIterface = retrofit.create(ApiIterface.class);

    SearchView mSearchView;
    ArrayList list;
    ArrayAdapter mAdapter;
    ListView mListView;

    public SearchFragment() {

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        mListView = (ListView) v.findViewById(R.id.listview_search);
        mSearchView = (SearchView) v.findViewById(R.id.simpleSearchView);

//        list = new ArrayList<>();
//        list.add("A1");
//        list.add("B2");
//        list.add("C3");
//        list.add("D4");
//        list.add("E5");
//        list.add("F6");
//        list.add("G7");
//        list.add("H8");
//        list.add("I9");
//        list.add("J10");
//
//        mAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,list);
//        mListView.setAdapter(mAdapter);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), "MY SEARCH: " + query.toString(), Toast.LENGTH_SHORT).show();
                getCurrnetProfile("Bearer " + readAccessTokenFromStorage(), query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                return false;
            }
        });

        return v;
    }

    private void getCurrnetProfile(String accessToken, String flag) {
        Call<List<Profile>> call = apiIterface.getListProfiles(accessToken, "application/json", flag);
        call.enqueue(new Callback<List<Profile>>() {
            @Override
            public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                if (!checkResponse(response.isSuccessful(), response.code())) return;

                List<Profile> profiles = response.body();

                return;
            }
            @Override
            public void onFailure(Call<List<Profile>> call, Throwable t) {
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
}