package com.example.ecommerciandroiapp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ecommerciandroiapp.BookDescriptionFragment;
import com.example.ecommerciandroiapp.BookSpecificationFragment;


public class BookDetailAdapter extends FragmentPagerAdapter {
    private int totalTabs;

    public BookDetailAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                BookDescriptionFragment bookDescriptionFragment = new BookDescriptionFragment();
                return bookDescriptionFragment;
            case 1:
                BookSpecificationFragment bookSpecificationFragment = new BookSpecificationFragment();
                return bookSpecificationFragment;
            case 2:
                BookDescriptionFragment bookDescriptionFragment2 = new BookDescriptionFragment();
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
