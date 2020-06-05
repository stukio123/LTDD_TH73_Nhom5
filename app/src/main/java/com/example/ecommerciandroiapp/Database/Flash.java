package com.example.ecommerciandroiapp.Database;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ecommerciandroiapp.Adapter.CategoryAdapter;
import com.example.ecommerciandroiapp.Adapter.FlashAdapter;
import com.example.ecommerciandroiapp.Adapter.HorizontalBookAdapter;
import com.example.ecommerciandroiapp.HomeFragment;
import com.example.ecommerciandroiapp.Model.BookModel;
import com.example.ecommerciandroiapp.Model.CategoryModel;
import com.example.ecommerciandroiapp.Model.HorizontalBookModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Flash {
    public static final String TAG = HomeFragment.class.getSimpleName();
    private FirebaseFirestore firebaseFirestore;
    private List<CategoryModel> horizontalBookModelList;
    private FlashAdapter horizontalBookAdapter;
    public Flash(FirebaseFirestore firebaseFirestore, List<CategoryModel> categoryModelList,
                 FlashAdapter categoryAdapter){
        this.horizontalBookAdapter =categoryAdapter;
        this.horizontalBookModelList = categoryModelList;
        this.firebaseFirestore = firebaseFirestore;
    }

    public void getCategory(){
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Books").orderBy("isbn").limit(10).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult())
                            {
                                try {
                                    BookModel book = new BookModel(documentSnapshot.getString("author")
                                            , documentSnapshot.getString("category"),documentSnapshot.getString("description")
                                    ,documentSnapshot.getString("title"),documentSnapshot.getString("image"),(int)documentSnapshot.get("isbn")
                                    ,(int)documentSnapshot.get("price"),(int)documentSnapshot.get("sku"));

                                    horizontalBookModelList.add(new CategoryModel(book.getImageURL(),book.getTitle()));
                                }catch (Exception e)
                                {
                                    Log.e(TAG,"Lỗi category: "+e.getMessage());
                                }

                            }
                            horizontalBookAdapter.notifyDataSetChanged();
                        }
                        else
                        {
                            String error = task.getException().getMessage();
                            Toast.makeText(null,error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
