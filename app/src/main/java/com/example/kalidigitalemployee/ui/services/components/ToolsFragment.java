package com.example.kalidigitalemployee.ui.services.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kalidigitalemployee.R;
import com.example.kalidigitalemployee.retrofit.ApiIterface;
import com.example.kalidigitalemployee.retrofit.RetrofitClient;
import com.example.kalidigitalemployee.retrofit.Tool;
import com.example.kalidigitalemployee.retrofit.Violate;
import com.example.kalidigitalemployee.ui.services.ServicesFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ToolsFragment extends Fragment {

    ImageButton mNextBtn;
    LinearLayout mToolsLayout;
    private Retrofit retrofit = RetrofitClient.getRetrofitInstance();
    private ApiIterface apiIterface = retrofit.create(ApiIterface.class);

    public ToolsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tools, container, false);
        mNextBtn = (ImageButton) v.findViewById(R.id.btn_next_fragment_tools);
        mToolsLayout = (LinearLayout) v.findViewById(R.id.layout_tools_tool_fragment);

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServicesFragment servicesFragment = new ServicesFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_menu, servicesFragment);
                fragmentTransaction.commit();
            }
        });

        getListTools("Bearer " + readAccessTokenFromStorage());

        return v;
    }

    public void printAllTools(String title,
                                 String number_inventar,
                                 String data_get,
                                 String data_out) {
        CardView cardView = createCardView();
        LinearLayout lnv = createLinerLayout("vertical");
        LinearLayout lnv1 = createLinerLayout("vertical");
        lnv1.setVisibility(View.GONE);
        LinearLayout lnh = createLinerLayout("horizontal");
        LinearLayout lnh1 = createLinerLayout("horizontal");
        LinearLayout lnh2 = createLinerLayout("horizontal");
        LinearLayout lnh3 = createLinerLayout("horizontal");


        lnh1.addView(createTextView("Инвентарный номер: ",
                0,0,0,0,
                16,
                "normal"));
        lnh1.addView(createTextView(number_inventar,
                0,0,0,0,
                16,
                "normal"));
        lnh2.addView(createTextView("Дата получения: ",
                0,0,0,0,
                16,
                "normal"));
        lnh2.addView(createTextView(data_get,
                0,0,0,0,
                16,
                "normal"));
        lnh3.addView(createTextView("Дата списания: ",
                0,0,0,0,
                16,
                "normal"));
        lnh3.addView(createTextView(data_out,
                0,0,0,0,
                16,
                "normal"));

        lnv1.addView(lnh1);
        lnv1.addView(lnh2);
        lnv1.addView(lnh3);
        lnh.addView(createTextView("Инструмент:" + (title),
                0,0,50,0,
                16,
                "bold"));

        lnv.addView(lnh);
        lnv.addView(lnv1);
        cardView.addView(lnv);
        mToolsLayout.addView(cardView);
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

    private void getListTools(String accessToken){
        Call<List<Tool>> call = apiIterface.getListTools(accessToken, "application/json");

        call.enqueue(new Callback<List<Tool>>() {
            @Override
            public void onResponse(Call<List<Tool>> call, Response<List<Tool>> response) {
                if (!checkResponse(response.isSuccessful(), response.code())) return;

                List<Tool> tools = response.body();

                for(Tool tool: tools){
                    printAllTools(
                            tool.getTitle(),
                            tool.getNumber_inventar(),
                            tool.getDate_get(),
                            tool.getDate_out());
                }
            }

            @Override
            public void onFailure(Call<List<Tool>> call, Throwable t) {
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