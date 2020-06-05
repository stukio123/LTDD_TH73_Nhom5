package com.example.ecommerciandroiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.ecommerciandroiapp.Adapter.BookDetailAdapter;
import com.example.ecommerciandroiapp.Adapter.BookImageAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class BookDetailActivity extends AppCompatActivity {

    private ViewPager bookImageViewPager;
    private ViewPager bookDetailViewPager;

    private TabLayout bookDetailTabLayout;

    //Rating layout
    private LinearLayout rateNowLayout;
    //Rating layout

    private TabLayout viewPagerIndicator;
    private static boolean ADDED_TO_WISHLIST = false;

    private FloatingActionButton addToWishListbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        bookImageViewPager = findViewById(R.id.book_image_viewpager);
        viewPagerIndicator = findViewById(R.id.viewpager_indicator);
        addToWishListbtn = findViewById(R.id.add_to_wish_list);
        bookDetailViewPager = findViewById(R.id.book_detail_viewpager);
        bookDetailTabLayout = findViewById(R.id.book_detail_tablayout);


        List<Integer> bookImages = new ArrayList<>();
        bookImages.add(R.drawable.ic_account_circle_black_24dp);
        bookImages.add(R.drawable.ic_account_circle_black_24dp);
        bookImages.add(R.drawable.ic_account_circle_black_24dp);
        bookImages.add(R.drawable.ic_account_circle_black_24dp);
        BookImageAdapter bookImageAdapter = new BookImageAdapter(bookImages);
        bookImageViewPager.setAdapter(bookImageAdapter);
        viewPagerIndicator.setupWithViewPager(bookImageViewPager,true);
        addToWishListbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ADDED_TO_WISHLIST){
                    ADDED_TO_WISHLIST = false;
                    addToWishListbtn.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                }else{
                    ADDED_TO_WISHLIST = true;
                    addToWishListbtn.setSupportBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
                }
            }
        });

        bookDetailViewPager.setAdapter(new BookDetailAdapter(getSupportFragmentManager(),bookDetailTabLayout.getTabCount()));
        bookDetailViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(bookDetailTabLayout));
        bookDetailTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                bookDetailViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Rating Layout

        //Rating Layout
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_and_cart,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
            return true;
        }else if(id == R.id.main_search_icon){
            //Thực hiện tìm kiếm sách
            return true;
        }else if(id == R.id.main_cart_icon){
            //Giả hàng
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}