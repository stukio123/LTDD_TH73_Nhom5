package com.example.ecommerciandroiapp.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class BookImageAdapter extends PagerAdapter {

    private List<Integer> bookImages;

    public BookImageAdapter(List<Integer> bookImages) {
        this.bookImages = bookImages;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView bookImage = new ImageView(container.getContext());
        bookImage.setImageResource(bookImages.get(position));
        container.addView(bookImage,0);
        return bookImage;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }

    @Override
    public int getCount() {
        return bookImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
