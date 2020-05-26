package com.example.ecommerciandroiapp;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.ecommerciandroiapp.Adapter.CategoryAdapter;
import com.example.ecommerciandroiapp.Adapter.GridBookLayoutAdapter;
import com.example.ecommerciandroiapp.Adapter.HomePageAdapter;
import com.example.ecommerciandroiapp.Adapter.HorizontalBookAdapter;
import com.example.ecommerciandroiapp.Adapter.SliderAdapter;
import com.example.ecommerciandroiapp.Model.CategoryModel;
import com.example.ecommerciandroiapp.Model.HomePageModel;
import com.example.ecommerciandroiapp.Model.HorizontalBookModel;
import com.example.ecommerciandroiapp.Model.SliderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
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
    private SliderAdapter sliderAdapter;
    private List<CategoryModel> categoryModelList;
    private FirebaseFirestore firebaseFirestore;
    private ViewPager bannerSliderViewPage;
    private List<SliderModel> sliderModelList;

    private TextView horizontalLayoutTitle;
    private Button viewAll;
    private RecyclerView horizontalRecyclerView;

    private  int currentIndex = 0;
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
        bannerSliderViewPage = view.findViewById(R.id.banner_slider_view_pager);
        bannerSliderViewPage.setCurrentItem(currentIndex);
        sliderModelList = new ArrayList<>();
        firebaseFirestore.collection("Banner").document("Deal")
                .collection("Banner").orderBy("index").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot: task.getResult())
                    {
                        try{
                            sliderModelList.add(new SliderModel(documentSnapshot.get("banner_image").toString()));
                        }catch(Exception e){
                            Log.e(TAG,"Lỗi: "+e.getMessage());
                        }
                        sliderAdapter.notifyDataSetChanged();
                    }
                }else{
                    String error = task.getException().getMessage();
                    Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                }
            }
        });
        sliderAdapter = new SliderAdapter(sliderModelList);
        bannerSliderViewPage.setAdapter(sliderAdapter);
        bannerSliderViewPage.setClipToPadding(false);
        bannerSliderViewPage.setPageMargin(20);

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == ViewPager.SCROLL_STATE_IDLE){
                    pageLoop();
                }
            }
        };
        bannerSliderViewPage.addOnPageChangeListener(onPageChangeListener);
        startBannerSlideShow();
        bannerSliderViewPage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pageLoop();
                stopBannerSlideShow();
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    startBannerSlideShow();
                }
                return false;
            }
        });
        //Kết thúc khởi tạo banner

        //Horizontal View
        horizontalLayoutTitle = view.findViewById(R.id.horizontal_scroll_layout_title);
        viewAll = view.findViewById(R.id.horizontal_scroll_layout_button);
        horizontalRecyclerView = view.findViewById(R.id.horizontal_scroll_layout_recycleview);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        horizontalRecyclerView.setLayoutManager(layoutManager1);
        final List<HorizontalBookModel> horizontalBookModelList = new ArrayList<>();
        final HorizontalBookAdapter horizontalBookAdapter = new HorizontalBookAdapter(horizontalBookModelList);
        firebaseFirestore.collection("Books").orderBy("isbn").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult())
                            {
                                try{
                                    horizontalBookModelList.add(new HorizontalBookModel(documentSnapshot.get("image").toString()
                                            ,documentSnapshot.get("title").toString(),
                                            documentSnapshot.get("category").toString(),documentSnapshot.get("price").toString()));
                                }catch(Exception e){
                                    Log.e(TAG,"Lỗi: "+e.getMessage());
                                }
                                horizontalBookAdapter.notifyDataSetChanged();
                            }
                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        horizontalRecyclerView.setAdapter(horizontalBookAdapter);
        //Horizontal View

        //Grid View
        TextView gridLayoutTitle = view.findViewById(R.id.grid_book_layout_title);
        GridLayout gridView = view.findViewById(R.id.grid_layout);
        Button gridlayoutButton = view.findViewById(R.id.grid_book_layout_button);

        //Grid View

        //Test HomeRecycler
        RecyclerView testing = view.findViewById(R.id.testing);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);
        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //
        return view;
    }

    private void pageLoop(){
        if(currentIndex == sliderModelList.size()){
            currentIndex = 0;
            bannerSliderViewPage.setCurrentItem(currentIndex,true);
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
