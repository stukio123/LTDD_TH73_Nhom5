package com.example.ecommerciandroiapp.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerciandroiapp.BookDetailActivity;
import com.example.ecommerciandroiapp.Model.HorizontalBookModel;
import com.example.ecommerciandroiapp.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HorizontalBookAdapter extends RecyclerView.Adapter<HorizontalBookAdapter.ViewHolder> {
    private List<HorizontalBookModel> horizontalBookModelList;

    public HorizontalBookAdapter(List<HorizontalBookModel> horizontalBookModelList) {
        this.horizontalBookModelList = horizontalBookModelList;
    }

    @NonNull
    @Override
    public HorizontalBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalBookAdapter.ViewHolder holder, int position) {
        String resource = horizontalBookModelList.get(position).getBookImage();
        String title = horizontalBookModelList.get(position).getBookTitle();
        String category = horizontalBookModelList.get(position).getBookCategory();
        int price = horizontalBookModelList.get(position).getBookPrice();

        holder.setProductImage(resource);
        holder.setProductTitle(title);
        holder.setProductCategory(category);
        holder.setProductPrice(price);
    }

    @Override
    public int getItemCount() {
        if(horizontalBookModelList.size() > 8)
        {
            return 8;
        }else {
            return horizontalBookModelList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle,productCategory,productPrice;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.h_imageBook);
            productTitle = itemView.findViewById(R.id.h_titleBook);
            productCategory = itemView.findViewById(R.id.h_categoryBook);
            productPrice = itemView.findViewById(R.id.h_priceBook);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent bookDetailIntent = new Intent(itemView.getContext(), BookDetailActivity.class);
                    itemView.getContext().startActivity(bookDetailIntent);
                }
            });
        }

        private void setProductImage(String url){
            Glide.with(itemView.getContext()).load(url).apply(new RequestOptions().override(800,600)).into(productImage);
        }
        private void setProductTitle(String title){
            productTitle.setText(title);
        }
        private void setProductCategory(String category){
            productCategory.setText(category);
        }
        private void setProductPrice(int price){
            int prices = price;
            String Prices = String.format("%,d đ",prices);
            productPrice.setText(Prices);
        }
    }
}
