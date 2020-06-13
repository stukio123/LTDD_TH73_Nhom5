package com.example.ecommerciandroiapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ecommerciandroiapp.Database.DataBaseQueries;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.ecommerciandroiapp.Database.DataBaseQueries.currentUser;
import static com.example.ecommerciandroiapp.MainActivity.signInDialog;
import static com.example.ecommerciandroiapp.RegisterActivity.onResetPasswordFragmen;
import static com.example.ecommerciandroiapp.RegisterActivity.onSignUpFragment;


public class UserFragment extends Fragment {

    private FrameLayout parentFrameLayout;

    public UserFragment() {
        // Required empty public constructor
    }

    private BottomNavigationView bottomNavigationView;
    private TextView txt_DangNhap;
    private Button btn_SignOut;
    private TextView user_name;
    private TextView user_email;
    private TextView myOrder;
    private TextView myWishList;
    private TextView myReward;
    private TextView myAddressed;
    private TextView btn_SigIn;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    public static final int MANAGE_ADDRESS = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        txt_DangNhap = view.findViewById(R.id.user_accout);
        parentFrameLayout = getActivity().findViewById(R.id.fragment_container);
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation_container);
        myOrder = view.findViewById(R.id.user_quanlydonhang);
        myWishList = view.findViewById(R.id.user_danhsachyeuthich);
        myReward = view.findViewById(R.id.user_magiamgia);
        myAddressed = view.findViewById(R.id.user_sodiachi);
        user_name = view.findViewById(R.id.user_name);
        user_name.setVisibility(View.GONE);
        user_email = view.findViewById(R.id.user_email);
        user_email.setVisibility(View.GONE);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        btn_SignOut = view.findViewById(R.id.btn_signout);

        /*if(txt_DangNhap.getText().equals("Đăng nhập")){
            txt_DangNhap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFragment(new SignInFragment());
                    parentFrameLayout.setVisibility(View.VISIBLE);
                }
            });
            btn_SignOut.setVisibility(View.GONE);
        }
        else
            txt_DangNhap.setEnabled(false);*/

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (currentUser != null) {
            txt_DangNhap.setVisibility(View.GONE);
            btn_SignOut.setVisibility(View.VISIBLE);
            user_name.setVisibility(View.VISIBLE);
            user_name.setText(currentUser.getDisplayName());
            user_email.setVisibility(View.VISIBLE);
            user_email.setText(currentUser.getEmail());
            btn_SignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*txt_DangNhap.setText("Đăng nhập");
                    txt_DangNhap.setEnabled(true);*/
                    //user_name.setText(currentUser.getDisplayName());
                    //user_email.setText(currentUser.getEmail());
                    user_name.setVisibility(View.GONE);
                    user_email.setVisibility(View.GONE);
                    txt_DangNhap.setVisibility(View.VISIBLE);
                    btn_SignOut.setVisibility(View.GONE);
                    firebaseAuth.signOut();
                    DataBaseQueries.clearData();
                }


            });
        } else {
            txt_DangNhap.setVisibility(View.VISIBLE);
            btn_SignOut.setVisibility(View.GONE);
        }
        txt_DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(getContext(), RegisterActivity.class);
                startActivity(registerIntent);
                //setFragment(new SignInFragment());
            }
        });
        myOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    setFragment(new MyOrderFragment());
                } else {
                    signInDialog.show();
                }
            }
        });
        myWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    setFragment(new MyWishListFragment());
                } else {
                    signInDialog.show();
                }

            }
        });
        myReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    setFragment(new MyRewardFragment());
                } else {
                    signInDialog.show();
                }

            }
        });

        myAddressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    Intent myAddressIntent = new Intent(getContext(), MyAddressActivity.class);
                    myAddressIntent.putExtra("MODE", MANAGE_ADDRESS);
                    startActivity(myAddressIntent);
                } else {
                    signInDialog.show();
                }

            }
        });
       /* }else {
            signInDialog.show();
        }*/
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_fromright, R.anim.slideout_fromleft);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}
