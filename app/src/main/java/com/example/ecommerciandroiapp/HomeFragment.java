package com.example.ecommerciandroiapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.ecommerciandroiapp.Adapter.HomePageAdapter;

import static com.example.ecommerciandroiapp.Database.DataBaseQueries.homePageModelList;
import static com.example.ecommerciandroiapp.Database.DataBaseQueries.loadFragmentData;

public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();

    private RecyclerView homePageRecyclerView;
    public static SwipeRefreshLayout refreshLayout;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private Button retrybtn;

    private HomePageAdapter adapter;
    private ImageView noInternetConnection;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home,container,false);
        noInternetConnection = view.findViewById(R.id.no_intenet_connection);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        homePageRecyclerView = view.findViewById(R.id.home_page_recycler_view);
        homePageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homePageRecyclerView.setHasFixedSize(true);
        retrybtn = view.findViewById(R.id.retry_btn);

        if(networkInfo != null && networkInfo.isConnected()) {
            noInternetConnection.setVisibility(View.GONE);
            retrybtn.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.VISIBLE);
            adapter = new HomePageAdapter(homePageModelList);
            homePageRecyclerView.setAdapter(adapter);
            if(homePageModelList.size() == 0){
                loadFragmentData(adapter,getContext());
            }else{
                adapter.notifyDataSetChanged();
            }
        }else {
            homePageRecyclerView.setVisibility(View.GONE);
            retrybtn.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.mipmap.nointernet_r_2x).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(),"Không có kết nối mạng, xin vui lòng thử lại khi kết nối mạng thành công",Toast.LENGTH_LONG).show();
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                reloadPage();
            }
        });

        retrybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadPage();
            }
        });

        return view;
    }

    private void reloadPage(){
        networkInfo = connectivityManager.getActiveNetworkInfo();
        homePageModelList.clear();
        if(networkInfo != null && networkInfo.isConnected()) {
            retrybtn.setVisibility(View.GONE);
            noInternetConnection.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.VISIBLE);
            adapter = new HomePageAdapter(homePageModelList);
            homePageRecyclerView.setAdapter(adapter);
            loadFragmentData(adapter,getContext());
        }else {
            retrybtn.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(getContext()).load(R.mipmap.nointernet_r_2x).into(noInternetConnection);
            Toast.makeText(getContext(),"Không có kết nối mạng, xin vui lòng thử lại khi kết nối mạng thành công",Toast.LENGTH_LONG).show();
            noInternetConnection.setVisibility(View.VISIBLE);
            refreshLayout.setRefreshing(false);
        }
    }
}
