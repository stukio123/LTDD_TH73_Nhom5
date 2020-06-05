package com.example.ecommerciandroiapp.Adapter;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerciandroiapp.Model.MyOrderModel;
import com.example.ecommerciandroiapp.OrderDetailActivity;
import com.example.ecommerciandroiapp.R;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    private List<MyOrderModel> myOrderModelList;

    public MyOrderAdapter(List<MyOrderModel> myOrderModelList) {
        this.myOrderModelList = myOrderModelList;
    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder holder, int position) {
        int resource = myOrderModelList.get(position).getBookimage();
        int rating = myOrderModelList.get(position).getRating();
        String title = myOrderModelList.get(position).getBookTitle();
        String deliveryDate = myOrderModelList.get(position).getDeliveryStatus();
        holder.setData(resource,title,deliveryDate,rating);
    }

    @Override
    public int getItemCount() {
        return myOrderModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView bookImage;
        private ImageView deliveryStatus;
        private TextView bookTitle;
        private TextView deliveryDate;
        private RatingBar ratingBar;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.book_image);
            bookTitle = itemView.findViewById(R.id.book_title);
            deliveryStatus = itemView.findViewById(R.id.order_status);
            deliveryDate = itemView.findViewById(R.id.order_delivered_date);
            ratingBar = itemView.findViewById(R.id.rating_now_container);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent orderDetailsIntent = new Intent(itemView.getContext(), OrderDetailActivity.class);
                    itemView.getContext().startActivity(orderDetailsIntent);
                    int rating = (int)ratingBar.getRating();
                    orderDetailsIntent.putExtra("rating",rating);
                }
            });
        }

        public void setData(int resource, String title, String date, int rating){
            bookImage.setImageResource(resource);
            bookTitle.setText(title);
            if(date.equals("Hủy")) {
                deliveryStatus.setImageTintList(ColorStateList.valueOf(itemView.getContext().getColor(R.color.red)));
            }else{
                deliveryStatus.setImageTintList(ColorStateList.valueOf(itemView.getContext().getColor(R.color.Success)));
            }
            deliveryDate.setText(date);
            ratingBar.setRating(rating);
        }

    }
}
