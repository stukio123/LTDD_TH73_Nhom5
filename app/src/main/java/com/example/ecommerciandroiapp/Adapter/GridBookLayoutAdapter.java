package com.example.ecommerciandroiapp.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerciandroiapp.BookDetailActivity;
import com.example.ecommerciandroiapp.Model.AuthorModel;
import com.example.ecommerciandroiapp.Model.HorizontalBookModel;
import com.example.ecommerciandroiapp.R;

import java.util.List;


public class GridBookLayoutAdapter extends BaseAdapter {

    private List<HorizontalBookModel> horizontalBookModelList;

    public GridBookLayoutAdapter(List<HorizontalBookModel> horizontalBookModelList) {
        this.horizontalBookModelList = horizontalBookModelList;
    }

    @Override
    public int getCount() {
        return horizontalBookModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;
        if(convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,null);
            view.setElevation(0);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent bookDetailsIntent = new Intent(parent.getContext(), BookDetailActivity.class);
                    bookDetailsIntent.putExtra("book_id",horizontalBookModelList.get(position).getBookID());
                    parent.getContext().startActivity(bookDetailsIntent);
                }
            });
                ImageView bookImage = view.findViewById(R.id.h_imageBook);
                TextView bookTitle = view.findViewById(R.id.h_titleBook);
                TextView bookCategory = view.findViewById(R.id.h_categoryBook);
                TextView bookPrice = view.findViewById(R.id.h_priceBook);
                Glide.with(parent.getContext()).load(horizontalBookModelList.get(position).getBookImage())
                            .apply(new RequestOptions().placeholder(R.mipmap.sachtienganh)).into(bookImage);
                bookTitle.setText(String.valueOf(horizontalBookModelList.get(position).getBookTitle()));
                bookCategory.setText(String.valueOf(horizontalBookModelList.get(position).getBookCategory()));
                bookPrice.setText(String.valueOf(formatPrice(horizontalBookModelList.get(position).getBookPrice())));

        }else {
            view = convertView;
        }
        return view;
    }

    private String formatPrice(String price){
        int prices = 0;
        if(price != null)
            prices = Integer.parseInt(price);
        return String.format("%,d đ",prices);
    }
}
