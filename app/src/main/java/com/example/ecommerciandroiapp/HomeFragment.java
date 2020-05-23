package com.example.ecommerciandroiapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {

    private String EVENT_DATE_TIME = "2020-05-24 10:30:00";
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private LinearLayout linearlayout;
    private TextView txt_gio, txt_phut, txt_giay;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        linearlayout = view.findViewById(R.id.linearlayout_thoigian);
        txt_gio = view.findViewById(R.id.txt_gio);
        txt_phut = view.findViewById(R.id.txt_phut);
        txt_giay = view.findViewById(R.id.txt_giay);

        countDownStart();
        return view;


    }


    private void countDownStart() {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date event_date = dateFormat.parse(EVENT_DATE_TIME);
                    Date current_date = new Date();
                    if (!current_date.after(event_date)) {
                        long diff = event_date.getTime() - current_date.getTime();
//                        long Days = diff / (24 * 60 * 60 * 1000);
                        long Hours = diff / (24 * 60 * 60 * 1000)*24 + diff / (60 * 60 * 1000) % 24 ;
                        long Minutes = diff / (60 * 1000) % 60;
                        long Seconds = diff / 1000 % 60;
                        //
//                        txt_ngay.setText(String.format("%02d", Days));
                        txt_gio.setText(String.format("%02d", Hours));
                        txt_phut.setText(String.format("%02d", Minutes));
                        txt_giay.setText(String.format("%02d", Seconds));

                        linearlayout.setVisibility(View.VISIBLE);
                    } else {

                        handler.removeCallbacks(runnable);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }




}
