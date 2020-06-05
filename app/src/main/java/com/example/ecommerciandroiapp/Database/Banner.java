package com.example.ecommerciandroiapp.Database;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ecommerciandroiapp.Adapter.HomePageAdapter;
import com.example.ecommerciandroiapp.Adapter.SliderAdapter;
import com.example.ecommerciandroiapp.Model.HomePageModel;
import com.example.ecommerciandroiapp.Model.SliderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Banner {
    private FirebaseFirestore firebaseFirestore;
    private SliderAdapter adapter;
    private List<SliderModel> sliderModelList;
    public Banner(FirebaseFirestore firebaseFirestore,
                  List<SliderModel> sliderModelList,SliderAdapter adapter) {
        this.firebaseFirestore = firebaseFirestore;
        this.adapter = adapter;
        this.sliderModelList = sliderModelList;
    }


    public void getBanner(){
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Banners").document("Deal")
                .collection("Banner").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult())
                            {
                                if(documentSnapshot.getLong("view_type") == 0){
                                    List<SliderModel> sliderModelList = new ArrayList<>();
                                    long no_of_banners = documentSnapshot.getLong("no_of_banners");
                                    for(int i = 1 ; i<no_of_banners+1; i++)
                                    {
                                        sliderModelList.add(new SliderModel(documentSnapshot.get("banner_"+ String.valueOf(i)).toString()));
                                    }
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(null,error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
