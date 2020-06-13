package com.example.ecommerciandroiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ecommerciandroiapp.Adapter.CartAdapter;
import com.example.ecommerciandroiapp.Model.CartItemModel;

import java.util.ArrayList;
import java.util.List;


public class DeliveryActivity extends AppCompatActivity {

    private RecyclerView deliveryRecyclerview;
    private Button changeOrAddAddressBtn;
    public static final int SELECT_ADDRESS = 0;
    private TextView totalAmount;
    private TextView fullname;
    private TextView address;
    private TextView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Sổ địa chỉ");

        totalAmount = findViewById(R.id.total_prices);
        deliveryRecyclerview = findViewById(R.id.delivery_recycler_view);
        changeOrAddAddressBtn = findViewById(R.id.charge_or_add_address_btn);
        fullname = findViewById(R.id.fullname);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerview.setLayoutManager(linearLayoutManager);

        List<CartItemModel> cartItemModelList = new ArrayList<>();

        CartAdapter adapter = new CartAdapter(cartItemModelList,totalAmount);
        deliveryRecyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        changeOrAddAddressBtn.setVisibility(View.VISIBLE);

        changeOrAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAddressedIntent = new Intent(DeliveryActivity.this,MyAddressActivity.class);
                myAddressedIntent.putExtra("MODE",SELECT_ADDRESS);
                startActivity(myAddressedIntent);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}