package com.example.kalidigitalemployee.ui.services.components;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.kalidigitalemployee.MainActivity;
import com.example.kalidigitalemployee.R;
import com.example.kalidigitalemployee.ui.services.ServicesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class ServicesPassSystemFragment extends Fragment {

    private BottomNavigationView bnv;
    ImageButton mNextBtn, mCalendarBtn;
    Calendar dateAndTime = Calendar.getInstance();
    TextView mCurrentDate, mDayOfweek;
    LinearLayout mLinear;

    public ServicesPassSystemFragment() {

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_pass_system, container, false);

        mNextBtn = (ImageButton) v.findViewById(R.id.btn_next_fragment_pass_system);
        mCalendarBtn = (ImageButton) v.findViewById(R.id.btn_pass_system_calendar);
        mCurrentDate = (TextView) v.findViewById(R.id.fragment_pass_system_day_date);
        mDayOfweek = (TextView) v.findViewById(R.id.fragment_pass_system_day_of_week);
        mLinear = (LinearLayout) v.findViewById(R.id.linerLayoutWithDates);

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

        mCalendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(),
                        d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        return v;
    }

    private void setInitialDate() {
        mCurrentDate.setText(
                Integer.toString(dateAndTime.get(Calendar.DAY_OF_MONTH)));

        mDayOfweek.setText(DateUtils.formatDateTime(
                getActivity(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_WEEKDAY
        ));
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

//            setInitialDate();
            createDateLayout();
        }
    };

    private void createDateLayout() {
        for (int i = 0; i < 7; i++) {
            TextView textView = new TextView(getActivity());
            textView.setText(Integer.toString(dateAndTime.get(Calendar.DAY_OF_MONTH)));
            textView.setTextSize(16);
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT
            );
            layoutParams.bottomMargin = 5;
            layoutParams.topMargin = 5;
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;

            textView.setLayoutParams(layoutParams);

            mLinear.addView(textView);
        }
    }

    private void createTextViewDate(){
        TextView textView = new TextView(getActivity());
        textView.setText(Integer.toString(dateAndTime.get(Calendar.DAY_OF_MONTH)));
        textView.setTextSize(16);
        textView.setTextColor(getResources().getColor(R.color.white));
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        textView.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        layoutParams.bottomMargin = 5;
        layoutParams.topMargin = 5;
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 5;

        textView.setLayoutParams(layoutParams);

        mLinear.addView(textView);
    }
}