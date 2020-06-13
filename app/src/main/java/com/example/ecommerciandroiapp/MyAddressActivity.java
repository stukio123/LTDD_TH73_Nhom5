package com.example.ecommerciandroiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerciandroiapp.Adapter.AddressAdapter;
import com.example.ecommerciandroiapp.Database.DataBaseQueries;
import com.example.ecommerciandroiapp.Model.AddressModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.ecommerciandroiapp.DeliveryActivity.SELECT_ADDRESS;


public class MyAddressActivity extends AppCompatActivity {

    private int preSelectedAddress;
    private LinearLayout addNewAddressBtn;
    private RecyclerView myAddressesRecyclerView;
    private static AddressAdapter adapter;
    private Button deliverHereBtn;
    private TextView addressesSaved;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Số địa chỉ");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        loadingDialog = new Dialog(this);
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(R.layout.loading_progress_layout);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        preSelectedAddress = DataBaseQueries.selectedAddress;

        myAddressesRecyclerView = findViewById(R.id.addresses_recycler_view);
        deliverHereBtn = findViewById(R.id.deliver_here_btn);
        addNewAddressBtn = findViewById(R.id.add_new_address_btn);
        addressesSaved = findViewById(R.id.address_saved);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyAddressActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressesRecyclerView.setLayoutManager(linearLayoutManager);

        int mode = getIntent().getIntExtra("MODE",-1);
        if(mode == SELECT_ADDRESS){
            deliverHereBtn.setVisibility(View.VISIBLE);
        }else{
            deliverHereBtn.setVisibility(View.GONE);
        }

        deliverHereBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(DataBaseQueries.selectedAddress != preSelectedAddress) {
                    final int preAddressIndex = preSelectedAddress;
                    loadingDialog.show();
                    Map<String, Object> updateSelection = new HashMap<>();
                    updateSelection.put("selected_" + String.valueOf(preSelectedAddress + 1), false);
                    updateSelection.put("selected_" + String.valueOf(DataBaseQueries.selectedAddress + 1), false);
                    preSelectedAddress = DataBaseQueries.selectedAddress;
                    if (DataBaseQueries.selectedAddress != preSelectedAddress) {
                        FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getUid())
                                .collection("user_data").document("my_addresses")
                                .update(updateSelection).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    finish();
                                } else {
                                    preSelectedAddress = preAddressIndex;
                                    String error = task.getException().getMessage();
                                    Toast.makeText(MyAddressActivity.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
                                }
                                loadingDialog.dismiss();
                            }
                        });
                    }else{
                        finish();
                    }
                //}
            }
        });

        adapter = new AddressAdapter(DataBaseQueries.addressModelList,mode);
        myAddressesRecyclerView.setAdapter(adapter);
        ((SimpleItemAnimator)myAddressesRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        adapter.notifyDataSetChanged();

        addNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addAddressIntent = new Intent(MyAddressActivity.this,AddAdressActivity.class);
                addAddressIntent.putExtra("INTENT","null");
                startActivity(addAddressIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        addressesSaved.setText(String.format("%s đã được thêm",DataBaseQueries.addressModelList.size()));
    }

    public static void refresh(int deSelect, int selected){
        adapter.notifyItemChanged(deSelect);
        adapter.notifyItemChanged(selected);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            if(DataBaseQueries.selectedAddress != preSelectedAddress){
                DataBaseQueries.addressModelList.get(DataBaseQueries.selectedAddress).setSelected(false);
                DataBaseQueries.addressModelList.get(preSelectedAddress).setSelected(true);
                DataBaseQueries.selectedAddress = preSelectedAddress;
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(DataBaseQueries.selectedAddress != preSelectedAddress){
            DataBaseQueries.addressModelList.get(DataBaseQueries.selectedAddress).setSelected(false);
            DataBaseQueries.addressModelList.get(preSelectedAddress).setSelected(true);
            DataBaseQueries.selectedAddress = preSelectedAddress;
        }
        super.onBackPressed();
    }
}