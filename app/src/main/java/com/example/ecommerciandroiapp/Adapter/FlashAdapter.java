package com.example.ecommerciandroiapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerciandroiapp.Model.CategoryModel;
import com.example.ecommerciandroiapp.R;

import java.util.List;

public class FlashAdapter extends RecyclerView.Adapter<FlashAdapter.ViewHolder> {

    private List<CategoryModel> categoryModelList;
    public FlashAdapter(List<CategoryModel> categoryModelList){
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public FlashAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flashsale_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlashAdapter.ViewHolder holder, int position) {
        String url = categoryModelList.get(position).getCategoryImage();
        String name = categoryModelList.get(position).getCategoryName();
        holder.setCategoryName(name);
        holder.setCategoryImage(url);
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView categoryImage;
        private TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.flash_name);
            categoryImage = itemView.findViewById(R.id.flash_image);

        }
        private void setCategoryImage(String url){
            if(!url.equals("null")){
                Glide.with(itemView.getContext()).load(url).apply(new RequestOptions().override(600, 200)).into(categoryImage);
            }

        }
        private void setCategoryName(String name){
            categoryName.setText(name);
        }


    }
}
