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
        String price = horizontalBookModelList.get(position).getBookPrice();
        String id = horizontalBookModelList.get(position).getBookID();
        holder.setData(id,resource,title,category,price);
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


        private void setData(final String id , String url, String title, String category, String price){
            Glide.with(itemView.getContext()).load(url).apply(new RequestOptions().placeholder(R.mipmap.sachtienganh)).into(productImage);
            productTitle.setText(title);
            productCategory.setText(category);
            productPrice.setText(formatPrice(price));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent bookDetailIntent = new Intent(itemView.getContext(), BookDetailActivity.class);
                    bookDetailIntent.putExtra("book_id",id);
                    itemView.getContext().startActivity(bookDetailIntent);
                }
            });
        }

        private String formatPrice(String price){
            int prices = 0;
            if(price != null)
                prices = Integer.parseInt(price);
            String Prices = String.format("%,d đ",prices);
            return Prices;
        }
    }
}
