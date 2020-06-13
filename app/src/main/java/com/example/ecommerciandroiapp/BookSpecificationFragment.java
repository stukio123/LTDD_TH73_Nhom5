package com.example.ecommerciandroiapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerciandroiapp.Adapter.BookSpecificationAdapter;
import com.example.ecommerciandroiapp.Model.BookSpecificationModel;

import java.util.ArrayList;
import java.util.List;


public class BookSpecificationFragment extends Fragment {

    private RecyclerView bookSpecificationRecyclerView;
    public List<BookSpecificationModel> bookSpecificationModelList;

    public BookSpecificationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_specification, container, false);

        bookSpecificationRecyclerView = view.findViewById(R.id.book_specification_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        bookSpecificationRecyclerView.setLayoutManager(linearLayoutManager);


        BookSpecificationAdapter bookSpecificationAdapter = new BookSpecificationAdapter(bookSpecificationModelList);
        bookSpecificationRecyclerView.setAdapter(bookSpecificationAdapter);
        bookSpecificationAdapter.notifyDataSetChanged();

        return view;
    }
}