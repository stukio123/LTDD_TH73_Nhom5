package com.example.ecommerciandroiapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BookDescriptionFragment extends Fragment {

    public BookDescriptionFragment() {
        // Required empty public constructor
    }

    private TextView description;
    public String body;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_description, container, false);
        description = view.findViewById(R.id.tv_book_description);
        description.setText(body);
        return view;
    }
}