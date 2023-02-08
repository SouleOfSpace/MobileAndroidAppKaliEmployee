package com.example.kalidigitalemployee.ui.services;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.kalidigitalemployee.R;
import com.example.kalidigitalemployee.ui.services.components.ClothesFragment;
import com.example.kalidigitalemployee.ui.services.components.SalaryFragment;
import com.example.kalidigitalemployee.ui.services.components.ServicesMedicalFragment;
import com.example.kalidigitalemployee.ui.services.components.ServicesPassSystemFragment;
import com.example.kalidigitalemployee.ui.services.components.ServicesTabelFragment;
import com.example.kalidigitalemployee.ui.services.components.ToolsFragment;

public class ServicesFragment extends Fragment {

    CardView mPassSistem, mTabel, mMedical, mSalary, mClothes, mTools;
    TextView mTitlePassSystem;

    public ServicesFragment() {

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_services, container, false);

        mPassSistem = (CardView) v.findViewById(R.id.card_pass_sistem);
        mTabel = (CardView) v.findViewById(R.id.card_tabel);
        mMedical = (CardView) v.findViewById(R.id.card_medical);
        mSalary = (CardView) v.findViewById(R.id.card_salary);
        mClothes = (CardView) v.findViewById(R.id.card_clothes);
        mTools = (CardView) v.findViewById(R.id.card_tools);
        mTitlePassSystem = (TextView) v.findViewById(R.id.title_pass_sistem);

        mPassSistem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServicesPassSystemFragment servicesPassSystemFragment = new ServicesPassSystemFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_menu, servicesPassSystemFragment);
                fragmentTransaction.commit();
            }
        });

        mTabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServicesTabelFragment servicesTabelFragment = new ServicesTabelFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_menu, servicesTabelFragment);
                fragmentTransaction.commit();
            }
        });

        mMedical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServicesMedicalFragment servicesMedicalFragment = new ServicesMedicalFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_menu, servicesMedicalFragment);
                fragmentTransaction.commit();
            }
        });

        mSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SalaryFragment salaryFragment = new SalaryFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_menu, salaryFragment);
                fragmentTransaction.commit();
            }
        });

        mTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToolsFragment toolsFragment = new ToolsFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_menu, toolsFragment);
                fragmentTransaction.commit();
            }
        });

        mClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClothesFragment clothesFragment = new ClothesFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_menu, clothesFragment);
                fragmentTransaction.commit();
            }
        });

        return v;
    }
}