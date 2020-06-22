package com.example.ecommerciandroiapp.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerciandroiapp.BookAllActivity;
import com.example.ecommerciandroiapp.Model.NotificationModel;
import com.example.ecommerciandroiapp.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<NotificationModel> notificationModelList;

    public NotificationAdapter(List<NotificationModel> notificationModelList) {
        this.notificationModelList = notificationModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String content = notificationModelList.get(position).getContent();
        String resource = notificationModelList.get(position).getImage();
       /// holder.setData(resource,content);
    }

    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView content;
        private ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.notification_content);
            image = itemView.findViewById(R.id.notification_image);
        }
        private void setData(int resource,String contents){
            content.setText(contents);
            image.setImageResource(resource);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent bookIntent = new Intent(itemView.getContext(), BookAllActivity.class);
                    itemView.getContext().startActivity(bookIntent);
                }
            });
        }
    }
}
