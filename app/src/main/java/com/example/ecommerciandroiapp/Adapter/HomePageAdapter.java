package com.example.ecommerciandroiapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerciandroiapp.BookAllActivity;
import com.example.ecommerciandroiapp.BookDetailActivity;
import com.example.ecommerciandroiapp.Model.HomePageModel;
import com.example.ecommerciandroiapp.Model.HorizontalBookModel;
import com.example.ecommerciandroiapp.Model.SliderModel;
import com.example.ecommerciandroiapp.Model.WishListModel;
import com.example.ecommerciandroiapp.R;
import com.example.ecommerciandroiapp.ViewAllActivity;

import java.util.ArrayList;
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
            /*case 3:
                return HomePageModel.HORIZONTAL_AUTHOR_VIEW;*/
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
            /*case HomePageModel.HORIZONTAL_AUTHOR_VIEW:
                View horizontalAuthorView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_author_layout,parent,false);
                return new HorizontalBookViewHolder(horizontalAuthorView);*/
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
                List<WishListModel> viewAllList = homePageModelList.get(position).getViewAllList();
                List<HorizontalBookModel> horizontalBookModelList = homePageModelList.get(position).getHorizontalBookModelList();
                ((HorizontalBookViewHolder)holder).setHorizontalBookLayout(horizontalBookModelList,viewAllList);
                break;
            case HomePageModel.GRID_BOOK_VIEW:
                String gridLayoutTitle = homePageModelList.get(position).getBookTitle();
                List<HorizontalBookModel> gridBookModelList = homePageModelList.get(position).getHorizontalBookModelList();
                ((GridBookViewHolder)holder).setGridBookLayout(gridBookModelList,gridLayoutTitle);
                break;
            /*case HomePageModel.HORIZONTAL_AUTHOR_VIEW:
                //String horizontalLayoutTitle = homePageModelList.get(position).getBookTitle();
                List<WishListModel> viewAllAuthorList = homePageModelList.get(position).getViewAllList();
                List<HorizontalBookModel> horizontalAuthorModelList = homePageModelList.get(position).getHorizontalBookModelList();
                ((HorizontalAuthorViewHolder)holder).setHorizontalAuthorLayout(horizontalAuthorModelList,viewAllAuthorList);
                break;*/
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
        private  int currentIndex ;
        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;
        private List<SliderModel> arrangedList;

        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPage = itemView.findViewById(R.id.banner_slider_view_pager);
        }
        @SuppressLint("ClickableViewAccessibility")
        private void setBannerSliderViewPage(final List<SliderModel> sliderModelList){
            currentIndex = 2;
            if(timer != null){
                timer.cancel();
            }
            arrangedList = new ArrayList<>();
            for(int i = 0;i <sliderModelList.size();i++){
                arrangedList.add(i,sliderModelList.get(i));
            }
            arrangedList.add(0,sliderModelList.get(sliderModelList.size() - 2));
            arrangedList.add(1,sliderModelList.get(sliderModelList.size()-1));
            arrangedList.add(sliderModelList.get(0));
            arrangedList.add(sliderModelList.get(1));

            SliderAdapter sliderAdapter = new SliderAdapter(arrangedList);
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
                        pageLoop(arrangedList);
                    }
                }
            };
            bannerSliderViewPage.addOnPageChangeListener(onPageChangeListener);
            startBannerSlideShow(arrangedList);
            bannerSliderViewPage.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pageLoop(arrangedList);
                    stopBannerSlideShow();
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        startBannerSlideShow(arrangedList);
                    }
                    Intent bookIntent = new Intent(itemView.getContext(), BookAllActivity.class);
                    //bookIntent.putExtra("layout_code",1);
                    //bookIntent.putExtra("title","Quảng Cáo");
                    itemView.getContext().startActivity(bookIntent);
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

        //private TextView horizontalLayoutTitle;
        private Button horizontalLayoutButton;
        private RecyclerView horizontalRecyclerView;

        public HorizontalBookViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontalRecyclerView = itemView.findViewById(R.id.horizontal_scroll_layout_recycleview);
            horizontalLayoutButton = itemView.findViewById(R.id.view_all_btn);
            horizontalRecyclerView.setRecycledViewPool(recycledViewPool);
        }

        private void setHorizontalBookLayout(List<HorizontalBookModel> horizontalBookModelList, final List<WishListModel> viewAllBookList){

            if(horizontalBookModelList.size() > 8)
            {
                horizontalLayoutButton.setVisibility(View.VISIBLE);
                horizontalLayoutButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.wishListModelList = viewAllBookList;
                        Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                        viewAllIntent.putExtra("layout_code",0);
                        itemView.getContext().startActivity(viewAllIntent);
                    }
                });
            }else{
                horizontalLayoutButton.setVisibility(View.INVISIBLE);
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
            gridBookLayout = itemView.findViewById(R.id.grid_book_layout);
        }
        private void setGridBookLayout(final List<HorizontalBookModel> horizontalBookModelList, final String title){
            gridLayoutTitle.setText(title);

            for(int i = 0; i<4;i++) {
                ImageView bookImage = gridBookLayout.getChildAt(i).findViewById(R.id.h_imageBook);
                TextView bookTitle = gridBookLayout.getChildAt(i).findViewById(R.id.h_titleBook);
                TextView bookCategory = gridBookLayout.getChildAt(i).findViewById(R.id.h_categoryBook);
                final TextView bookPrice = gridBookLayout.getChildAt(i).findViewById(R.id.h_priceBook);
                Glide.with(itemView.getContext()).load(horizontalBookModelList.get(i).getBookImage())
                        .apply(new RequestOptions().placeholder(R.mipmap.sachtienganh)).into(bookImage);
                bookTitle.setText(String.valueOf(horizontalBookModelList.get(i).getBookTitle()));
                bookCategory.setText(String.valueOf(horizontalBookModelList.get(i).getBookCategory()));
                bookPrice.setText(String.valueOf(horizontalBookModelList.get(i).getBookPrice()));
                bookTitle.setText(String.valueOf(horizontalBookModelList.get(i).getBookTitle()));
                bookCategory.setText(String.valueOf(horizontalBookModelList.get(i).getBookCategory()));
                bookPrice.setText(String.valueOf(formatPrice(horizontalBookModelList.get(i).getBookPrice())));
                final int finalI = i;
                gridBookLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent bookDetailsIntent = new Intent(itemView.getContext(), BookDetailActivity.class);
                        bookDetailsIntent.putExtra("book_id",horizontalBookModelList.get(finalI).getBookID());
                        itemView.getContext().startActivity(bookDetailsIntent);
                    }
                });
            }

            gridLayoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewAllActivity.horizontalBookModelList = horizontalBookModelList;
                    Intent viewAllIntent = new Intent(itemView.getContext(),ViewAllActivity.class);
                    viewAllIntent.putExtra("layout_code",1);
                    viewAllIntent.putExtra("title",title);
                    itemView.getContext().startActivity(viewAllIntent);
                }
            });
        }
        private String formatPrice(String price){
            int prices = 0;
            if(price != null)
                prices = Integer.parseInt(price);
            return String.format("%,d đ",prices);
        }
    }
    public class HorizontalAuthorViewHolder extends RecyclerView.ViewHolder{

        private Button horizontalLayoutButton;
        private RecyclerView horizontalRecyclerView;

        public HorizontalAuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontalRecyclerView = itemView.findViewById(R.id.horizontal_scroll_layout_recycleview);
            horizontalLayoutButton = itemView.findViewById(R.id.view_all_btn);
            horizontalRecyclerView.setRecycledViewPool(recycledViewPool);
        }

        private void setHorizontalAuthorLayout(List<HorizontalBookModel> horizontalBookModelList, final List<WishListModel> viewAllBookList){
            if(horizontalBookModelList.size() > 8)
            {
                horizontalLayoutButton.setVisibility(View.VISIBLE);
                horizontalLayoutButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.wishListModelList = viewAllBookList;
                        Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                        viewAllIntent.putExtra("layout_code",0);
                        itemView.getContext().startActivity(viewAllIntent);
                    }
                });
            }else{
                horizontalLayoutButton.setVisibility(View.INVISIBLE);
            }
            HorizontalBookAdapter horizontalBookAdapter = new HorizontalBookAdapter(horizontalBookModelList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyclerView.setLayoutManager(linearLayoutManager);
            horizontalRecyclerView.setAdapter(horizontalBookAdapter);
            horizontalBookAdapter.notifyDataSetChanged();
        }
    }
}
