package com.example.ecommerciandroiapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerciandroiapp.Adapter.CartAdapter;
import com.example.ecommerciandroiapp.Model.CartItemModel;

import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment {

    public MyCartFragment() {
    }

    private RecyclerView cartItemRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        cartItemRecyclerView = view.findViewById(R.id.cart_items_recycler_view);
        Context context;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecyclerView.setLayoutManager(linearLayoutManager);

        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0, R.mipmap.sachtienganh,"Sách tiếng anh","120.000đ","Bộ Giáo Dục","199.000đ",1));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.sachtienganh,"Sách tiếng anh","120.000đ","Bộ Giáo Dục","199.000đ",1));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.sachtienganh,"Sách tiếng anh","120.000đ","Bộ Giáo Dục","199.000đ",1));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.sachtienganh,"Sách tiếng anh","120.000đ","Bộ Giáo Dục","199.000đ",1));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.sachtienganh,"Sách tiếng anh","120.000đ","Bộ Giáo Dục","199.000đ",1));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.sachtienganh,"Sách tiếng anh","120.000đ","Bộ Giáo Dục","199.000đ",1));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.sachtienganh,"Sách tiếng anh","120.000đ","Bộ Giáo Dục","199.000đ",1));
        cartItemModelList.add(new CartItemModel(1,"Giá (3 sản phẩm)","360.000đ","Free","237.000đ","3"));

        CartAdapter adapter = new CartAdapter(cartItemModelList);
        cartItemRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }
}