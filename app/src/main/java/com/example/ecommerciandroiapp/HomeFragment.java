package com.example.ecommerciandroiapp;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerciandroiapp.Adapter.CategoryAdapter;
import com.example.ecommerciandroiapp.Adapter.FlashAdapter;
import com.example.ecommerciandroiapp.Adapter.HomePageAdapter;
import com.example.ecommerciandroiapp.Database.Banner;
import com.example.ecommerciandroiapp.Database.Category;
import com.example.ecommerciandroiapp.Database.Flash;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();
    //DANH MỤC
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private List<CategoryModel> categoryModelList;

    //DỮ LIỆU
    private FirebaseFirestore firebaseFirestore;
    private FirebaseDatabase firebaseDatabase;
    //BANNER
    private HomePageAdapter adapter;
    private RecyclerView homePageRecyclerView;
    private List<HomePageModel> homePageModelList;

    //FLASH SALE
    private String EVENT_DATE_TIME = "2020-06-03 10:30:00";
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private LinearLayout linearlayoutFlashSale;
    private TextView txt_gio, txt_phut, txt_giay;
    private Handler handler = new Handler();
    private Runnable runnable;


    ///////////////////////////////////////////ĐANG CHỜ LOAD ITEM SÁCH ĐỂ SỬA LẠI
    //----SACH FLASH SALE
    private RecyclerView flashRecyclerView;
    private FlashAdapter flashAdapter;
    private List<CategoryModel> flashModelList;

    //== SÁCH HOT
    private RecyclerView recyclerView;
    private FlashAdapter bookFlashAdapter;
    private List<CategoryModel> bookflashModelList;
    /////////////////////////////////////////////////


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home,container,false);
        // Khởi tạo Danh mục (Category)
        categoryRecyclerView = view.findViewById(R.id.category_recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        categoryModelList  = new ArrayList<CategoryModel>();
        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);

        Category category = new Category(firebaseFirestore,categoryModelList,categoryAdapter);
        category.getCategory();
        categoryRecyclerView.setAdapter(categoryAdapter);

        // Khởi tạo Danh mục (Category)  ((((( THU GIAO DIEN CHO FLASH)))))))
        flashRecyclerView = view.findViewById(R.id.flashsale_recyclerview);
        LinearLayoutManager layoutManagerFLASH = new LinearLayoutManager(getActivity());
        layoutManagerFLASH.setOrientation(LinearLayoutManager.HORIZONTAL);
        flashRecyclerView.setLayoutManager(layoutManagerFLASH);

        flashModelList  = new ArrayList<CategoryModel>();
        flashAdapter = new FlashAdapter(flashModelList);
        flashRecyclerView.setAdapter(flashAdapter);

        Flash flash = new Flash(firebaseFirestore,flashModelList,flashAdapter);
        flash.getCategory();
        flashRecyclerView.setAdapter(flashAdapter);


        recyclerView = view.findViewById(R.id.re);
        GridLayoutManager layoutManagerre = new GridLayoutManager(getActivity(),2);
        layoutManagerre.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManagerre);
        bookflashModelList  = new ArrayList<CategoryModel>();
        bookFlashAdapter = new FlashAdapter(bookflashModelList);
        recyclerView.setAdapter(bookFlashAdapter);
        Flash flashbook = new Flash(firebaseFirestore,bookflashModelList,bookFlashAdapter);
        flashbook.getCategory();
        flashbook.getCategory();
        flashbook.getCategory();
        flashbook.getCategory();
        flashbook.getCategory();
        flashbook.getCategory();
        recyclerView.setAdapter(bookFlashAdapter);


        //Test HomeRecycler
        homePageRecyclerView = view.findViewById(R.id.home_page_recycler_view);
        homePageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homePageRecyclerView.setHasFixedSize(true);

        homePageModelList = new ArrayList<HomePageModel>();
        adapter = new HomePageAdapter(homePageModelList);
        homePageRecyclerView.setAdapter(adapter);

        Banner banner = new Banner(firebaseFirestore, homePageModelList, adapter);
        banner.getBanner();

        homePageRecyclerView.setAdapter(adapter);

        //FLASH SALE --- DEM THOI GIAN
        linearlayoutFlashSale = view.findViewById(R.id.linearlayoutFlashSale);
        txt_gio = view.findViewById(R.id.txt_gio);
        txt_phut = view.findViewById(R.id.txt_phut);
        txt_giay = view.findViewById(R.id.txt_giay);
        countDownStart();
        //KẾT THÚC FLASH SALE


        return view;
    }



    // FLASH SALE ----- Đếm ngược thời gian
    private void countDownStart() {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date event_date = dateFormat.parse(EVENT_DATE_TIME);
                    Date current_date = new Date();
                    if (!current_date.after(event_date)) {
                        long diff = event_date.getTime() - current_date.getTime();
//                        long Days = diff / (24 * 60 * 60 * 1000);
                        long Hours = diff / (24 * 60 * 60 * 1000)*24 + diff / (60 * 60 * 1000) % 24 ;
                        long Minutes = diff / (60 * 1000) % 60;
                        long Seconds = diff / 1000 % 60;
                        //
                        txt_gio.setText(String.format("%02d", Hours));
                        txt_phut.setText(String.format("%02d", Minutes));
                        txt_giay.setText(String.format("%02d", Seconds));

                        linearlayoutFlashSale.setVisibility(View.VISIBLE);
                    } else {

                        handler.removeCallbacks(runnable);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
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
