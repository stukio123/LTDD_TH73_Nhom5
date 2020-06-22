package com.example.ecommerciandroiapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerciandroiapp.Adapter.AuthorAdapter;
import com.example.ecommerciandroiapp.Adapter.WishListAdapter;
import com.example.ecommerciandroiapp.Model.AuthorModel;
import com.example.ecommerciandroiapp.Model.WishListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class SearchFragment extends Fragment {
    private Button searchButton;
    private TextView searchTextView;
    private RecyclerView searchView;
    private FirebaseFirestore firebaseFirestore;
    WishListAdapter adapter;
    private GridView author_gridview;
    List<WishListModel> s;
    private List<AuthorModel> authorModelList;
    private AuthorAdapter authorAdapter;
    String[] items;
    ArrayList<String> listItems = new ArrayList<>();
    ArrayAdapter<String> v;
    ListView listView;
    EditText editText;
    List<WishListModel> wishList;
    @Override
    public void onStart() {
        super.onStart();searchTextView.requestFocus();
        InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(searchTextView, 0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchButton = view.findViewById(R.id.search_btn);
        listView= view.findViewById(R.id.lv_search);
        searchTextView = view.findViewById(R.id.search_text);
        searchView = view.findViewById(R.id.search_recyclerview);
        //final String value = searchTextView.getText().toString();
        firebaseFirestore = FirebaseFirestore.getInstance();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        searchView.setLayoutManager(linearLayoutManager);
        wishList = new ArrayList<>();



        adapter = new WishListAdapter(wishList, false);

        firebaseFirestore.collection("Books").get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size() >0){
                                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                    listItems.add(documentSnapshot.getString("title"));

                                }

                            }else{
                                Toast.makeText(getContext(), "Không tìm thấy sách", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Không tìm thấy sách", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        initList();


        //listView.setVisibility(View.VISIBLE);
        searchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                SearchFragment.this.v.getFilter().filter(s);
                adapter.notifyDataSetChanged();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                        // searchTextView.setText(
                        //   listItems.get(position));
                        searchView.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                        String test =  (String) parent.getItemAtPosition(position);
                        searchTextView.setText(test);
                        adapter.notifyDataSetChanged();

                    }});

                searchTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchView.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                    }

                });
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                String test =  (String) parent.getItemAtPosition(position);
                searchTextView.setText(test);
                searchView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();



            }


        });


        searchView.setAdapter(adapter);



        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                wishList = new ArrayList<>();
                adapter = new WishListAdapter(wishList, false);
                adapter.notifyDataSetChanged();;
                searchView.setAdapter(adapter);

                if (searchTextView.getText().toString().equals("")){
                Toast.makeText(getContext(),"Tên sách không được bỏ trống",Toast.LENGTH_SHORT).show();
            }else {
                firebaseFirestore.collection("Books").whereEqualTo("title", searchTextView.getText().toString()).get().
                        addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if(task.getResult().size() >0){
                                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                            wishList.add(new WishListModel(documentSnapshot.getId()
                                                    , documentSnapshot.getString("image")
                                                    , documentSnapshot.getString("title")
                                                    , documentSnapshot.getString("author")
                                                    , documentSnapshot.getLong("avg_rating")
                                                    , documentSnapshot.getLong("total_rating")
                                                    , documentSnapshot.getString("price")
                                                    , documentSnapshot.getString("cutted_price")));
                                            adapter.notifyDataSetChanged();


                                        }

                                    }else{
                                        Toast.makeText(getContext(), "Không tìm thấy sách", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getContext(), "Không tìm thấy sách", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }


            }
        });



        searchView.setAdapter(adapter);
        adapter.notifyDataSetChanged();










        return view;
    }


    public void initList() {
//        items = new String[]{"Mắt Biếc", "Đắc Nhân Tâm","Hiện Tại Kiên Trì Tương Lai Cố","Tôi Là Bêtô"};
////        listItems.addAll(Arrays.asList(items));
        v = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(v);
    }
}
