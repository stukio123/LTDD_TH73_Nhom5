package com.example.ecommerciandroiapp.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerciandroiapp.BookAllActivity;
import com.example.ecommerciandroiapp.BookDetailActivity;
import com.example.ecommerciandroiapp.CategoryActivity;
import com.example.ecommerciandroiapp.Model.CategoryModel;
import com.example.ecommerciandroiapp.NotiActivity;
import com.example.ecommerciandroiapp.R;

import java.util.List;

public class NotiAdapter extends RecyclerView.Adapter<NotiAdapter.ViewHolder> {

    private List<CategoryModel> categoryModelList;
    public NotiAdapter(List<CategoryModel> categoryModelList){
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public NotiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotiAdapter.ViewHolder holder, int position) {
        String url = categoryModelList.get(position).getCategoryImage();
        String name = categoryModelList.get(position).getCategoryName();

        holder.setCategory(name);
        holder.setCategoryImage(url);
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView categoryImage;
        private TextView categoryName;
//        private Constraints background_categori;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.flash_name);
            categoryImage = itemView.findViewById(R.id.flash_image);
//            background_categori = itemView.findViewById(R.id.background_categori);
        }
        private void setCategoryImage(String url){
            if(!url.equals("null")){
                Glide.with(itemView.getContext()).load(url).apply(new RequestOptions().override(600, 200)).into(categoryImage);
            }

        }
        private void setCategory(final String name){
            categoryName.setText(name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent bookIntent = new Intent(view.getContext(), BookAllActivity.class);
                    view.getContext().startActivity(bookIntent);
                }
            });
        }
    }
}
