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

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.ecommerciandroiapp.RegisterActivity.onResetPasswordFragmen;
import static com.example.ecommerciandroiapp.RegisterActivity.onSignUpFragmen;


public class UserFragment extends Fragment {

    private FrameLayout parentFrameLayout;

    public UserFragment() {
        // Required empty public constructor
    }

    private BottomNavigationView bottomNavigationView;
    private TextView txt_DangNhap;
    private Button btn_SignOut;
    private TextView myOrder;
    private TextView myWishList;
    private TextView myReward;
    private TextView myAddressed;
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

//        bottomNavigationView.setVisibility(View.GONE);
        btn_SignOut = view.findViewById(R.id.btn_signout);
        if(txt_DangNhap.getText().equals("Đăng nhập")){
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
            txt_DangNhap.setEnabled(false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_DangNhap.setText("Đăng nhập");
                txt_DangNhap.setEnabled(true);
                btn_SignOut.setVisibility(View.GONE);
            }


        });
        myOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new MyOrderFragment());
            }
        });
        myWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new MyWishListFragment());
            }
        });
        myReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new MyRewardFragment());
            }
        });

        myAddressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAddressIntent = new Intent(getContext(),MyAddressActivity.class);
                myAddressIntent.putExtra("MODE",MANAGE_ADDRESS);
                startActivity(myAddressIntent);
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_fromright,R.anim.slideout_fromleft);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
