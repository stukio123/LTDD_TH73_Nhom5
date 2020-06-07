package com.example.ecommerciandroiapp.Database;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ecommerciandroiapp.Adapter.CategoryAdapter;
import com.example.ecommerciandroiapp.Adapter.HomePageAdapter;
import com.example.ecommerciandroiapp.Adapter.WishListAdapter;
import com.example.ecommerciandroiapp.Model.CategoryModel;
import com.example.ecommerciandroiapp.Model.HomePageModel;
import com.example.ecommerciandroiapp.Model.HorizontalBookModel;
import com.example.ecommerciandroiapp.Model.SliderModel;
import com.example.ecommerciandroiapp.Model.WishListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DataBaseQueries {

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<>();
    public static List<HomePageModel> homePageModelList = new ArrayList<>();

    public static void loadCategories(final CategoryAdapter categoryAdapter, final Context context){
        categoryModelList = new ArrayList<>();
        firebaseFirestore.collection("Categories").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        categoryModelList.add(new CategoryModel(documentSnapshot.getString("thumbNail"),documentSnapshot.getString("categoryName")));
                    }
                    categoryAdapter.notifyDataSetChanged();
                }else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void loadFragmentData(final HomePageAdapter adapter, final Context context){
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
                                    List<WishListModel> viewAllBookList = new ArrayList<>();
                                    List<HorizontalBookModel> horizontalBookModelList = new ArrayList<>();
                                    long no_of_books = documentSnapshot.getLong("no_of_books");
                                    for(int i = 1; i<= no_of_books+1;i++)
                                    {
                                        long totalRatings ;
                                        long avgRatings;
                                        horizontalBookModelList.add(new HorizontalBookModel(documentSnapshot.getString("book_"+i+"_id"),
                                                documentSnapshot.getString("book_"+i),
                                                documentSnapshot.getString("book_"+i+"_title"),
                                                documentSnapshot.getString("book_"+i+"_category"),
                                                documentSnapshot.getString("book_"+i+"_price")));
                                        if(documentSnapshot.getLong("book_"+i+"_total_rating") == null){
                                            totalRatings = 0;
                                        }else
                                        if(documentSnapshot.getLong("book_"+i+"_avg_rating") == null){
                                            avgRatings = 0;
                                        }else {
                                            viewAllBookList.add(new WishListModel(documentSnapshot.getString("book_" + i)
                                                    , documentSnapshot.getString("book_" + i + "_title")
                                                    , documentSnapshot.getString("author")
                                                    , avgRatings = documentSnapshot.getLong("book_" + i + "_avg_rating")
                                                    , totalRatings = documentSnapshot.getLong("book_" + i + "_total_rating")
                                                    , documentSnapshot.getString("book_" + i + "_price")
                                                    , documentSnapshot.getString("book_" + i + "_cutted_price")));
                                        }
                                    }
                                    homePageModelList.add(new HomePageModel(1,documentSnapshot.getString("title"),horizontalBookModelList,viewAllBookList));

                                }else if(documentSnapshot.getLong("view_type") == 2){
                                    //List<WishListModel> viewAllBookList = new ArrayList<>();
                                    List<HorizontalBookModel> gridLayoutModelList = new ArrayList<>();
                                    long no_of_booksg = documentSnapshot.getLong("no_of_books");
                                    for(int i = 1; i<= no_of_booksg+1;i++)
                                    {
                                        gridLayoutModelList.add(new HorizontalBookModel(documentSnapshot.getString("book_"+i+"_id"),
                                                documentSnapshot.getString("book_"+i),
                                                documentSnapshot.getString("book_"+i+"_title"),
                                                documentSnapshot.getString("book_"+i+"_category"),
                                                documentSnapshot.getString("book_"+i+"_price")));
                                    }
                                    homePageModelList.add(new HomePageModel(2,documentSnapshot.getString("author"),gridLayoutModelList));
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
