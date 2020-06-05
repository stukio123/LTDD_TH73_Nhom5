package com.example.ecommerciandroiapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerciandroiapp.Adapter.MyOrderAdapter;
import com.example.ecommerciandroiapp.Model.MyOrderModel;

import java.util.ArrayList;
import java.util.List;

public class MyOrderFragment extends Fragment {

    public MyOrderFragment() {
        // Required empty public constructor
    }

    private RecyclerView myOrderRecyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);
        myOrderRecyclerView = view.findViewById(R.id.my_order_recycler_view);
        Context context;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrderRecyclerView.setLayoutManager(linearLayoutManager);

        List<MyOrderModel> myOrderModelList = new ArrayList<>();
        myOrderModelList.add(new MyOrderModel(R.mipmap.sachtienganh,2,"Sách tiếng anh","Đã giao hàng ngày x, tháng y, năm z"));
        myOrderModelList.add(new MyOrderModel(R.mipmap.sachtienganh,3,"Sách tiếng anh","Hủy"));
        myOrderModelList.add(new MyOrderModel(R.mipmap.sachtienganh,4,"Sách tiếng anh","Đã giao hàng ngày x, tháng y, năm z"));
        myOrderModelList.add(new MyOrderModel(R.mipmap.sachtienganh,5,"Sách tiếng anh","Đã giao hàng ngày x, tháng y, năm z"));
        MyOrderAdapter adapter = new MyOrderAdapter(myOrderModelList);
        myOrderRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return view;
    }
}