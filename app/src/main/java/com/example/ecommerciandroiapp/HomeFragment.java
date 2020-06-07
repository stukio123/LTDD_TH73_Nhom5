package com.example.ecommerciandroiapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerciandroiapp.Adapter.HomePageAdapter;

import static com.example.ecommerciandroiapp.Database.DataBaseQueries.homePageModelList;
import static com.example.ecommerciandroiapp.Database.DataBaseQueries.loadFragmentData;

public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();

    private RecyclerView homePageRecyclerView;

    private HomePageAdapter adapter;
    private ImageView noInternetConnection;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home,container,false);
        noInternetConnection = view.findViewById(R.id.no_intenet_connection);

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            noInternetConnection.setVisibility(View.GONE);
            homePageRecyclerView = view.findViewById(R.id.home_page_recycler_view);
            homePageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            homePageRecyclerView.setHasFixedSize(true);

            adapter = new HomePageAdapter(homePageModelList);
            homePageRecyclerView.setAdapter(adapter);

            if(homePageModelList.size() == 0){
                loadFragmentData(adapter,getContext());
            }else{
                adapter.notifyDataSetChanged();
            }
        }else {
            Glide.with(this).load(R.mipmap.nointernet_r_2x).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
        }
        return view;
    }

}
