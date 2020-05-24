package com.example.ecommerciandroiapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        return 4;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,parent,false);
            view.setElevation(0);
            ImageView bookImage = view.findViewById(R.id.h_imageBook);
            TextView bookTitle = view.findViewById(R.id.h_titleBook);
            TextView bookCategory = view.findViewById(R.id.h_categoryBook);
            TextView bookPrice = view.findViewById(R.id.h_priceBook);

            bookImage.setImageResource(horizontalBookModelList.get(position).getBookImage());
            bookTitle.setText(horizontalBookModelList.get(position).getBookTitel());
            bookCategory.setText(horizontalBookModelList.get(position).getBookCategory());
            bookPrice.setText(horizontalBookModelList.get(position).getBookPrice());
        }else {
            view = convertView;
        }
        return view;
    }
}
