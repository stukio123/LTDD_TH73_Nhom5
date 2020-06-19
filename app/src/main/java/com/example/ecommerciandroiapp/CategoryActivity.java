package com.example.ecommerciandroiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.ecommerciandroiapp.Adapter.CategoryAdapter;
import com.example.ecommerciandroiapp.Adapter.WishListAdapter;
import com.example.ecommerciandroiapp.Model.CategoryModel;
import com.example.ecommerciandroiapp.Model.WishListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter adapter;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("categoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseFirestore = FirebaseFirestore.getInstance();
        categoryRecyclerView = findViewById(R.id.category_rc);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);
        final List<WishListModel> wishList = new ArrayList<>();
        final WishListAdapter adapter = new WishListAdapter(wishList, false);
        categoryRecyclerView.setAdapter(adapter);
        firebaseFirestore.collection("Books").whereEqualTo("category",title).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult()){
                        wishList.add(new WishListModel(documentSnapshot.getId()
                                ,documentSnapshot.getString("image")
                                , documentSnapshot.getString("title")
                                , documentSnapshot.getString("author")
                                , documentSnapshot.getLong("avg_rating")
                                , documentSnapshot.getLong("total_rating")
                                , documentSnapshot.getString("price")
                                , documentSnapshot.getString("cutted_price")));
                        //System.out.println(documentSnapshot.toString());
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(CategoryActivity.this,"Không tìm thấy sách",Toast.LENGTH_SHORT).show();
                }
                //String bookID,String bookImage, String bookTitle, String bookAuthor, long rating, long totalRating, String bookPrice, String cuttedPrice
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_book,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }else if(item.getItemId() == R.menu.search_book){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}