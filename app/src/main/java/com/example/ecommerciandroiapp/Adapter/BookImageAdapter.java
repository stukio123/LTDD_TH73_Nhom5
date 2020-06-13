package com.example.ecommerciandroiapp.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerciandroiapp.R;

import java.util.List;

public class BookImageAdapter extends PagerAdapter {

    private List<String> bookImages;

    public BookImageAdapter(List<String> bookImages) {
        this.bookImages = bookImages;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView bookImage = new ImageView(container.getContext());
        Glide.with(container.getContext()).load(bookImages.get(position)).apply(new RequestOptions().placeholder(R.mipmap.sachtienganh)).into(bookImage);
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
