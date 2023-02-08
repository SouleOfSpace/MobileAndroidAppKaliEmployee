package com.example.kalidigitalemployee.ui.services.components;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.kalidigitalemployee.R;
import com.example.kalidigitalemployee.ui.services.ServicesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class ServicesMedicalFragment extends Fragment {

    private BottomNavigationView bnv;
    ImageButton mNextBtn;

    public ServicesMedicalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_services_medical, container, false);
        mNextBtn = (ImageButton) v.findViewById(R.id.btn_next_fragment_medical);

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

        return v;
    }
}