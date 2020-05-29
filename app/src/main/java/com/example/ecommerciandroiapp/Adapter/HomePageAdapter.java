package com.example.ecommerciandroiapp.Adapter;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerciandroiapp.Model.HomePageModel;
import com.example.ecommerciandroiapp.Model.HorizontalBookModel;
import com.example.ecommerciandroiapp.Model.SliderModel;
import com.example.ecommerciandroiapp.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<HomePageModel> homePageModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()){
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.HORIZONTAL_BOOK_VIEW;
            case 2:
                return HomePageModel.GRID_BOOK_VIEW;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case HomePageModel.BANNER_SLIDER:
                View bannerSliderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_banner_layout,parent,false);
                return new BannerSliderViewHolder(bannerSliderView);
            case HomePageModel.HORIZONTAL_BOOK_VIEW:
                View horizontalBookView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout,parent,false);
                return new HorizontalBookViewHolder(horizontalBookView);
            case HomePageModel.GRID_BOOK_VIEW:
                View GridBookView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_book_layout,parent,false);
                return new GridBookViewHolder(GridBookView);
            default:
                return null;

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (homePageModelList.get(position).getType()){
            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList = homePageModelList.get(position).getSliderModelList();
                ((BannerSliderViewHolder)holder).setBannerSliderViewPage(sliderModelList);
                break;
            case HomePageModel.HORIZONTAL_BOOK_VIEW:
                String horizontalLayoutTitle = homePageModelList.get(position).getBookTitle();
                List<HorizontalBookModel> horizontalBookModelList = homePageModelList.get(position).getHorizontalBookModelList();
                ((HorizontalBookViewHolder)holder).setHorizontalBookLayout(horizontalBookModelList,horizontalLayoutTitle);
                break;
            case HomePageModel.GRID_BOOK_VIEW:
                String gridLayoutTitle = homePageModelList.get(position).getBookTitle();
                List<HorizontalBookModel> gridBookModelList = homePageModelList.get(position).getHorizontalBookModelList();
                ((GridBookViewHolder)holder).setGridBookLayout(gridBookModelList,gridLayoutTitle);
                break;
            default:
                return ;
        }
    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }



    public class BannerSliderViewHolder extends RecyclerView.ViewHolder{

        private ViewPager bannerSliderViewPage;
        private  int currentIndex = 0;
        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;

        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPage = itemView.findViewById(R.id.banner_slider_view_pager);
        }
        private void setBannerSliderViewPage(final List<SliderModel> sliderModelList){
            SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
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
                        pageLoop(sliderModelList);
                    }
                }
            };
            bannerSliderViewPage.addOnPageChangeListener(onPageChangeListener);
            startBannerSlideShow(sliderModelList);
            bannerSliderViewPage.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pageLoop(sliderModelList);
                    stopBannerSlideShow();
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        startBannerSlideShow(sliderModelList);
                    }
                    return false;
                }
            });
        }
        private void pageLoop(List<SliderModel> sliderModelList){
            if(currentIndex == sliderModelList.size()){
                currentIndex = 0;
                bannerSliderViewPage.setCurrentItem(currentIndex,true);
            }
        }
        private void startBannerSlideShow(final List<SliderModel> sliderModelList){
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
    public class HorizontalBookViewHolder extends RecyclerView.ViewHolder{

        private TextView horizontalLayoutTitle;
        private Button horizontalLayoutButton;
        private RecyclerView horizontalRecyclerView;

        public HorizontalBookViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontalLayoutButton = itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontalLayoutButton = itemView.findViewById(R.id.horizontal_scroll_layout_button);
            horizontalRecyclerView = itemView.findViewById(R.id.horizontal_scroll_layout_recycleview);
            horizontalRecyclerView.setRecycledViewPool(recycledViewPool);
        }

        private void setHorizontalBookLayout(List<HorizontalBookModel> horizontalBookModelList,String title){
            horizontalLayoutTitle.setText(title);

            if(horizontalBookModelList.size() > 8)
            {
                horizontalRecyclerView.setVisibility(View.VISIBLE);
            }else{
                horizontalRecyclerView.setVisibility(View.INVISIBLE);
            }


            HorizontalBookAdapter horizontalBookAdapter = new HorizontalBookAdapter(horizontalBookModelList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyclerView.setLayoutManager(linearLayoutManager);
            horizontalRecyclerView.setAdapter(horizontalBookAdapter);
            horizontalBookAdapter.notifyDataSetChanged();
        }
    }
    public class GridBookViewHolder extends  RecyclerView.ViewHolder{

        private TextView gridLayoutTitle;
        private Button gridLayoutButton;
        private GridLayout gridBookLayout;

        public GridBookViewHolder(@NonNull View itemView) {
            super(itemView);
            gridLayoutButton = itemView.findViewById(R.id.grid_book_layout_button);
            gridLayoutTitle = itemView.findViewById(R.id.grid_book_layout_title);
            gridBookLayout = itemView.findViewById(R.id.grid_layout);
        }
        private void setGridBookLayout(List<HorizontalBookModel> horizontalBookModelList,String title){
            gridLayoutTitle.setText(title);
            for(int i = 0; i<4;i++) {
                ImageView bookImage = gridBookLayout.getChildAt(i).findViewById(R.id.h_imageBook);
                TextView bookTitle = gridBookLayout.getChildAt(i).findViewById(R.id.h_titleBook);
                TextView bookCategory = gridBookLayout.getChildAt(i).findViewById(R.id.h_categoryBook);
                TextView bookPrice = gridBookLayout.getChildAt(i).findViewById(R.id.h_priceBook);
                Glide.with(itemView.getContext()).load(horizontalBookModelList.get(i).getBookImage())
                        .apply(new RequestOptions().override(800, 600)).into(bookImage);
                bookTitle.setText(horizontalBookModelList.get(i).getBookTitle());
                bookCategory.setText(horizontalBookModelList.get(i).getBookCategory());
                bookPrice.setText(horizontalBookModelList.get(i).getBookPrice());
            }
        }
    }
}
