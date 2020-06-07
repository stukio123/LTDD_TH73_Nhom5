package com.example.ecommerciandroiapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerciandroiapp.Model.RewardModel;
import com.example.ecommerciandroiapp.R;

import java.util.ArrayList;
import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.ViewHolder> {

    private List<RewardModel> rewardModelList = new ArrayList<>();

    public RewardAdapter(List<RewardModel> rewardModelList) {
        this.rewardModelList = rewardModelList;
    }

    @NonNull
    @Override
    public RewardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_items_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardAdapter.ViewHolder holder, int position) {
        String title = rewardModelList.get(position).getTile();
        String date = rewardModelList.get(position).getDate();
        String descript = rewardModelList.get(position).getDescript();
        holder.setData(title,date,descript);
    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title,date,descript;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.coupon_title);
            date = itemView.findViewById(R.id.coupon_date);
            descript = itemView.findViewById(R.id.coupon_description);
        }

        private void setData(String couponTitle,String expireDate,String couponDescription){
            title.setText(couponTitle);
            date.setText(String.format("Hết hạn %s",expireDate));
            descript.setText(couponDescription);

        }
    }
}
