package com.example.ecommerciandroiapp.Database;

import androidx.annotation.NonNull;

import com.example.ecommerciandroiapp.Model.BookModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Book extends BookModel {
    private FirebaseFirestore firebaseFirestore;
    private FirebaseDatabase firebaseDatabase;
    private List<BookModel> bookModelList;

    public Book() {
    }

    public List<BookModel> getBookModelList() {
        bookModelList = new ArrayList<>();

        firebaseFirestore.collection("Books").orderBy("isbn").limit(50).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        BookModel book = new BookModel(documentSnapshot.getString("author"), documentSnapshot.getString("category")
                                , documentSnapshot.getString("description"), documentSnapshot.getString("title"), documentSnapshot.getString("image")
                                , (int) documentSnapshot.get("isbn"), (int) documentSnapshot.get("price"), (int) documentSnapshot.get("sku"));
                        bookModelList.add(book);
                    }
                }
            }
        });
        return bookModelList;
    }

    public List<BookModel> getBookModelList(String isbn){
        bookModelList = new ArrayList<>();
        //firebaseFirestore = FirebaseFirestore.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Book");
        Query query = ref.orderByChild("isbn").equalTo(isbn);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        BookModel bookModel = snapshot.getValue(BookModel.class);
                        bookModelList.add(bookModel);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        return bookModelList;
    }
    //35785372

    /*public static void main(String[] args) {
        System.out.println(getBookModelList("35785372").toString());
    }*/
}
