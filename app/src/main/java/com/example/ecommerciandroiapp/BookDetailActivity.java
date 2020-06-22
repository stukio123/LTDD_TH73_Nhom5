package com.example.ecommerciandroiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerciandroiapp.Database.DataBaseQueries;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.ecommerciandroiapp.Adapter.BookDetailAdapter;
import com.example.ecommerciandroiapp.Adapter.BookImageAdapter;
import com.example.ecommerciandroiapp.Model.BookSpecificationModel;
import com.example.ecommerciandroiapp.Model.CartItemModel;
import com.example.ecommerciandroiapp.Model.WishListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.ecommerciandroiapp.Database.DataBaseQueries.currentUser;
import static com.example.ecommerciandroiapp.Database.DataBaseQueries.loadCartList;
import static com.example.ecommerciandroiapp.Database.DataBaseQueries.loadRatingList;
import static com.example.ecommerciandroiapp.Database.DataBaseQueries.loadWishList;
import static com.example.ecommerciandroiapp.Database.DataBaseQueries.myRateIds;
import static com.example.ecommerciandroiapp.Database.DataBaseQueries.myRating;
import static com.example.ecommerciandroiapp.Database.DataBaseQueries.wishList;
import static com.example.ecommerciandroiapp.Database.DataBaseQueries.wishListModelList;
import static com.example.ecommerciandroiapp.MainActivity.showCart;
import static com.example.ecommerciandroiapp.RegisterActivity.setSignupFragment;

public class BookDetailActivity extends AppCompatActivity {

    public static boolean RUNNING_WISHLIST_QUERY = false;
    public static boolean RUNNING_RATING_QUERY = false;
    public static boolean RUNNING_CART_QUERY = false;
    public static boolean ADDED_TO_CART = false;
    public static MenuItem cartItem;

    private ViewPager bookImageViewPager;
    private ViewPager bookDetailViewPager;
    private TextView bookTitle;
    private TextView bookPrice;
    private TextView bookCuttedPrice;
    private RatingBar avgRatingBar;
    private TabLayout bookDetailTabLayout;
    private TextView avgRatings;
    private TextView totalRatings;
    private LinearLayout ratingsNumberContainer;
    private LinearLayout ratingProgressBarContainer;
    public static RatingBar yours_rating;
    private DocumentSnapshot documentSnapshot;
    private TextView star1, star2, star3, star4, star5;

    private List<BookSpecificationModel> bookSpecificationModelList = new ArrayList<>();
    private String bookDescription = "";

    //Rating layout
    private LinearLayout rateNowLayout;
    //Rating layout

    private Button buyNowBtn;
    private LinearLayout addToCart;
    private TabLayout viewPagerIndicator;
    public static boolean ADDED_TO_WISHLIST = false;
    public static FloatingActionButton addToWishListbtn;
    private Dialog signInDialog;
    private Dialog loadingDialog;
    public static String bookID;
    public static int currentRatings;
    private long totalRating;
    private FirebaseFirestore firebaseFirestore;
    private TextView badgeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        bookImageViewPager = findViewById(R.id.book_image_viewpager);
        viewPagerIndicator = findViewById(R.id.viewpager_indicator);
        bookTitle = findViewById(R.id.book_title);
        addToWishListbtn = findViewById(R.id.add_to_wish_list);
        addToCart = findViewById(R.id.add_to_card_btn);
        bookDetailViewPager = findViewById(R.id.book_detail_viewpager);
        bookDetailTabLayout = findViewById(R.id.book_detail_tablayout);
        buyNowBtn = findViewById(R.id.btn_buy_now);
        avgRatingBar = findViewById(R.id.book_ratingbar);
        bookPrice = findViewById(R.id.book_price);
        bookCuttedPrice = findViewById(R.id.book_costprice);
        avgRatings = findViewById(R.id.book_avg_rating);
        totalRatings = findViewById(R.id.tv_total_ratings);
        ratingsNumberContainer = findViewById(R.id.ratings_numbers_container);
        ratingProgressBarContainer = findViewById(R.id.ratings_progressbar_container);
        yours_rating = findViewById(R.id.yours_rating);

        firebaseFirestore = FirebaseFirestore.getInstance();
        bookID = getIntent().getStringExtra("book_id");
        final List<String> bookImages = new ArrayList<>();
        firebaseFirestore.collection("Books").document(bookID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            documentSnapshot = task.getResult();
                            bookImages.add(documentSnapshot.getString("image"));
                            BookImageAdapter bookImageAdapter = new BookImageAdapter(bookImages);
                            bookImageViewPager.setAdapter(bookImageAdapter);
                            bookTitle.setText(documentSnapshot.getString("title"));
                            avgRatingBar.setRating(documentSnapshot.getLong("avg_rating"));
                            bookPrice.setText(formatPrice(documentSnapshot.getString("price")));
                            bookCuttedPrice.setText(formatPrice(documentSnapshot.getString("cutted_price")));
                            bookDescription = documentSnapshot.getString("description");
                            bookSpecificationModelList = new ArrayList<>();
                            bookSpecificationModelList.add(new BookSpecificationModel("Nhà xuất bản", documentSnapshot.getString("publisher")));
                            bookSpecificationModelList.add(new BookSpecificationModel("Ngày xuất bản", documentSnapshot.getString("date")));
                            bookSpecificationModelList.add(new BookSpecificationModel("SKU", documentSnapshot.getString("sku")));
                            bookSpecificationModelList.add(new BookSpecificationModel("ISBN", documentSnapshot.getString("isbn")));
                            bookSpecificationModelList.add(new BookSpecificationModel("Số trang", documentSnapshot.getString("page")));
                            avgRatings.setText(String.valueOf(avgRatingBar.getRating()));
                            totalRating = documentSnapshot.getLong("total_rating");
                            totalRatings.setText(totalRating + " đánh giá");
                            for (int x = 0; x < 5; x++) {
                                TextView rating = (TextView) ratingsNumberContainer.getChildAt(x);
                                rating.setText(documentSnapshot.getString((5 - x) + "star"));
                                ProgressBar progressBar = (ProgressBar) ratingProgressBarContainer.getChildAt(x);
                                int maxProgress = (int) totalRating;
                                progressBar.setMax(maxProgress);
                                progressBar.setProgress(Integer.parseInt(Objects.requireNonNull(documentSnapshot.getString((5 - x) + "star"))));
                            }

                            bookDetailViewPager.setAdapter(new BookDetailAdapter(getSupportFragmentManager(), bookDetailTabLayout.getTabCount(), bookDescription, bookSpecificationModelList));

                            if (currentUser != null) {
                                if (DataBaseQueries.myRating.size() == 0) {
                                    loadRatingList(BookDetailActivity.this);
                                }
                                if (DataBaseQueries.cartList.size() == 0) {
                                    loadCartList(BookDetailActivity.this, loadingDialog, false,badgeCount);
                                }
                                if (DataBaseQueries.wishList.size() == 0) {
                                    loadWishList(BookDetailActivity.this, loadingDialog, false);
                                } else {
                                    loadingDialog.dismiss();
                                }
                            } else {
                                loadingDialog.dismiss();
                            }

                            if (myRateIds.contains(bookID)) {
                                int index = myRateIds.indexOf(bookID);
                                yours_rating.setRating(myRating.get(index));
                            }
                            if (DataBaseQueries.cartList.contains(bookID)) {
                                ADDED_TO_CART = true;
                            } else {
                                ADDED_TO_CART = false;
                            }
                            if (DataBaseQueries.wishList.contains(bookID)) {
                                ADDED_TO_WISHLIST = true;
                                addToWishListbtn.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4B29E6")));
                            } else {
                                ADDED_TO_WISHLIST = false;
                            }
                        } else {
                            loadingDialog.dismiss();
                            String error = Objects.requireNonNull(task.getException()).getMessage();
                            Toast.makeText(BookDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        loadingDialog = new Dialog(BookDetailActivity.this);
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(R.layout.loading_progress_layout);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        signInDialog = new Dialog(BookDetailActivity.this);
        signInDialog.setCancelable(true);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button dialogSignIn = signInDialog.findViewById(R.id.sign_in_btn);
        Button dialogSignUp = signInDialog.findViewById(R.id.sign_up_btn);

        final Intent registerIntent = new Intent(BookDetailActivity.this, RegisterActivity.class);

        dialogSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInFragment.disableCloseButton = true;
                SignUpFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignupFragment = false;
                startActivity(registerIntent);
            }
        });
        dialogSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInFragment.disableCloseButton = true;
                SignUpFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignupFragment = true;
                startActivity(registerIntent);
            }
        });


        viewPagerIndicator.setupWithViewPager(bookImageViewPager, true);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    if (!RUNNING_CART_QUERY) {
                        RUNNING_CART_QUERY = true;
//                        if (ADDED_TO_CART) {
//                            RUNNING_CART_QUERY = false;
//                            Toast.makeText(BookDetailActivity.this, "Sách đã có trong giỏ hàng", Toast.LENGTH_LONG).show();
//                        } else {
                            Map<String, Object> addBook = new HashMap<>();
                            addBook.put("book_id_" + String.valueOf(DataBaseQueries.cartList.size()), bookID);
                            addBook.put("list_size", (long) (DataBaseQueries.cartList.size() + 1));

                            firebaseFirestore.collection("users").document(currentUser.getUid()).collection("user_data")
                                    .document("my_cart")
                                    .update(addBook).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        if (DataBaseQueries.cartItemModelList.size() != 0) {
                                            DataBaseQueries.cartItemModelList.add(new CartItemModel(CartItemModel.CART_ITEM
                                                    , bookID
                                                    , documentSnapshot.getString("image")
                                                    , documentSnapshot.getString("title")
                                                    , documentSnapshot.getString("price")
                                                    , documentSnapshot.getString("publisher")
                                                    , documentSnapshot.getString("cutted_price")
                                                    , (long) 1));
                                        }
                                        ADDED_TO_CART = true;
                                        DataBaseQueries.cartList.add(bookID);
                                        Toast.makeText(BookDetailActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                                        invalidateOptionsMenu();
                                        RUNNING_CART_QUERY = false;
                                    } else {
                                        RUNNING_CART_QUERY = false;
                                        String error = Objects.requireNonNull(task.getException()).getMessage();
                                        Toast.makeText(BookDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }
//            }
        });

        //Xuất hiện lỗi khi click trở lại sách đã rating thì avg_rating trong database trả về 0
        yours_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, final float rating,
                                        boolean fromUser) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    if (!RUNNING_RATING_QUERY) {
                        RUNNING_RATING_QUERY = true;
                        //currentRatings = (int) yours_rating.getRating();
                        Map<String, Object> updateRating = new HashMap<>();
                        if (myRateIds.contains(bookID)) {
                            TextView oldRating;
                            TextView newRating;
                            if (rating == 5 || currentRatings == 5) {
                                oldRating = (TextView) ratingsNumberContainer.getChildAt(5 - currentRatings);
                                newRating = (TextView) ratingsNumberContainer.getChildAt(5 - (int) rating);
                            } else {
                                newRating = (TextView) ratingsNumberContainer.getChildAt(5 - (int) rating - 1);
                                oldRating = (TextView) ratingsNumberContainer.getChildAt(5 - (int) yours_rating.getRating() - 1);
                            }
                            updateRating.put(String.format("%sstar", currentRatings), String.valueOf(Integer.parseInt(oldRating.getText().toString()) - 1));
                            updateRating.put(String.format("%sstar", (int) rating), String.valueOf(Integer.parseInt(newRating.getText().toString()) + 1));
                            updateRating.put("avg_rating", calculateAvgRating(((long) rating - currentRatings), true));
                        } else {
                            TextView youratingText;
                            youratingText = (TextView) ratingsNumberContainer.getChildAt(5 - (int) rating);
                            updateRating.put((int) rating + "star", String.valueOf(Integer.parseInt(youratingText.getText().toString()) + 1));
                            //youratingText.setText(String.valueOf(Integer.parseInt(youratingText.getText().toString()) + 1));
                            updateRating.put("avg_rating", calculateAvgRating((long) rating, false));
                            updateRating.put("total_rating", documentSnapshot.getLong("total_rating") + 1);
                        }
                        firebaseFirestore.collection("Books").document(bookID).update(updateRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    final Map<String, Object> myRatings = new HashMap<>();
                                    if (myRateIds.contains(bookID)) {
                                        myRatings.put("rating_" + myRateIds.indexOf(bookID), (long) rating);
                                    } else {
                                        myRatings.put("list_size", (long) myRateIds.size() + 1);
                                        myRatings.put("book_id_" + myRateIds.size(), bookID);
                                        myRatings.put("rating_" + myRateIds.size(), (int) rating);
                                    }
                                    firebaseFirestore.collection("users").document(currentUser.getUid()).collection("user_data")
                                            .document("my_ratings").update(myRatings).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                TextView tv_rating = (TextView) ratingsNumberContainer.getChildAt(5 - (int) rating);
                                                if (myRateIds.contains(bookID)) {
                                                    myRating.set(myRateIds.indexOf(bookID), (long) rating);
                                                    TextView oldRating;
                                                    TextView newRating;
                                                    if (rating == 5 || currentRatings == 5) {
                                                        oldRating = (TextView) ratingsNumberContainer.getChildAt(6 - currentRatings - 1);
                                                        newRating = (TextView) ratingsNumberContainer.getChildAt(6 - (int) rating - 1);
                                                    } else {
                                                        newRating = (TextView) ratingsNumberContainer.getChildAt(5 - (int) rating - 1);
                                                        oldRating = (TextView) ratingsNumberContainer.getChildAt(5 - (int) yours_rating.getRating() - 1);
                                                    }
                                                    System.out.println(currentRatings + "current rating và rating" + rating);
                                                    System.out.println(oldRating.getText().toString() + " xuống hàng \n" + newRating.getText().toString());
                                                    oldRating.setText(String.valueOf(Integer.parseInt(oldRating.getText().toString()) - 1));
                                                    newRating.setText(String.valueOf(Integer.parseInt(newRating.getText().toString()) + 1));
                                                } else {
                                                    totalRating++;
                                                    tv_rating.setText(String.valueOf(Integer.parseInt(tv_rating.getText().toString()) + 1));
                                                    myRateIds.add(bookID);
                                                    myRating.add((long) rating);
                                                    Toast.makeText(BookDetailActivity.this, "Cảm ơn bạn đã đánh giá", Toast.LENGTH_LONG).show();
                                                }

                                                totalRatings.setText(String.format("%s đánh giá", totalRating));

                                                for (int x = 0; x < 5; x++) {
                                                    ProgressBar progressBar = (ProgressBar) ratingProgressBarContainer.getChildAt(x);
                                                    if (!myRateIds.contains(bookID)) {
                                                        int maxProgress = (int) totalRating;
                                                        progressBar.setMax(maxProgress);
                                                    } else {
                                                        progressBar.setProgress(Integer.parseInt(tv_rating.getText().toString()));
                                                    }
                                                }
                                                currentRatings = (int) rating;
                                                avgRatings.setText(String.valueOf(calculateAvgRating((long) rating, false)));
                                                if (wishList.contains(bookID) && wishListModelList.size() != 0) {
                                                    int index = wishList.indexOf(bookID);
                                                    WishListModel changeRatings = wishListModelList.get(index);
                                                    wishListModelList.get(index).setRating(Long.parseLong(avgRatings.getText().toString()));
                                                    wishListModelList.get(index).setTotalRating(Long.parseLong(totalRatings.getText().toString()));
                                                }
                                            } else {
                                                currentRatings = (int) rating;
                                                yours_rating.setRating(0);
                                                String error = Objects.requireNonNull(task.getException()).getMessage();
                                                Toast.makeText(BookDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                                            }
                                            RUNNING_RATING_QUERY = false;
                                        }
                                    });
                                    //currentRatings = (int) rating;
                                    //yours_rating.setRating(currentRatings);
                                } else {
                                    RUNNING_RATING_QUERY = false;
                                    currentRatings = (int) rating;
                                    yours_rating.setRating(0);
                                    String error = Objects.requireNonNull(task.getException()).getMessage();
                                    Toast.makeText(BookDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });

        addToWishListbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    //addToWishListbtn.setEnabled(false);
                    if (!RUNNING_WISHLIST_QUERY) {
                        RUNNING_WISHLIST_QUERY = true;
                        if (ADDED_TO_WISHLIST) {
                            int index = DataBaseQueries.wishList.indexOf(bookID);
                            DataBaseQueries.removeWishList(index, BookDetailActivity.this);
                            addToWishListbtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        } else {
                            Map<String, Object> addBook = new HashMap<>();
                            addBook.put("book_id_" + String.valueOf(DataBaseQueries.wishList.size()), bookID);
                            addBook.put("list_size", (long) (DataBaseQueries.wishList.size() + 1));
                            firebaseFirestore.collection("users").document(currentUser.getUid()).collection("user_data").document("my_wishList")
                                    .update(addBook).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        if (DataBaseQueries.wishListModelList.size() != 0) {
                                            DataBaseQueries.wishListModelList.add(new WishListModel(bookID, documentSnapshot.getString("image")
                                                    , documentSnapshot.getString("title")
                                                    , documentSnapshot.getString("author")
                                                    , documentSnapshot.getLong("avg_rating")
                                                    , documentSnapshot.getLong("total_rating")
                                                    , documentSnapshot.getString("price")
                                                    , documentSnapshot.getString("cutted_price")));
                                        }
                                        ADDED_TO_WISHLIST = true;
                                        addToWishListbtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4B29E6")));
                                        DataBaseQueries.wishList.add(bookID);
                                        Toast.makeText(BookDetailActivity.this, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                                    } else {
                                        RUNNING_WISHLIST_QUERY = false;
                                        String error = Objects.requireNonNull(task.getException()).getMessage();
                                        Toast.makeText(BookDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                                    }
                                    RUNNING_WISHLIST_QUERY = false;
                                }
                            });
                        }
                    }
                }
            }
        });

        bookDetailViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(bookDetailTabLayout));
        bookDetailTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                bookDetailViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    Intent deliveryIntent = new Intent(BookDetailActivity.this, DeliveryActivity.class);
                    startActivity(deliveryIntent);
                }
            }
        });
    }

    private float calculateAvgRating(long yourRatings, boolean update) {
        //totalRating = documentSnapshot.getLong("total_rating");
        float totalStars = 0;
        for (int i = 0; i < 5; i++) {
            TextView tv = (TextView) ratingsNumberContainer.getChildAt(i);
            totalStars = totalStars + Long.parseLong(tv.getText().toString()) * (5-i);
        }
        totalStars = totalStars + yourRatings;
        if (update) {
            return Math.round(totalStars / totalRating);
        } else {
            return (totalStars / (totalRating + 1));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            if (DataBaseQueries.myRating.size() == 0) {
                loadRatingList(BookDetailActivity.this);
            }
            if (DataBaseQueries.cartList.size() == 0) {
                loadCartList(BookDetailActivity.this, loadingDialog, true,badgeCount);
            }
            if (DataBaseQueries.wishList.size() == 0) {
                loadWishList(BookDetailActivity.this, loadingDialog, false);
            } else {
                loadingDialog.dismiss();
            }
        } else {
            loadingDialog.dismiss();
        }
        if (myRateIds.contains(bookID)) {
            int index = myRateIds.indexOf(bookID);
            yours_rating.setRating(myRating.get(index));
        }
        if (DataBaseQueries.cartList.contains(bookID)) {
            ADDED_TO_CART = true;
        } else {
            ADDED_TO_CART = false;
        }
        if (DataBaseQueries.wishList.contains(bookID)) {
            ADDED_TO_WISHLIST = true;
        } else {
            ADDED_TO_WISHLIST = false;
        }
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_and_cart, menu);
        cartItem = menu.findItem(R.id.main_cart_icon);
        cartItem.setActionView(R.layout.badge_layout);
        ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badge_icon);
        badgeIcon.setImageResource(R.drawable.ic_cart);
        badgeCount = cartItem.getActionView().findViewById(R.id.badge_count);
        badgeCount.setText(String.valueOf(DataBaseQueries.cartList.size()));
        if (currentUser != null) {
            if (DataBaseQueries.cartList.size() == 0) {
                loadCartList(BookDetailActivity.this, loadingDialog, false,badgeCount);
            }else{
                badgeCount.setVisibility(View.VISIBLE);
                if(DataBaseQueries.cartList.size() < 99 ){
                    badgeCount.setText(String.valueOf(DataBaseQueries.cartList.size()));
                }else{
                    badgeCount.setText("99");
                }
            }
        }
        /*cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    Intent cartIntet = new Intent(BookDetailActivity.this, MainActivity.class);
                    showCart = true;
                    startActivity(cartIntet);
                } else {
                    signInDialog.show();
                }
            }
        });*/

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.main_search_icon) {
            return true;
        } else if (id == R.id.main_cart_icon) {
            if (currentUser != null) {
                Intent cartIntet = new Intent(BookDetailActivity.this, MainActivity.class);
                showCart = true;
                startActivity(cartIntet);
                return true;
            } else {
                signInDialog.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private String formatPrice(String price) {
        int prices = 0;
        if (price != null)
            prices = Integer.parseInt(price);
        return String.format("%,d đ", prices);
    }
}