package com.example.ecommerciandroiapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerciandroiapp.Adapter.WishListAdapter;
import com.example.ecommerciandroiapp.Model.WishListModel;

import java.util.ArrayList;
import java.util.List;

public class MyWishListFragment extends Fragment {

    public MyWishListFragment() {
        // Required empty public constructor
    }

    private RecyclerView wishlistRecyclerView;


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
        Context context;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlistRecyclerView.setLayoutManager(linearLayoutManager);
        List<WishListModel> wishListModelList = new ArrayList<>();
        wishListModelList.add(new WishListModel(R.mipmap.sachtienganh,"Sách tiếng anh","Bộ Giáo Dục","4.5",27,"120.000 đ","199.000 đ"));
        wishListModelList.add(new WishListModel(R.mipmap.sachtienganh,"Sách tiếng anh","Bộ Giáo Dục","1.5",5,"120.000 đ","199.000 đ"));
        wishListModelList.add(new WishListModel(R.mipmap.sachtienganh,"Sách tiếng anh","Bộ Giáo Dục","2",15,"120.000 đ","199.000 đ"));
        wishListModelList.add(new WishListModel(R.mipmap.sachtienganh,"Sách tiếng anh","Bộ Giáo Dục","5",390,"120.000 đ","199.000 đ"));
        wishListModelList.add(new WishListModel(R.mipmap.sachtienganh,"Sách tiếng anh","Bộ Giáo Dục","3",23,"120.000 đ","199.000 đ"));
        WishListAdapter adapter = new WishListAdapter(wishListModelList);
        wishlistRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }
}