package com.example.ecommerciandroiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.ecommerciandroiapp.Adapter.AddressAdapter;
import com.example.ecommerciandroiapp.Model.AddressModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.ecommerciandroiapp.DeliveryActivity.SELECT_ADDRESS;


public class MyAddressActivity extends AppCompatActivity {

    private RecyclerView myAddressesRecyclerView;
    private static AddressAdapter adapter;
    private Button deliverHereBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Số địa chỉ");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        myAddressesRecyclerView = findViewById(R.id.addresses_recycler_view);
        deliverHereBtn = findViewById(R.id.deliver_here_btn);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyAddressActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressesRecyclerView.setLayoutManager(linearLayoutManager);

        List<AddressModel> addressModelList = new ArrayList<>();
        addressModelList.add(new AddressModel("Phạm Trí Quang","86 Tản Đà, Phường 11, Quận 5, TP.Hồ Chí Minh","0913368752",true));
        addressModelList.add(new AddressModel("Phạm Trí Quang","86 Tản Đà, Phường 11, Quận 5, TP.Hồ Chí Minh","0913368752",false));
        addressModelList.add(new AddressModel("Phạm Trí Quang","86 Tản Đà, Phường 11, Quận 5, TP.Hồ Chí Minh","0913368752",false));
        addressModelList.add(new AddressModel("Phạm Trí Quang","86 Tản Đà, Phường 11, Quận 5, TP.Hồ Chí Minh","0913368752",false));
        addressModelList.add(new AddressModel("Phạm Trí Quang","86 Tản Đà, Phường 11, Quận 5, TP.Hồ Chí Minh","0913368752",false));

        int mode = getIntent().getIntExtra("MODE",-1);
        if(mode == SELECT_ADDRESS){
            deliverHereBtn.setVisibility(View.VISIBLE);
        }else{
            deliverHereBtn.setVisibility(View.GONE);
        }
        adapter = new AddressAdapter(addressModelList,mode);
        myAddressesRecyclerView.setAdapter(adapter);
        ((SimpleItemAnimator)myAddressesRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        adapter.notifyDataSetChanged();
    }

    public static void refresh(int deSelect, int selected){
        adapter.notifyItemChanged(deSelect);
        adapter.notifyItemChanged(selected);
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