package com.example.kalidigitalemployee.ui.services.components;

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


public class ToolsFragment extends Fragment {

    ImageButton mNextBtn;

    public ToolsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tools, container, false);
        mNextBtn = (ImageButton) v.findViewById(R.id.btn_next_fragment_tools);

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