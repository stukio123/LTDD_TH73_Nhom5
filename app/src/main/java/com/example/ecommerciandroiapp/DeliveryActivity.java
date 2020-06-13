package com.example.ecommerciandroiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerciandroiapp.Adapter.CartAdapter;
import com.example.ecommerciandroiapp.Database.DataBaseQueries;
import com.example.ecommerciandroiapp.Model.CartItemModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.ecommerciandroiapp.MainActivity.showCart;


public class DeliveryActivity extends AppCompatActivity {

    private RecyclerView deliveryRecyclerview;
    private Button changeOrAddAddressBtn;
    public static final int SELECT_ADDRESS = 0;
    private TextView totalAmount;
    private TextView fullname;
    private TextView address;
    private TextView phone;
    private ConstraintLayout orderConfirmationLayout;
    private ImageView continueShoppingBtn;
    private TextView orderId;
    private TextView continueBtn;
    private ConstraintLayout deliveryLayout;

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
        orderConfirmationLayout = findViewById(R.id.order_confirmation_layout);
        continueShoppingBtn = findViewById(R.id.continue_shopping_btn);
        orderId = findViewById(R.id.order_id);
        continueBtn = findViewById(R.id.cart_continue_btn);
        deliveryLayout = findViewById(R.id.delivery_container);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerview.setLayoutManager(linearLayoutManager);

        CartAdapter adapter = new CartAdapter(DataBaseQueries.cartItemModelList,totalAmount,false);
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
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryLayout.setVisibility(View.GONE);
                Date date = new Date();
                String convertDate = date.toString().trim().replace(":","").replace(" ","").replace("/","");
                final String orderID = FirebaseAuth.getInstance().getUid()+convertDate;
                Map<String,Object> updateOrder = new HashMap<>();
                updateOrder.put("orderID",orderID);
                FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getUid()).collection("user_data")
                        .document("my_order").set(updateOrder).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            orderId.setText(String.format("Mã đơn hàng: %s",orderID));
                            orderConfirmationLayout.setVisibility(View.VISIBLE);
                            Map<String,Object> updateCart = new HashMap<>();
                            updateCart.put("list_size",(long) 0);
                            FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getUid()).collection("user_data")
                                    .document("my_cart").set(updateCart).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        DataBaseQueries.cartItemModelList.clear();
                                        DataBaseQueries.cartList.clear();
                                    }else{
                                        String error = task.getException().getMessage();
                                        Toast.makeText(DeliveryActivity.this,"Lỗi: "+error,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(DeliveryActivity.this,"Lỗi: "+error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        continueShoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderConfirmationLayout.setVisibility(View.GONE);
                showCart = false;
                Intent mainIntent = new Intent(DeliveryActivity.this,MainActivity.class);
                startActivity(mainIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fullname.setText(DataBaseQueries.addressModelList.get(DataBaseQueries.selectedAddress).getName());
        address.setText(DataBaseQueries.addressModelList.get(DataBaseQueries.selectedAddress).getAddress());
        phone.setText(DataBaseQueries.addressModelList.get(DataBaseQueries.selectedAddress).getPhone());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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