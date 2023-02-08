package com.example.kalidigitalemployee.ui.profile.subInfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kalidigitalemployee.R;
import com.example.kalidigitalemployee.retrofit.ApiIterface;
import com.example.kalidigitalemployee.retrofit.RetrofitClient;
import com.example.kalidigitalemployee.retrofit.User;
import com.example.kalidigitalemployee.retrofit.Violate;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class WorkInfoFragment extends Fragment {
    private TextView mContractStart, mContractFinish, mWorkClass, mViolateTitle, mViolateCreated;
    private LinearLayout mViolatesLayout;
    private Retrofit retrofit = RetrofitClient.getRetrofitInstance();
    private ApiIterface apiIterface = retrofit.create(ApiIterface.class);


    public WorkInfoFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_work_info, container, false);

        mContractStart = (TextView) v.findViewById(R.id.start_contract_work_info);
        mContractFinish = (TextView) v.findViewById(R.id.finish_contract_work_info);
        mWorkClass = (TextView) v.findViewById(R.id.razryad_work_info);
        mViolatesLayout = (LinearLayout) v.findViewById(R.id.layout_violates_work_info);

        getListViolates("Bearer " + readAccessTokenFromStorage());

        return v;
    }

    public void printAllViolates(String title, String data, int flag){
        CardView cardView = createCardView();
        LinearLayout lnv = createLinerLayout("vertical");
        LinearLayout lnv1 = createLinerLayout("vertical");
        lnv1.setVisibility(View.GONE);
        LinearLayout lnh = createLinerLayout("horizontal");


        lnv1.addView(createTextView(title,
                0,10,0,0,
                16,
                "normal"));
        lnv1.addView(createTextView(data,
                0,10,0,0,
                16,
                "normal"));
        lnh.addView(createTextView("Нарушение (тип, дата) №" + (flag),
                0,0,50,0,
                16,
                "bold"));
        lnv.addView(lnh);
        lnv.addView(lnv1);
        cardView.addView(lnv);
        mViolatesLayout.addView(cardView);
        lnh.addView(createImageButton(cardView, lnv1));
    }

    public CardView createCardView() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,0,20);

        CardView cardView = new CardView(getContext());
        cardView.setLayoutParams(layoutParams);

        cardView.setRadius(16);


        return cardView;
    }

    public LinearLayout createLinerLayout(String flag) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,0,0);

        LinearLayout linearLayout = new LinearLayout(getContext());

        if (flag=="vertical")
            linearLayout.setOrientation(LinearLayout.VERTICAL);
        else
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);


        return linearLayout;
    }

    public TextView createTextView(String text, int ml, int mt, int mr, int mb, float size, String style) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(ml,mt,mr,mb);

        TextView textView = new TextView(getContext());
        textView.setLayoutParams(layoutParams);
        textView.setText(text);
        textView.setTextSize(size);
        if (style == "bold")
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        else if (style == "normal")
            textView.setTypeface(textView.getTypeface(), Typeface.NORMAL);

        return textView;
    }

    public ImageButton createImageButton(CardView card, LinearLayout ln) {

        ImageButton imageButton = new ImageButton(getContext());
        imageButton.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);

        imageButton.setOnClickListener(view -> {
            if (ln.getVisibility() == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(card, new AutoTransition());
                ln.setVisibility(View.GONE);
                imageButton.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
            }
            else {
                TransitionManager.beginDelayedTransition(card, new AutoTransition());
                ln.setVisibility(View.VISIBLE);
                imageButton.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
            }
        });

        return imageButton;
    }


    private void getListViolates(String accessToken){
        Call<List<Violate>> call = apiIterface.getListViolates(accessToken, "application/json");

        call.enqueue(new Callback<List<Violate>>() {
            @Override
            public void onResponse(Call<List<Violate>> call, Response<List<Violate>> response) {
                if (!checkResponse(response.isSuccessful(), response.code())) return;

                if (response.code() == 200){
                    List<Violate> violates = response.body();

                    int flag = 1;
                    for(Violate violate: violates){
                        printAllViolates(violate.getTitle(), violate.getCreated(), flag);
                        flag +=1;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Violate>> call, Throwable t) {
                Log.e("TAG RESPONSE", "onFailure: " + t.getMessage());
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