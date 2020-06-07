package com.example.ecommerciandroiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Connection;

import static com.example.ecommerciandroiapp.RegisterActivity.setSignupFragment;

public class MainActivity extends AppCompatActivity {

    public static boolean showCart = false;
    private FrameLayout framelayout;

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


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.main_cart_icon){
            final Dialog signInDialog = new Dialog(MainActivity.this);
            signInDialog.setCancelable(true);
            signInDialog.setContentView(R.layout.sign_in_dialog);
            signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            Button dialogSignIn = signInDialog.findViewById(R.id.sign_in_btn);
            Button dialogSignUp = signInDialog.findViewById(R.id.sign_up_btn);
            final Intent registerIntent = new Intent(MainActivity.this,RegisterActivity.class);


            dialogSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signInDialog.dismiss();
                    setSignupFragment = false;
                    startActivity(registerIntent);
                }
            });
            dialogSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signInDialog.dismiss();
                    setSignupFragment = true;
                    startActivity(registerIntent);
                }
            });
            signInDialog.show();
            //setFragment(new MyCartFragment());
        }else if(item.getItemId() == android.R.id.home){
            if(showCart){
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
                    switch (item.getItemId()){
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
        fragmentTransaction.setCustomAnimations(R.anim.slide_fromleft,R.anim.slideout_fromright);
        fragmentTransaction.replace(framelayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    private void setDefaultFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(framelayout.getId(),fragment);
        fragmentTransaction.commit();
    }
};


