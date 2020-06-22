package com.example.ecommerciandroiapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.ecommerciandroiapp.Adapter.AuthorAdapter;
import com.example.ecommerciandroiapp.Adapter.HomePageAdapter;
import com.example.ecommerciandroiapp.Adapter.WishListAdapter;
import com.example.ecommerciandroiapp.Model.AuthorModel;
import com.example.ecommerciandroiapp.Model.WishListModel;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.ecommerciandroiapp.Database.DataBaseQueries.firebaseFirestore;
import static com.example.ecommerciandroiapp.Database.DataBaseQueries.homePageModelList;
import static com.example.ecommerciandroiapp.Database.DataBaseQueries.loadFragmentData;

public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();

    private RecyclerView homePageRecyclerView;
    private GridView author_gridview;
    public static SwipeRefreshLayout refreshLayout;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private Button retrybtn;
    private List<AuthorModel> authorModelList;
    private AuthorAdapter authorAdapter;
    private RecyclerView bookHotRecyclerView;
    private List<WishListModel> wishListModelList;
    private WishListAdapter wishListAdapter;

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

        author_gridview = view.findViewById(R.id.grid_book_layout);
        bookHotRecyclerView = view.findViewById(R.id.book_hot_recycler_view);
        bookHotRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        wishListModelList = new ArrayList<>();
        wishListAdapter = new WishListAdapter(wishListModelList,false);
        bookHotRecyclerView.setAdapter(wishListAdapter);

        homePageRecyclerView.setHasFixedSize(true);
        retrybtn = view.findViewById(R.id.retry_btn);
        authorModelList = new ArrayList<>();
        authorAdapter = new AuthorAdapter(authorModelList);
        author_gridview.setAdapter(authorAdapter);
        author_gridview.setNumColumns(4);

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

            firebaseFirestore.collection("Banners")
                    .document("Deal")
                    .collection("author")
                    .orderBy("index").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot documentSnapshot : task.getResult()) {
                            authorModelList.add(new AuthorModel(documentSnapshot.getString("name"),documentSnapshot.getString("image")));

                        }
                        author_gridview.setAdapter(authorAdapter);
                    }else{
                        String error = task.getException().getMessage();
                        Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                    }
                }
            });



            firebaseFirestore.collection("Books").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            wishListModelList.add(new WishListModel(documentSnapshot.getId()
                                    , documentSnapshot.getString("image")
                                    , documentSnapshot.getString("title")
                                    , documentSnapshot.getString("author")
                                    , documentSnapshot.getLong("avg_rating")
                                    , documentSnapshot.getLong("total_rating")
                                    , documentSnapshot.getString("price")
                                    , documentSnapshot.getString("cutted_price")));
                        }
                        wishListAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "Không tìm thấy sách", Toast.LENGTH_SHORT).show();
                    }
                }
            });

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
          //      refreshLayout.setRefreshing(true);
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
           // refreshLayout.setRefreshing(false);
        }
    }
}
