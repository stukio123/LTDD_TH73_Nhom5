package com.example.ecommerciandroiapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerciandroiapp.Adapter.WishListAdapter;
import com.example.ecommerciandroiapp.Model.WishListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchFragment extends Fragment {
    private Button searchButton;
    private TextView searchTextView;
    private RecyclerView searchView;
    private FirebaseFirestore firebaseFirestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchButton = view.findViewById(R.id.search_btn);
        searchTextView = view.findViewById(R.id.search_text);
        searchView = view.findViewById(R.id.search_recyclerview);
        //final String value = searchTextView.getText().toString();
        firebaseFirestore = FirebaseFirestore.getInstance();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        searchView.setLayoutManager(linearLayoutManager);
        final List<WishListModel> wishList = new ArrayList<>();
        final WishListAdapter adapter = new WishListAdapter(wishList, false);
        searchView.setAdapter(adapter);
        if (!searchTextView.getText().equals("")){
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebaseFirestore.collection("Books").whereEqualTo("title", searchTextView.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                    wishList.add(new WishListModel(documentSnapshot.getId()
                                            , documentSnapshot.getString("image")
                                            , documentSnapshot.getString("title")
                                            , documentSnapshot.getString("author")
                                            , documentSnapshot.getLong("avg_rating")
                                            , documentSnapshot.getLong("total_rating")
                                            , documentSnapshot.getString("price")
                                            , documentSnapshot.getString("cutted_price")));
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "Không tìm thấy sách", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
        return view;
    }
}
