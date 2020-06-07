package com.example.ecommerciandroiapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerciandroiapp.Adapter.RewardAdapter;
import com.example.ecommerciandroiapp.Model.RewardModel;

import java.util.ArrayList;
import java.util.List;

public class MyRewardFragment extends Fragment {

    public MyRewardFragment() {
        // Required empty public constructor
    }

    private RecyclerView rewardRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_reward, container, false);

        rewardRecyclerView = view.findViewById(R.id.my_reward_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rewardRecyclerView.setLayoutManager(linearLayoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("Giảm giá 20%","27/7/2020","Áp dụng cho sản phẩm của Tác giả Nguyễn Nhật Ánh"));
        rewardModelList.add(new RewardModel("Mua 1 tặng 1","7/7/2020","Áp dụng cho Khách hàng mới"));
        rewardModelList.add(new RewardModel("Giảm giá 50%","31/7/2020","Áp dụng cho sản phẩm Truyện Tranh"));
        rewardModelList.add(new RewardModel("Giảm giá 20%","1/7/2020","Áp dụng cho sản phẩm Khoa Học &  Công Nghệ"));
        rewardModelList.add(new RewardModel("Giảm giá 20%","27/6/2020","Áp dụng cho sản phẩm của Tác giả Tô Hoài"));

        RewardAdapter adapter = new RewardAdapter(rewardModelList);
        rewardRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }
}