package com.example.kalidigitalemployee.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.kalidigitalemployee.R;

public class HomeFragment extends Fragment {

    private TextView mTitle;

    public HomeFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mTitle = (TextView) v.findViewById(R.id.news_title);
        mTitle.setText("Sashka cool, well job");
//        mTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                mTitle.setText("Happened the click");
//                ServicesTableFragment servicesTableFragment = new ServicesTableFragment();
//                FragmentManager fragmentManager = getParentFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.nav_host_fragment_activity_menu, servicesTableFragment);
//                fragmentTransaction.commit();
//            }
//        });


        return v;
    }

//    private FragmentHomeBinding binding;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
//
//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        TextView mTitle = binding.findViewById(R.id.news_title);
////        mTitle.setText("HEllo ALX");
////        TextView v = getView().findViewById(R.id.news_title);
////        v.setText("HELLOO ALXXX");
//
////        final TextView textView = binding.textHome;
////        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        return root;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
}