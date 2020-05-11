package com.example.ecommerciandroiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

public class RegisterActivity extends AppCompatActivity {

    private FrameLayout framelayout;
    public static boolean onResetPasswordFragmen = false;
    public static boolean onSignUpFragmen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        framelayout = (FrameLayout) findViewById(R.id.register_layout);
        setDefaultFragment(new SignInFragment());
    }

   /* @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        switch (count)
        {
            case 0:
                super.onBackPressed();
                break;
            case 1:
                setFragment(new SignInFragment());
                break;
            default:
                getFragmentManager().popBackStack();
                break;
        }
    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(onResetPasswordFragmen){
                setFragment(new SignInFragment());
                onResetPasswordFragmen = false;
                return false;
            }
            if(onSignUpFragmen){
                setFragment(new SignInFragment());
                onSignUpFragmen = false;
                return false;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    private void setDefaultFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(framelayout.getId(),fragment);
        fragmentTransaction.commit();
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_fromleft,R.anim.slideout_fromright);
        fragmentTransaction.replace(framelayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
