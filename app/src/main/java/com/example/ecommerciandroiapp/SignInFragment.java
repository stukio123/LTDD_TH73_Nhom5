package com.example.ecommerciandroiapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

import static com.example.ecommerciandroiapp.RegisterActivity.onResetPasswordFragmen;
import static com.example.ecommerciandroiapp.RegisterActivity.onSignUpFragmen;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    public SignInFragment() {
        // Required empty public constructor
    }

    private TextView dontHaveAccount;
    private FrameLayout parentFrameLayout;

    private EditText email,password;
    private ImageButton ibtn_Close;
    private Button btn_SignIn;

    private TextView forgetPassword;

    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+" ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        dontHaveAccount = view.findViewById(R.id.tv_SignUp);
        parentFrameLayout = getActivity().findViewById(R.id.register_layout);

        email = (EditText) view.findViewById(R.id.txt_Email);
        password = (EditText) view.findViewById(R.id.txt_Password);
        ibtn_Close = view.findViewById(R.id.btn_CloseSignUp);
        btn_SignIn = view.findViewById(R.id.btn_SignIn);
        progressBar = view.findViewById(R.id.SignIn_ProgressBar);
        forgetPassword = view.findViewById(R.id.tv_ForgotPass);

        firebaseAuth = FirebaseAuth.getInstance();
         return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUpFragmen = true;
                setFragment(new SignUpFragment());
            }


        });

        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailPassword();
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetPasswordFragmen = true;
                setFragment(new ForgotPasswordFragment());
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkInput();
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkInput();
            }
        });
    }

    private void checkEmailPassword() {
        if(email.getText().toString().matches(emailPattern)){
            if(password.getText().length()>=8){
                progressBar.setVisibility(View.VISIBLE);
                btn_SignIn.setEnabled(false);
                btn_SignIn.setBackgroundColor(Color.rgb(128,128,128));
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText()
                        .toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent mainIntent = new Intent(getActivity(),MainActivity.class);
                            startActivity(mainIntent);
                            getActivity().finish();
                        }else{
                            progressBar.setVisibility(View.INVISIBLE);
                            btn_SignIn.setEnabled(true);
                            btn_SignIn.setBackgroundColor(Color.WHITE);
                            String error = task.getException().getMessage();
                            Toast.makeText(getActivity(),"Lỗi: "+error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }else{
                Toast.makeText(getActivity(),"Mật khẩu ít hơn 8 ký tự !",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getActivity(),"Email không đúng định dạng",Toast.LENGTH_SHORT).show();
        }

    }

    private void checkInput() {
        if(!TextUtils.isEmpty(email.getText())){
             if(!TextUtils.isEmpty(password.getText())){
                 btn_SignIn.setEnabled(true);
                 btn_SignIn.setBackgroundColor(Color.WHITE);
             }else{
                 btn_SignIn.setEnabled(false);
                 btn_SignIn.setBackgroundColor(Color.rgb(128,128,128));
             }
        }else{
            btn_SignIn.setEnabled(false);
            btn_SignIn.setBackgroundColor(Color.rgb(128,128,128));
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_fromright,R.anim.slideout_fromleft);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
