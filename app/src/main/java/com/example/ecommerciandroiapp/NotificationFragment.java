package com.example.ecommerciandroiapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerciandroiapp.Adapter.NotificationAdapter;
import com.example.ecommerciandroiapp.Adapter.RewardAdapter;
import com.example.ecommerciandroiapp.Model.NotificationModel;
import com.example.ecommerciandroiapp.Model.RewardModel;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    public NotificationFragment() {
        // Required empty public constructor
    }
    private RecyclerView notificationRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        notificationRecyclerView = view.findViewById(R.id.notification_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        notificationRecyclerView.setLayoutManager(linearLayoutManager);

        List<NotificationModel> notificationModelList = new ArrayList<>();

        notificationModelList.add(new NotificationModel("aaaaaaaaaaaa",R.drawable.ic_person_black_24dp));
        notificationModelList.add(new NotificationModel("aaaaaaaaaaaa",R.drawable.ic_person_black_24dp));
        notificationModelList.add(new NotificationModel("aaaaaaaaaaaa",R.drawable.ic_person_black_24dp));
        notificationModelList.add(new NotificationModel("aaaaaaaaaaaa",R.drawable.ic_person_black_24dp));
        notificationModelList.add(new NotificationModel("aaaaaaaaaaaa",R.drawable.ic_person_black_24dp));

        NotificationAdapter adapter = new NotificationAdapter(notificationModelList);
        notificationRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }
}
