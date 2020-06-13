package com.example.ecommerciandroiapp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ecommerciandroiapp.BookDescriptionFragment;
import com.example.ecommerciandroiapp.BookDetailActivity;
import com.example.ecommerciandroiapp.BookSpecificationFragment;
import com.example.ecommerciandroiapp.Model.BookSpecificationModel;

import java.util.List;


public class BookDetailAdapter extends FragmentPagerAdapter {

    private int totalTabs;
    private String bookDescription;
    private List<BookSpecificationModel> bookSpecificationModelList;

    public BookDetailAdapter (@NonNull FragmentManager fm, int totalTabs, String bookDescription, List<BookSpecificationModel> bookSpecificationModelList){
        super(fm);
        this.bookDescription = bookDescription;
        this.bookSpecificationModelList = bookSpecificationModelList;
        this.totalTabs = totalTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                BookDescriptionFragment bookDescriptionFragment = new BookDescriptionFragment();
                bookDescriptionFragment.body = bookDescription;
                return bookDescriptionFragment;
            case 1:
                BookSpecificationFragment bookSpecificationFragment = new BookSpecificationFragment();
                bookSpecificationFragment.bookSpecificationModelList = bookSpecificationModelList;
                return bookSpecificationFragment;
            case 2:
                BookDescriptionFragment bookDescriptionFragment2 = new BookDescriptionFragment();
                bookDescriptionFragment2.body = bookDescription;
                return bookDescriptionFragment2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
