package com.example.ecommerciandroiapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerciandroiapp.Adapter.CartAdapter;
import com.example.ecommerciandroiapp.Adapter.WishListAdapter;
import com.example.ecommerciandroiapp.Database.DataBaseQueries;
import com.example.ecommerciandroiapp.Model.CartItemModel;

import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment {

    public MyCartFragment() {
    }

    private RecyclerView cartItemRecyclerView;
    private Button pay;
    private Dialog loadingDialog;
    public static CartAdapter cartAdapter;
    public static TextView totalAmount;

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
        pay = view.findViewById(R.id.cart_continue_btn);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecyclerView.setLayoutManager(linearLayoutManager);
        totalAmount = view.findViewById(R.id.total_prices);

        loadingDialog = new Dialog(getContext());
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(R.layout.loading_progress_layout);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
            if(DataBaseQueries.cartItemModelList.size() == 0 ){
                DataBaseQueries.cartList.clear();
                DataBaseQueries.loadCartList(getContext(),loadingDialog,true,new TextView(getContext()));
            }else{
                DataBaseQueries.loadCartList(getContext(),loadingDialog,false,new TextView(getContext()));
                loadingDialog.dismiss();
            }

        cartAdapter = new CartAdapter(DataBaseQueries.cartItemModelList,totalAmount,true);
        cartItemRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.show();
                if(DataBaseQueries.cartList.size() != 0){
                    DataBaseQueries.loadAddresses(getContext(),loadingDialog);
                }else{
                    Toast.makeText(getContext(),"Không thể tiếp tục thanh toán khi giỏ hàng trống",Toast.LENGTH_LONG).show();
                    loadingDialog.dismiss();
                }

            }
        });

        return view;
    }
}