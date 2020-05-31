package com.example.ecommerciandroiapp.Database;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ecommerciandroiapp.Adapter.CategoryAdapter;
import com.example.ecommerciandroiapp.Adapter.FlashAdapter;
import com.example.ecommerciandroiapp.HomeFragment;
import com.example.ecommerciandroiapp.Model.CategoryModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Flash {
    public static final String TAG = HomeFragment.class.getSimpleName();
    private FirebaseFirestore firebaseFirestore;
    private List<CategoryModel> categoryModelList;
    private FlashAdapter categoryAdapter;
    public Flash(FirebaseFirestore firebaseFirestore, List<CategoryModel> categoryModelList,
                 FlashAdapter categoryAdapter){
        this.categoryAdapter =categoryAdapter;
        this.categoryModelList = categoryModelList;
        this.firebaseFirestore = firebaseFirestore;
    }

    public void getCategory(){
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Categories").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult())
                            {
                                try {
                                    categoryModelList.add(new CategoryModel(documentSnapshot.get("thumbNail").toString(),
                                            documentSnapshot.get("categoryName").toString()));
                                }catch (Exception e)
                                {
                                    Log.e(TAG,"Lỗi category: "+e.getMessage());
                                }

                            }
                            categoryAdapter.notifyDataSetChanged();
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
