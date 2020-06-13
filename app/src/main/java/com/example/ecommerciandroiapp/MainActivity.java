package com.example.ecommerciandroiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecommerciandroiapp.Database.DataBaseQueries;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.ecommerciandroiapp.Database.DataBaseQueries.currentUser;
import static com.example.ecommerciandroiapp.Database.DataBaseQueries.loadCartList;
import static com.example.ecommerciandroiapp.RegisterActivity.setSignupFragment;

public class MainActivity extends AppCompatActivity {

    public static boolean showCart = false;
    private FrameLayout framelayout;
    public static Dialog signInDialog;
    private TextView badgeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        framelayout = findViewById(R.id.fragment_container);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navLister);

        if (showCart) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Giỏ hàng");
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            setFragment(new MyCartFragment());
        } else {
            setDefaultFragment(new HomeFragment());
        }

        signInDialog = new Dialog(MainActivity.this);
        signInDialog.setCancelable(true);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button dialogSignIn = signInDialog.findViewById(R.id.sign_in_btn);
        Button dialogSignUp = signInDialog.findViewById(R.id.sign_up_btn);
        final Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);


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

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        MenuItem cartItem = menu.findItem(R.id.main_cart_icon);
        cartItem.setActionView(R.layout.badge_layout);
        ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badge_icon);
        badgeIcon.setImageResource(R.drawable.ic_cart);
        badgeCount = cartItem.getActionView().findViewById(R.id.badge_count);
        //badgeCount.setText(String.valueOf(DataBaseQueries.cartList.size()));
        if (currentUser != null) {
            if (DataBaseQueries.cartList.size() == 0) {
                loadCartList(MainActivity.this, new Dialog(MainActivity.this), false,badgeCount);
            }else{
                    badgeCount.setVisibility(View.VISIBLE);
                if(DataBaseQueries.cartList.size() < 99 ){
                    badgeCount.setText(String.valueOf(DataBaseQueries.cartList.size()));
                }else{
                    badgeCount.setText("99");
                }
            }
        }
        cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    setFragment(new MyCartFragment());
                } else {
                    signInDialog.show();
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_cart_icon) {
            if (currentUser == null) {
                signInDialog.show();
            } else {
                setFragment(new MyCartFragment());
            }

        } else if (item.getItemId() == android.R.id.home) {
            if (showCart) {
                showCart = false;
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navLister =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragmet = null;
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragmet = new HomeFragment();
                            break;
                        case R.id.nav_category:
                            selectedFragmet = new CategoryFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragmet = new SearchFragment();
                            break;
                        case R.id.nav_notification:
                            selectedFragmet = new MyCartFragment();
                            break;
                        case R.id.nav_user:
                            selectedFragmet = new UserFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragmet).commit();
                    return true;
                }
            };


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_fromleft, R.anim.slideout_fromright);
        fragmentTransaction.replace(framelayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void setDefaultFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(framelayout.getId(), fragment);
        fragmentTransaction.commit();
    }
};


