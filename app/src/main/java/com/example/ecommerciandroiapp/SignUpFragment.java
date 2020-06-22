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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import static com.example.ecommerciandroiapp.RegisterActivity.onSignUpFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpFragment extends Fragment {

    public SignUpFragment() {
        // Required empty public constructor
    }

    private TextView alreadyHaveAccount;
    private FrameLayout parentFrameLayout;
    private EditText email,name,phone,password,confirmpassword;
    private ImageButton ibtn_Close;
    private Button btn_SignUp;
    private ProgressBar progressBar;
    public static boolean disableCloseBtn = false;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseFirestore firebaseFirestore;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+" ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        alreadyHaveAccount = view.findViewById(R.id.tv_SignUp2);
        parentFrameLayout = getActivity().findViewById(R.id.register_layout);
        email = view.findViewById(R.id.txt_Email);
        name = view.findViewById(R.id.txt_Name);
        password = view.findViewById(R.id.txt_Password);
        confirmpassword = view.findViewById(R.id.txt_ConfirmPassWord);
        phone = view.findViewById(R.id.txt_Phone);
        ibtn_Close = view.findViewById(R.id.btn_CloseSignUp);
        btn_SignUp = view.findViewById(R.id.btn_SignUp);
        progressBar = view.findViewById(R.id.SignUp_ProgressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if(disableCloseBtn){
            ibtn_Close.setVisibility(View.GONE);
        }else{
            ibtn_Close.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
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
        name.addTextChangedListener(new TextWatcher() {
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
        phone.addTextChangedListener(new TextWatcher() {
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
        confirmpassword.addTextChangedListener(new TextWatcher() {
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
        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailPassWord();
            }
        });
    }

    //Method kiểm tra Email và Password
    private void checkEmailPassWord() {
        //Validate Email theo pattern
        if(email.getText().toString().matches(emailPattern)) {
            //Password và ConfirmPassword phải giống nhau
            if(password.getText().toString().equals(confirmpassword.getText().toString())) {
                progressBar.setVisibility(View.VISIBLE);
                btn_SignUp.setEnabled(false);
                btn_SignUp.setBackgroundColor(Color.rgb(128,128,128));

                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Map<String, Object>  userData = new HashMap<>();
                                    userData.put("fullname",name.getText().toString());
                                    userData.put("phone",phone.getText().toString());
                                    //userData.put("user_data",null);
                                    firebaseFirestore.collection("users").document(firebaseAuth.getUid()).set(userData)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        final CollectionReference userDataReference = firebaseFirestore.collection("users").document(firebaseAuth.getUid()).collection("user_data");
                                                        Map<String, Object> wishListMap = new HashMap<>();
                                                        wishListMap.put("list_size",(long) 0);
                                                        Map<String,Object> ratingsMap = new HashMap<>();
                                                        ratingsMap.put("list_size",(long) 0);

                                                        Map<String,Object> cartMap = new HashMap<>();
                                                        cartMap.put("list_size",(long) 0);

                                                        Map<String,Object> myAddressMap = new HashMap<>();
                                                        myAddressMap.put("list_size",(long) 0);
                                                        Map<String,Object> myOrderMap = new HashMap<>();
                                                        myOrderMap.put("list_size",(long)0);

                                                        final List<String> documentNames = new ArrayList<>();
                                                        documentNames.add("my_wishList");
                                                        documentNames.add("my_ratings");
                                                        documentNames.add("my_cart");
                                                        documentNames.add("my_addresses");
                                                        documentNames.add("my_order");

                                                        final List<Map<String,Object>> documentFields = new ArrayList<>();
                                                        documentFields.add(wishListMap);
                                                        documentFields.add(ratingsMap);
                                                        documentFields.add(cartMap);
                                                        documentFields.add(myAddressMap);
                                                        documentFields.add(myOrderMap);

                                                        for(int i = 0 ; i < documentNames.size();i++){
                                                            final int finalI = i;
                                                            userDataReference.document(documentNames.get(i)).set(documentFields.get(i))
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    userDataReference.document(documentNames.get(finalI))
                                                                            .set(documentFields.get(finalI)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if(task.isSuccessful()){
                                                                                if(finalI == documentNames.size() - 1) {
                                                                                    mainIntent();
                                                                                }
                                                                            }else{
                                                                                progressBar.setVisibility(View.INVISIBLE);
                                                                                btn_SignUp.setEnabled(true);

                                                                                String error = task.getException().toString();
                                                                                Toast.makeText(getActivity(),"Lỗi: "+error,Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            });
                                                        }
                                                    }else{
                                                        String error = task.getException().toString();
                                                        Toast.makeText(getActivity(),"Lỗi: "+error,Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }else{
                                    String error = task.getException().toString();
                                    Toast.makeText(getActivity(),"Lỗi: "+error,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }else{
                progressBar.setVisibility(View.INVISIBLE);
                confirmpassword.setError("Hai mật khẩu không giống nhau !");
            }
        }else{
            progressBar.setVisibility(View.INVISIBLE);
            email.setError("Email không hợp lệ !");
        }
    }

    private void checkInput() {
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(name.getText())){
                if(!TextUtils.isEmpty(phone.getText())){
                    if(!TextUtils.isEmpty(password.getText()) && password.length()>=8){
                        if(!TextUtils.isEmpty(confirmpassword.getText())){
                            btn_SignUp.setEnabled(true);
//                            btn_SignUp.setBackgroundColor(Color.WHITE);
                        }else{
                            btn_SignUp.setEnabled(false);
//                            btn_SignUp.setBackgroundColor(Color.rgb(128,128,128));
                        }
                    }else{
                        btn_SignUp.setEnabled(false);
                    //    btn_SignUp.setBackgroundColor(Color.rgb(128,128,128));
                    }
                }else{
                    btn_SignUp.setEnabled(false);
                //    btn_SignUp.setBackgroundColor(Color.rgb(128,128,128));
                }
            }else{
                btn_SignUp.setEnabled(false);
            ///    btn_SignUp.setBackgroundColor(Color.rgb(128,128,128));
            }
        }else{
            btn_SignUp.setEnabled(false);
          //  btn_SignUp.setBackgroundColor(Color.rgb(128,128,128));
        }
    }

    private void mainIntent(){
        if(disableCloseBtn){
            disableCloseBtn = false;
        }else{
            Intent mainIntent = new Intent(getActivity(),MainActivity.class);
            startActivity(mainIntent);
        }
        getActivity().finish();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_fromleft,R.anim.slideout_fromright);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
