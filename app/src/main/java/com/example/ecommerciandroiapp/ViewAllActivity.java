package com.example.ecommerciandroiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.example.ecommerciandroiapp.Adapter.GridBookLayoutAdapter;
import com.example.ecommerciandroiapp.Adapter.WishListAdapter;
import com.example.ecommerciandroiapp.Model.HorizontalBookModel;
import com.example.ecommerciandroiapp.Model.WishListModel;

import java.util.ArrayList;
import java.util.List;


public class ViewAllActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridView gridView;
    public static List<WishListModel> wishListModelList;
    public static List<HorizontalBookModel> horizontalBookModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        gridView = findViewById(R.id.grid_view);

        int layout_code = getIntent().getIntExtra("layout_code",-1);
        if(layout_code == 0) {
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            WishListAdapter adapter = new WishListAdapter(wishListModelList, false);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else if(layout_code == 1){
            gridView.setVisibility(View.VISIBLE);
            GridBookLayoutAdapter gridBookLayoutAdapter = new GridBookLayoutAdapter(horizontalBookModelList);
            gridView.setAdapter(gridBookLayoutAdapter);
            gridBookLayoutAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}