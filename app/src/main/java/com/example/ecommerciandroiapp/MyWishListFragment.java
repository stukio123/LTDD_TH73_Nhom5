package com.example.ecommerciandroiapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerciandroiapp.Adapter.WishListAdapter;
import com.example.ecommerciandroiapp.Database.DataBaseQueries;
import com.example.ecommerciandroiapp.Model.WishListModel;

import java.util.ArrayList;
import java.util.List;

public class MyWishListFragment extends Fragment {

    public MyWishListFragment() {
        // Required empty public constructor
    }

    private RecyclerView wishlistRecyclerView;
    private Dialog loadingDialog;
    public static WishListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_wish_list, container, false);
        wishlistRecyclerView = view.findViewById(R.id.my_wishlist_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlistRecyclerView.setLayoutManager(linearLayoutManager);

        loadingDialog = new Dialog(getContext());
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(R.layout.loading_progress_layout);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        if(DataBaseQueries.wishListModelList.size() == 0 ){
            DataBaseQueries.wishList.clear();
            DataBaseQueries.loadWishList(getContext(),loadingDialog,true);
        }else{
            loadingDialog.dismiss();
        }

        adapter = new WishListAdapter(DataBaseQueries.wishListModelList,true);
        wishlistRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }
}