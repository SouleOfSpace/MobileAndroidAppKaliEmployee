package com.example.kalidigitalemployee.ui.services.components;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageButton;

import com.example.kalidigitalemployee.R;
import com.example.kalidigitalemployee.ui.services.ServicesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class ServicesTabelFragment extends Fragment {

    private BottomNavigationView bnv;
    ImageButton mNextBtn;
    CalendarView mCalendarView;
    Calendar mCalendar;

    public ServicesTabelFragment() {
        // Required empty public constructor
    }


//    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_services_tabel, container, false);
        mNextBtn = (ImageButton) v.findViewById(R.id.btn_next_fragment_tabel);
        mCalendarView = (CalendarView) v.findViewById(R.id.calendarView);
        mCalendar = Calendar.getInstance();

        mCalendar.set(Calendar.MONTH, Calendar.FEBRUARY);
        mCalendar.set(Calendar.DAY_OF_MONTH, 7);
        mCalendar.set(Calendar.YEAR, 2023);

        mCalendar.add(Calendar.DAY_OF_MONTH, 1);
        mCalendar.add(Calendar.YEAR, 1);

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