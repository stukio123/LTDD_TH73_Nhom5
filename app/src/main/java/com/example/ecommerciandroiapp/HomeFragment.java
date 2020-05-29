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
        // Khởi tạo Danh mục (Category)
        categoryRecyclerView = view.findViewById(R.id.category_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        categoryModelList  = new ArrayList<CategoryModel>();
        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);

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
                            Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        categoryRecyclerView.setAdapter(categoryAdapter);

        //Test HomeRecycler
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
                            /*List<HorizontalBookModel> horizontalBookModelList = new ArrayList<>();
                            long no_of_books = documentSnapshot.getLong("no_of_books");
                            for(int i = 1; i< no_of_books;i++)
                            {
                                String isbn = documentSnapshot.getString("book_"+i);
                                horizontalBookModelList.add(new HorizontalBookModel(fetchBooks(isbn)));
                            }
                            homePageModelList.add(new HomePageModel(1,documentSnapshot.get("title").toString(),horizontalBookModelList));*/
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

    /*private BookModel fetchBooks(String isbn){
        final BookModel book = new BookModel();
        firebaseFirestore.collection("Books").orderBy("isbn").whereEqualTo("isbn",isbn).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult())
                    {
                        try {
                            book.setSku((int)documentSnapshot.get("sku"));
                            book.setIsbn((int)documentSnapshot.get("isbn"));
                            book.setAuthor(documentSnapshot.get("author").toString());
                            //book.setDescription(documentSnapshot.get("description").toString());
                            book.setTitle(documentSnapshot.get("title").toString());
                            book.setImageURL(documentSnapshot.get("image").toString());

                        }catch (Exception e)
                        {
                            Log.e(TAG,"Lỗi category: "+e.getMessage());
                        }

                    }
                }else{

                }
            }
        });

        return book;
    }*/
}
