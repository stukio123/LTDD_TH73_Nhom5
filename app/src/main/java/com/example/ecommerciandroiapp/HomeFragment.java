package com.example.ecommerciandroiapp;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.ecommerciandroiapp.Adapter.CategoryAdapter;
import com.example.ecommerciandroiapp.Adapter.HomePageAdapter;
import com.example.ecommerciandroiapp.Adapter.SliderAdapter;
import com.example.ecommerciandroiapp.Model.CategoryModel;
import com.example.ecommerciandroiapp.Model.HomePageModel;
import com.example.ecommerciandroiapp.Model.SliderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();

    private RecyclerView categoryRecyclerView;
    private RecyclerView homPageRecyclerView;
    private CategoryAdapter categoryAdapter;
    private List<CategoryModel> categoryModelList;
    private FirebaseFirestore firebaseFirestore;
    private ViewPager bannerSliderViewPage;
    private List<SliderModel> sliderModelList;
    private  int currentIndex = 2;
    private Timer timer;
    final private long DELAY_TIME = 3000;
    final private long PERIOD_TIME = 3000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        // Khởi tạo Danh mục (Category)
        categoryRecyclerView = view.findViewById(R.id.category_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryModelList  = new ArrayList<CategoryModel>();
        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Categories").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult())
                            {
                                try {
                                    categoryModelList.add(new CategoryModel(documentSnapshot.get("thumbNail").toString(),
                                            documentSnapshot.get("categoryName").toString()));
                                }catch (Exception e)
                                {
                                    Log.e(TAG,"Lỗi: "+e.getMessage());
                                }

                            }
                            categoryAdapter.notifyDataSetChanged();
                        }
                        else
                        {
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // Kết thúc khởi tạo danh mục

        //Khởi Tạo Banner

        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        //Kết thúc khởi tạo banner
        return view;
    }

    private void pageLoop(){
        if(currentIndex == sliderModelList.size()-2){
            currentIndex = 2;
            bannerSliderViewPage.setCurrentItem(currentIndex,false);
        }
        if(currentIndex == 1){
            currentIndex = sliderModelList.size()-3;
            bannerSliderViewPage.setCurrentItem(currentIndex,false);
        }
    }
    private void startBannerSlideShow(){
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if(currentIndex >= sliderModelList.size()){
                    currentIndex = 1;
                }
                bannerSliderViewPage.setCurrentItem(currentIndex++,true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },DELAY_TIME,PERIOD_TIME);
    }
    private void stopBannerSlideShow(){
        timer.cancel();
    }
}
