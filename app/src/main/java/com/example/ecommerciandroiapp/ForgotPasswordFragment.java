package com.example.ecommerciandroiapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends Fragment {

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    private EditText fgEmail;
    private Button btnReset;


    private FrameLayout parentFrameLayout;
    private FirebaseAuth firebaseAuth;

    private ViewGroup viewGroup;
    private ImageView iconEmail;
    private TextView conText;
    private ProgressBar progressBar;
    private ImageButton btnBack;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+" ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        fgEmail = view.findViewById(R.id.txt_Email);
        btnReset = view.findViewById(R.id.btn_Forgot);
        parentFrameLayout = getActivity().findViewById(R.id.farm);
        firebaseAuth = FirebaseAuth.getInstance();
        viewGroup = view.findViewById(R.id.linearLayout_Container);
        iconEmail = view.findViewById(R.id.iconEmail);
        conText = view.findViewById(R.id.iconConText);
        progressBar = view.findViewById(R.id.progressBar);
        btnBack = view.findViewById(R.id.btn_back);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });

        fgEmail.addTextChangedListener(new TextWatcher() {
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




        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(viewGroup);
                if (checkEmail()) {
                    iconEmail.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.sendPasswordResetEmail(fgEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.INVISIBLE);
                                conText.setText("Khôi phục email thành công. Vui lòng kiểm tra email");
                                conText.setVisibility(View.VISIBLE);
//                                conText.setTextColor(Color.GREEN);
                                btnReset.setEnabled(false);
//                                btnReset.setBackgroundColor(Color.rgb(128, 128, 128));
                            } else {
                                progressBar.setVisibility(View.GONE);
                                String error = task.getException().getMessage();
                                conText.setText(error);
                                conText.setTextColor(Color.RED);
                                conText.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                }else
                {
                    btnReset.setEnabled(false);
//                    btnReset.setBackgroundColor(Color.rgb(128,128,128));
                    Toast.makeText(getActivity(), "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean checkEmail(){
        if(fgEmail.getText().toString().matches(emailPattern))
            return true;
        else
            return false;
    }
    private void checkInput() {
        if(!TextUtils.isEmpty(fgEmail.getText())){
            btnReset.setEnabled(true);
//            btnReset.setBackgroundColor(Color.rgb(24, 158, 255));
        }else{
            btnReset.setEnabled(false);
//            btnReset.setBackgroundColor(Color.rgb(128,128,128));
        }
    }





    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_fromleft,R.anim.slideout_fromright);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
