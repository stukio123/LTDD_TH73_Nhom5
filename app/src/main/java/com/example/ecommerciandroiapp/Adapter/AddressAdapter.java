package com.example.ecommerciandroiapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerciandroiapp.Database.DataBaseQueries;
import com.example.ecommerciandroiapp.Model.AddressModel;
import com.example.ecommerciandroiapp.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.ecommerciandroiapp.DeliveryActivity.SELECT_ADDRESS;
import static com.example.ecommerciandroiapp.MyAddressActivity.refresh;
import static com.example.ecommerciandroiapp.UserFragment.MANAGE_ADDRESS;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private List<AddressModel> addressModelList = new ArrayList<>();
    private int MODE ;
    private int preSelectedPosition;

    public AddressAdapter(List<AddressModel> addressModelList, int mode) {
        this.addressModelList = addressModelList;
        this.MODE = mode;
        preSelectedPosition = DataBaseQueries.selectedAddress;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addressed_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, int position) {
        String name = addressModelList.get(position).getName();
        String address = addressModelList.get(position).getAddress();
        String phone = addressModelList.get(position).getPhone();
        Boolean selected = addressModelList.get(position).isSelected();
        holder.setData(name,address,phone,selected,position);
    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name,address,phone;
        private ImageView icon;
        private LinearLayout optionsContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            phone = itemView.findViewById(R.id.phone);
            icon = itemView.findViewById(R.id.icon_view);
            optionsContainer = itemView.findViewById(R.id.options_container);
        }

        private void setData(String userName, String userAddress, String userPhone, Boolean selected, final int position){
            name.setText(userName);
            address.setText(userAddress);
            phone.setText(userPhone);

            if(MODE == SELECT_ADDRESS){
                icon.setImageResource(R.drawable.ic_baseline_check_24);
                if(selected){
                    icon.setVisibility(View.VISIBLE);
                    preSelectedPosition = position;
                }
                else {
                    icon.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(preSelectedPosition != position) {
                            addressModelList.get(position).setSelected(true);
                            addressModelList.get(preSelectedPosition).setSelected(false);
                            refresh(preSelectedPosition, position);
                            preSelectedPosition = position;
                            DataBaseQueries.selectedAddress = position;
                        }
                    }
                });
            }else if(MODE == MANAGE_ADDRESS){
                optionsContainer.setVisibility(View.GONE);
                icon.setImageResource(R.drawable.vertical_dot);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Icon được click");
                        optionsContainer.setVisibility(View.VISIBLE);
                        //refresh(preSelectedPosition,position);
                        preSelectedPosition = position;
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //refresh(preSelectedPosition,preSelectedPosition);
                        preSelectedPosition = -1;
                    }
                });
            }
        }
    }
}
