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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import static com.example.ecommerciandroiapp.RegisterActivity.onSignUpFragmen;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
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
        email = (EditText) view.findViewById(R.id.txt_Email);
        name = (EditText) view.findViewById(R.id.txt_Name);
        password = (EditText) view.findViewById(R.id.txt_Password);
        confirmpassword = (EditText) view.findViewById(R.id.txt_ConfirmPassWord);
        phone = (EditText) view.findViewById(R.id.txt_Phone);
        ibtn_Close = view.findViewById(R.id.btn_CloseSignUp);
        btn_SignUp = view.findViewById(R.id.btn_SignUp);
        progressBar = view.findViewById(R.id.SignUp_ProgressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
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
                                    Map<Object,String> userdata = new HashMap<>();
                                    //userdata.put("email",email.getText().toString());
                                    userdata.put("fullname",name.getText().toString());
                                    userdata.put("phone",phone.getText().toString());
                                    firebaseFirestore.collection("users").add(userdata)
                                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getActivity(),"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                                                Intent mainIntent = new Intent(getActivity(),MainActivity.class);
                                                startActivity(mainIntent);
                                                getActivity().finish();
                                            }else{
                                                progressBar.setVisibility(View.INVISIBLE);
                                                btn_SignUp.setEnabled(true);
                                                btn_SignUp.setBackgroundColor(Color.WHITE);
                                                String error = task.getException().toString();
                                                Toast.makeText(getActivity(),"Lỗi: "+error,Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else{
                                    progressBar.setVisibility(View.INVISIBLE);
                                    btn_SignUp.setEnabled(true);
                                    btn_SignUp.setBackgroundColor(Color.WHITE);
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

    //Method kiểm tra các EditText đang được điền đầy đủ
    //Nếu 1 trong những EditText chưa được điền thì button SignUp không được Enable
    private void checkInput() {
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(name.getText())){
                if(!TextUtils.isEmpty(phone.getText())){
                    if(!TextUtils.isEmpty(password.getText()) && password.length()>=8){
                        if(!TextUtils.isEmpty(confirmpassword.getText())){
                            btn_SignUp.setEnabled(true);
                            btn_SignUp.setBackgroundColor(Color.WHITE);
                        }else{
                            btn_SignUp.setEnabled(false);
                            btn_SignUp.setBackgroundColor(Color.rgb(128,128,128));
                        }
                    }else{
                        btn_SignUp.setEnabled(false);
                        btn_SignUp.setBackgroundColor(Color.rgb(128,128,128));
                    }
                }else{
                    btn_SignUp.setEnabled(false);
                    btn_SignUp.setBackgroundColor(Color.rgb(128,128,128));
                }
            }else{
                btn_SignUp.setEnabled(false);
                btn_SignUp.setBackgroundColor(Color.rgb(128,128,128));
            }
        }else{
            btn_SignUp.setEnabled(false);
            btn_SignUp.setBackgroundColor(Color.rgb(128,128,128));
        }
    }

    //Method lấy fragment load lên Activity
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_fromleft,R.anim.slideout_fromright);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
