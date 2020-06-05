package com.example.ecommerciandroiapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerciandroiapp.Adapter.CategoryAdapter;
import com.example.ecommerciandroiapp.Adapter.HomePageAdapter;
import com.example.ecommerciandroiapp.Model.BookModel;
import com.example.ecommerciandroiapp.Model.CategoryModel;
import com.example.ecommerciandroiapp.Model.HomePageModel;
import com.example.ecommerciandroiapp.Model.HorizontalBookModel;
import com.example.ecommerciandroiapp.Model.SliderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();

    private RecyclerView categoryRecyclerView;
    private RecyclerView homePageRecyclerView;

    private CategoryAdapter categoryAdapter;
    private List<CategoryModel> categoryModelList;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseDatabase firebaseDatabase;
    private HomePageAdapter adapter;
    private List<HomePageModel> homePageModelList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home,container,false);
        firebaseFirestore = FirebaseFirestore.getInstance();

        homePageRecyclerView = view.findViewById(R.id.home_page_recycler_view);
        homePageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homePageRecyclerView.setHasFixedSize(true);

        homePageModelList = new ArrayList<>();
        adapter = new HomePageAdapter(homePageModelList);
        homePageRecyclerView.setAdapter(adapter);

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
                                sliderModelList.add(new SliderModel(documentSnapshot.get("banner_"+i).toString()));
                            }
                            homePageModelList.add(new HomePageModel(0,sliderModelList));
                        }else if(documentSnapshot.getLong("view_type") == 1){
                            List<HorizontalBookModel> horizontalBookModelList = new ArrayList<>();
                            long no_of_books = documentSnapshot.getLong("no_of_books");
                            for(int i = 1; i<= no_of_books+1;i++)
                            {
                                horizontalBookModelList.add(new HorizontalBookModel(documentSnapshot.getString("book_"+i)));

                            }
                            homePageModelList.add(new HomePageModel(1,documentSnapshot.get("title").toString(),horizontalBookModelList));
                        }else{}
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    String error = task.getException().getMessage();
                    Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                }
            }
        });
        homePageRecyclerView.setAdapter(adapter);
        return view;
    }

}
