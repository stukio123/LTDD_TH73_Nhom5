package com.example.ecommerciandroiapp.Database;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ecommerciandroiapp.Adapter.CategoryAdapter;
import com.example.ecommerciandroiapp.Adapter.HomePageAdapter;
import com.example.ecommerciandroiapp.AddAdressActivity;
import com.example.ecommerciandroiapp.BookDetailActivity;
import com.example.ecommerciandroiapp.DeliveryActivity;
import com.example.ecommerciandroiapp.MainActivity;
import com.example.ecommerciandroiapp.Model.AddressModel;
import com.example.ecommerciandroiapp.Model.AuthorModel;
import com.example.ecommerciandroiapp.Model.CartItemModel;
import com.example.ecommerciandroiapp.Model.CategoryModel;
import com.example.ecommerciandroiapp.Model.HomePageModel;
import com.example.ecommerciandroiapp.Model.HorizontalBookModel;
import com.example.ecommerciandroiapp.Model.SliderModel;
import com.example.ecommerciandroiapp.Model.WishListModel;
import com.example.ecommerciandroiapp.MyCartFragment;
import com.example.ecommerciandroiapp.MyWishListFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.ecommerciandroiapp.HomeFragment.refreshLayout;
import static java.lang.Long.getLong;

public class DataBaseQueries {

    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<>();
    public static List<HomePageModel> homePageModelList = new ArrayList<>();
    public static List<String> wishList = new ArrayList<>();
    public static List<WishListModel> wishListModelList = new ArrayList<>();
    public static List<String> myRateIds = new ArrayList<>();
    public static List<Long> myRating = new ArrayList<>();
    public static List<String> cartList = new ArrayList<>();
    public static List<CartItemModel> cartItemModelList = new ArrayList<>();
    public static List<AddressModel> addressModelList = new ArrayList<>();
    public static int selectedAddress = -1 ;



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

    public static void loadFragmentData( final HomePageAdapter adapter, final Context context){
        homePageModelList.clear();
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
                                            viewAllBookList.add(new WishListModel(documentSnapshot.getString("book_"+i+"_id"),documentSnapshot.getString("book_" + i)
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
                                    List<WishListModel> viewAllBookList = new ArrayList<>();
                                    List<HorizontalBookModel> gridLayoutModelList = new ArrayList<>();
                                    long no_of_booksg = documentSnapshot.getLong("no_of_books");
                                    for(int i = 1; i<= no_of_booksg+1;i++)
                                    {
                                        long totalRatings ;
                                        long avgRatings;
                                        gridLayoutModelList.add(new HorizontalBookModel(documentSnapshot.getString("book_"+i+"_id"),
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
                                            viewAllBookList.add(new WishListModel(documentSnapshot.getString("book_"+i+"_id"),documentSnapshot.getString("book_" + i)
                                                    , documentSnapshot.getString("book_" + i + "_title")
                                                    , documentSnapshot.getString("author")
                                                    , avgRatings = documentSnapshot.getLong("book_" + i + "_avg_rating")
                                                    , totalRatings = documentSnapshot.getLong("book_" + i + "_total_rating")
                                                    , documentSnapshot.getString("book_" + i + "_price")
                                                    , documentSnapshot.getString("book_" + i + "_cutted_price")));
                                        }
                                    }
                                    homePageModelList.add(new HomePageModel(2,documentSnapshot.getString("author"),gridLayoutModelList,viewAllBookList));
                                }/*else if(documentSnapshot.getLong("view_type") == 3){

                                }*/
                            }
                            adapter.notifyDataSetChanged();
                            refreshLayout.setRefreshing(false);
                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadWishList(final Context context, final Dialog dialog, final boolean loadBookData){
        wishList.clear();
        firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("user_data")
                .document("my_wishList").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    for(long i = 0; i < (long) Objects.requireNonNull(task.getResult()).get("list_size"); i++){
                        wishList.add(task.getResult().get("book_id_"+i).toString());
                        if(wishList.contains(BookDetailActivity.bookID)){
                            BookDetailActivity.ADDED_TO_WISHLIST = true;
                        }else{
                            BookDetailActivity.ADDED_TO_WISHLIST = false;
                        }
                        if(loadBookData){
                            wishListModelList.clear();
                            firebaseFirestore.collection("Books").document(task.getResult().getString("book_id_"+i)).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()){
                                                wishListModelList.add(new WishListModel(task.getResult().getId(),task.getResult().getString("image")
                                                        , task.getResult().getString("title")
                                                        , task.getResult().getString("author")
                                                        , task.getResult().getLong("avg_rating")
                                                        , task.getResult().getLong("total_rating")
                                                        , task.getResult().getString("price")
                                                        , task.getResult().getString("cutted_price")));
                                                MyWishListFragment.adapter.notifyDataSetChanged();
                                            }else{
                                                String error = task.getException().getMessage();
                                                Toast.makeText(context,"Lỗi: "+error,Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                }else{
                    String error = task.getException().getMessage();
                    Toast.makeText(context,"Lỗi: "+error,Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }

    public static void removeWishList(final int index, final Context context){
        final String removeBookID = wishList.get(index);
        wishList.remove(index);
        Map<String,Object> updateWishList = new HashMap<>();
        for (int i = 0 ; i <wishList.size(); i++){
            updateWishList.put("book_id_"+i,wishList.get(i));
        }
        updateWishList.put("list_size",(long)wishList.size());
        firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("user_data").document("my_wishList")
                .set(updateWishList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(wishListModelList.size() != 0){
                        wishListModelList.remove(index);
                        MyWishListFragment.adapter.notifyDataSetChanged();
                    }
                    BookDetailActivity.ADDED_TO_WISHLIST = false;
                    Toast.makeText(context,"Xóa khỏi danh sách yêu thích thành công",Toast.LENGTH_LONG).show();
                }else{
                    if(BookDetailActivity.addToWishListbtn != null){
                        BookDetailActivity.addToWishListbtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                    }
                    wishList.add(index,removeBookID);
                    String error = task.getException().getMessage();
                    Toast.makeText(context,"Lỗi: "+error,Toast.LENGTH_SHORT).show();
                }
                BookDetailActivity.RUNNING_WISHLIST_QUERY = false;
            }
        });
    }

    public static void loadRatingList(final Context context){
        if(!BookDetailActivity.RUNNING_RATING_QUERY) {
            BookDetailActivity.RUNNING_RATING_QUERY = true;
            myRateIds.clear();
            myRating.clear();
            firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getUid())
                    .collection("user_data").document("my_ratings").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        for (long i = 0; i < Objects.requireNonNull(task.getResult()).getLong("list_size"); i++) {
                            myRateIds.add(task.getResult().get("book_id_" + i).toString());
                            myRating.add(task.getResult().getLong("rating_" + i));
                            if (task.getResult().getString("book_id_" + i).equals(BookDetailActivity.bookID)) {
                                BookDetailActivity.yours_rating.setRating(task.getResult().getLong("rating_" + i));
                                //BookDetailActivity.currentRatings = (int)BookDetailActivity.yours_rating.getRating();
                            }
                        }
                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(context, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
                    }
                    BookDetailActivity.RUNNING_RATING_QUERY = false;
                }
            });
        }
    }

    public static void loadCartList(final Context context, final Dialog dialog , final boolean loadBookData,final TextView badgeCount){
        cartList.clear();
        firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("user_data")
                .document("my_cart").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    for(long i = 0; i <(long)task.getResult().get("list_size"); i++){
                        cartList.add(task.getResult().get("book_id_"+i).toString());
                        if(cartList.contains(BookDetailActivity.bookID)){
                            BookDetailActivity.ADDED_TO_CART = true;
                        }else{
                            BookDetailActivity.ADDED_TO_CART = false;
                        }
                        if(loadBookData){
                            cartItemModelList.clear();
                            final String bookid = task.getResult().getString("book_id_"+i);
                            firebaseFirestore.collection("Books").document(bookid).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()){
                                                int index = 0;
                                                /*if(cartList.size() >= 2){
                                                    index = cartList.size()-2;
                                                }*/
                                                //int type ,String bookID, String bookImage, String bookTitle, String bookPrice, String bookPublisher, String cuttedPrice, long bookQuantity
                                                cartItemModelList.add(index,new CartItemModel(CartItemModel.CART_ITEM
                                                        ,task.getResult().getId()
                                                        ,task.getResult().getString("image")
                                                        , task.getResult().getString("title")
                                                        , task.getResult().getString("price")
                                                        , task.getResult().getString("publisher")
                                                        , task.getResult().getString("cutted_price")
                                                        , (long)1));
                                                if(cartList.size() == 0){
                                                    cartItemModelList.clear();
                                                }
                                                cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
                                                MyCartFragment.cartAdapter.notifyDataSetChanged();
                                            }else{
                                                String error = task.getException().getMessage();
                                                Toast.makeText(context,"Lỗi: "+error,Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                    /*if(cartList.size() != 0){
                        badgeCount.setVisibility(View.VISIBLE);
                    }else{
                        badgeCount.setVisibility(View.INVISIBLE);
                    }
                    if(DataBaseQueries.cartList.size() < 99 ){
                        badgeCount.setText(String.valueOf(DataBaseQueries.cartList.size()));
                    }else{
                        badgeCount.setText("99");
                    }*/
                }else{
                    String error = task.getException().getMessage();
                    Toast.makeText(context,"Lỗi: "+error,Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }

    public static void removeCartList(final int index, final Context context){
        final String removeBookID = cartList.get(index);
        cartList.remove(index);
        Map<String,Object> updateCartList = new HashMap<>();
        for (int i = 0 ; i <cartList.size(); i++){
            updateCartList.put("book_id_"+i,cartList.get(i));
        }
        updateCartList.put("list_size",(long)cartList.size());
        firebaseFirestore.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("user_data")
                .document("my_cart")
                .set(updateCartList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(cartItemModelList.size() != 0){
                        cartItemModelList.remove(index);
                        MyCartFragment.cartAdapter.notifyDataSetChanged();
                        if(cartList.size()>0){
                            MainActivity.badgeCount.setText(String.valueOf(cartList.size()));
                        }else{
                            MainActivity.badgeCount.setVisibility(View.GONE);
                        }
                    }
                    if(cartList.size() == 0){
                        cartItemModelList.clear();
                        MyCartFragment.totalAmount.setText("0");
                        MyCartFragment.cartAdapter.notifyDataSetChanged();
                    }
                    Toast.makeText(context,"Xóa khỏi giỏ hàng thành công",Toast.LENGTH_LONG).show();
                }else{
                    cartList.add(index,removeBookID);
                    String error = task.getException().getMessage();
                    Toast.makeText(context,"Lỗi: "+error,Toast.LENGTH_SHORT).show();
                }
                BookDetailActivity.RUNNING_CART_QUERY = false;
            }
        });
    }

    public static void loadAddresses(final Context context, final Dialog loadingDialog){
        addressModelList.clear();
        firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("user_data")
                .document("my_addresses").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    Intent deliveryIntent;
                    if(task.getResult().getLong("list_size")==0){
                        deliveryIntent = new Intent(context, AddAdressActivity.class);
                        deliveryIntent.putExtra("INTENT","deliveryIntent");
                    }else{
                        for(long i = 1 ; i < task.getResult().getLong("list_size")+1; i++){
                            addressModelList.add(new AddressModel(task.getResult().getString("fullname_"+i)
                            ,task.getResult().getString("address_"+i)
                            ,task.getResult().getString("phone_"+i)
                            ,task.getResult().getBoolean("selected_"+i)));
                            if(task.getResult().getBoolean("selected_"+i)){
                                selectedAddress = Integer.parseInt(String.valueOf(i-1));
                            }
                        }
                        deliveryIntent = new Intent(context, DeliveryActivity.class);
                    }
                    context.startActivity(deliveryIntent);
                }else{
                    String error = task.getException().getMessage();
                    Toast.makeText(context,"Lỗi: "+error,Toast.LENGTH_SHORT).show();
                }
                loadingDialog.dismiss();
            }
        });
    }

    public static void clearData(){
        homePageModelList.clear();
        wishList.clear();
        wishListModelList.clear();
        cartList.clear();
        cartItemModelList.clear();
    }
}
