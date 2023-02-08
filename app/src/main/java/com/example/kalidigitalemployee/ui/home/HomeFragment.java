package com.example.kalidigitalemployee.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.kalidigitalemployee.R;
import com.example.kalidigitalemployee.retrofit.ApiIterface;
import com.example.kalidigitalemployee.retrofit.Clothes;
import com.example.kalidigitalemployee.retrofit.Post;
import com.example.kalidigitalemployee.retrofit.RetrofitClient;
import com.example.kalidigitalemployee.retrofit.Tool;
import com.example.kalidigitalemployee.ui.services.components.DialogPostFragment;
import com.google.rpc.context.AttributeContext;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.grpc.internal.SharedResourceHolder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    private TextView mTitle;
    private LinearLayout mLayoutPost;
    private Retrofit retrofit = RetrofitClient.getRetrofitInstance();
    private ApiIterface apiIterface = retrofit.create(ApiIterface.class);

    public HomeFragment(){

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mLayoutPost = (LinearLayout) v.findViewById(R.id.layout_posts_fragment);
        getListClothes("Bearer " + readAccessTokenFromStorage());

        return v;
    }

    public void printAllPosts(String data, String title, String body) {
        CardView cardView = createCardView();
        LinearLayout lnv = createLinerLayout("vertical", 20, 20,20,20);
        LinearLayout lnh1 = createLinerLayout("horizontal",0,0,0,0);
        LinearLayout lnh2 = createLinerLayout("horizontal",0,0,0,0);
        LinearLayout lnh3 = createLinerLayout("horizontal",0,0,0,0);
        CircleImageView civ = createCircleImage();
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("title", title);
                args.putString("body", body);

                DialogPostFragment dialogPostFragment = new DialogPostFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                dialogPostFragment.setArguments(args);
                dialogPostFragment.show(fragmentManager, "custom");
            }
        });


        lnh1.addView(createTextView(data,
                0,0,0,0,
                16,
                "normal"));
        lnh2.addView(createTextView(title,
                0,0,0,0,
                16,
                "bold"));
        lnh3.addView(createTextView(body.substring(0, 20) + "...",
                0,0,0,0,
                16,
                "bold"));

        lnv.addView(createCircleImage());
        lnv.addView(lnh1);
        lnv.addView(lnh2);
        lnv.addView(lnh3);
        cardView.addView(lnv);
        mLayoutPost.addView(cardView);
    }

    public CardView createCardView() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(20,20,20,20);
        layoutParams.gravity = Gravity.CENTER;

        CardView cardView = new CardView(getContext());
        cardView.setLayoutParams(layoutParams);
        cardView.setBackgroundResource(R.drawable.fragment_home_form_news);
        cardView.setElevation(15);

        return cardView;
    }

//    public GridLayout createGridLayout() {
//        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
//        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
//        layoutParams.columnSpec = GridLayout.spec()
//
//    }

    private LinearLayout createLinerLayout(String flag, int ps, int pt, int pe, int pb) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,0,0);

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setPaddingRelative(ps, pt, pe, pb);

        if (flag=="vertical")
            linearLayout.setOrientation(LinearLayout.VERTICAL);
        else
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);


        return linearLayout;
    }

    private TextView createTextView(String text, int ml, int mt, int mr, int mb, float size, String style) {
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

    private CircleImageView createCircleImage() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(260,260);
        layoutParams.setMargins(20,20,20,20);
        layoutParams.gravity = Gravity.CENTER;

        CircleImageView circleImageView = new CircleImageView(getContext());
        circleImageView.setImageResource(R.drawable.bg_menu);
        circleImageView.setBorderWidth(6);
        circleImageView.setBorderColor(Color.parseColor("#D8D4D4"));
        circleImageView.setLayoutParams(layoutParams);

        return circleImageView;
    }

    private void getListClothes(String accessToken){
        Call<List<Post>> call = apiIterface.getListPosts(accessToken, "application/json");

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!checkResponse(response.isSuccessful(), response.code())) return;

                List<Post> posts = response.body();

                for(Post post: posts){
                    printAllPosts(
                            post.getPublished(),
                            post.getTitle(),
                            post.getDescription());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
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