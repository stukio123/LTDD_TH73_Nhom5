package com.example.ecommerciandroiapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerciandroiapp.AuthorActivity;
import com.example.ecommerciandroiapp.BookDetailActivity;
import com.example.ecommerciandroiapp.Model.AuthorModel;
import com.example.ecommerciandroiapp.R;

import java.util.List;

public class AuthorAdapter extends BaseAdapter {
    private List<AuthorModel> authorModelList;

    public AuthorAdapter(List<AuthorModel> authorModelList) {
        this.authorModelList = authorModelList;
    }

    @Override
    public int getCount() {
        return authorModelList.size();
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.author_gridview_layout,null);
            view.setElevation(0);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent bookDetailsIntent = new Intent(parent.getContext(), AuthorActivity.class);
                    bookDetailsIntent.putExtra("author_name",authorModelList.get(position).getAuthorName());
                    parent.getContext().startActivity(bookDetailsIntent);
                }
            });
            ImageView authorImage = view.findViewById(R.id.author_image);
            TextView authorname = view.findViewById(R.id.author_name);
            authorname.setText(authorModelList.get(position).getAuthorName());
            Glide.with(parent.getContext()).load("http://upload.wikimedia.org/wikipedia/commons/9/96/Nguyen_Nhat_Anh.jpg")
                    .apply(new RequestOptions().placeholder(R.mipmap.sachtienganh)).into(authorImage);

        }else{
            view = convertView;
        }
        return view;
    }
}
