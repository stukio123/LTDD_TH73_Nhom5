package com.example.ecommerciandroiapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecommerciandroiapp.Adapter.CategoryAdapter;
import com.example.ecommerciandroiapp.Adapter.NotiAdapter;
import com.example.ecommerciandroiapp.Adapter.NotificationAdapter;
import com.example.ecommerciandroiapp.Adapter.RewardAdapter;
import com.example.ecommerciandroiapp.Model.CategoryModel;
import com.example.ecommerciandroiapp.Model.NotificationModel;
import com.example.ecommerciandroiapp.Model.RewardModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    public NotificationFragment() {
        // Required empty public constructor
    }
    private RecyclerView notificationRecyclerView;
    private FirebaseFirestore firebaseFirestore;
    List<NotificationModel> notificationModelList;
    private NotiAdapter adapter;
    public static final String TAG = HomeFragment.class.getSimpleName();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        notificationRecyclerView = view.findViewById(R.id.notification_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        notificationRecyclerView.setLayoutManager(linearLayoutManager);

       notificationModelList = new ArrayList<NotificationModel>();
        adapter = new NotiAdapter(notificationModelList);
        notificationRecyclerView.setAdapter(adapter);
//        notificationModelList.add(new NotificationModel("aaaaaaaaaaaa",R.drawable.ic_person_black_24dp));
//        notificationModelList.add(new NotificationModel("aaaaaaaaaaaa",R.drawable.ic_person_black_24dp));
//        notificationModelList.add(new NotificationModel("aaaaaaaaaaaa",R.drawable.ic_person_black_24dp));
//        notificationModelList.add(new NotificationModel("aaaaaaaaaaaa",R.drawable.ic_person_black_24dp));
//        notificationModelList.add(new NotificationModel("aaaaaaaaaaaa",R.drawable.ic_person_black_24dp));
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("notification").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult())
                            {
                                try {
                                    notificationModelList.add(new NotificationModel(documentSnapshot.get("Thongtin").toString(),
                                            documentSnapshot.get("Banner").toString(), documentSnapshot.get("chitet").toString()));
                                }catch (Exception e)
                                {
                                    Log.e(TAG,"Lỗi notifi: "+e.getMessage());
                                }
                                adapter.notifyDataSetChanged();
                            }
                            adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });



        notificationRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }
}
