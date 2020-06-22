package com.example.ecommerciandroiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ecommerciandroiapp.Database.DataBaseQueries;
import com.example.ecommerciandroiapp.Model.AddressModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAdressActivity extends AppCompatActivity {

    private Button saveBtn;
    private EditText name;
    private EditText phone;
    private EditText address;
    private Spinner city;
    private Spinner ward;
    private Spinner district;
    private String selectedCity;
    private String selectedDistrict;
    private String selectedWard;
    private Dialog loadingDialog;
    private String[] cityList;
    private String[] districtList;
    private String[] wardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adress);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Thêm địa chỉ mới");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        saveBtn = findViewById(R.id.save_btn);
        name = findViewById(R.id.edit_text_name);
        phone = findViewById(R.id.edit_text_phone);
        address = findViewById(R.id.edit_text_address);
        city = findViewById(R.id.spinner_city);
        ward = findViewById(R.id.spinner_ward);
        district = findViewById(R.id.spinner_district);

        loadingDialog = new Dialog(this);
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(R.layout.loading_progress_layout);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        cityList = getResources().getStringArray(R.array.Vietnam_states);
        districtList = getResources().getStringArray(R.array.Vietnam_district);
        wardList = getResources().getStringArray(R.array.ward_quan1);

        ArrayAdapter spinnerCityAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,cityList);
        spinnerCityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(spinnerCityAdapter);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = cityList[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter spinnerDistrictAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,districtList);
        spinnerDistrictAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        district.setAdapter(spinnerDistrictAdapter);
        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDistrict = districtList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter spinnerWard = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,wardList);
        spinnerWard.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ward.setAdapter(spinnerWard);
        ward.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedWard = wardList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(name.getText())){
                    if(!TextUtils.isEmpty(address.getText())){
                        if(!TextUtils.isEmpty(phone.getText())){
                            loadingDialog.show();
                            //address.getText().toString()+selectedWard+selectedDistrict+selectedCity
                            final String fullAddress = String.format("%s, %s, %s, %s",address.getText().toString(),selectedWard,selectedDistrict,selectedCity);
                            Map<String,Object> addAddress = new HashMap<>();
                            addAddress.put("list_size", (long)DataBaseQueries.addressModelList.size()+1);
                            addAddress.put("fullname_"+ String.valueOf ((long)DataBaseQueries.addressModelList.size()+1),name.getText().toString());
                            addAddress.put("address_"+String.valueOf ((long)DataBaseQueries.addressModelList.size()+1),fullAddress);
                            addAddress.put("phone_"+String.valueOf ((long)DataBaseQueries.addressModelList.size()+1),phone.getText().toString());
                            addAddress.put("selected_"+String.valueOf ((long)DataBaseQueries.addressModelList.size()+1),true);
                            if(DataBaseQueries.addressModelList.size() >0) {
                                addAddress.put("selected_" + (DataBaseQueries.selectedAddress + 1), false);
                            }
                            FirebaseFirestore.getInstance().collection("users")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .collection("user_data")
                                    .document("my_addresses")
                                    .update(addAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        if(DataBaseQueries.addressModelList.size() >0){
                                            DataBaseQueries.addressModelList.get(DataBaseQueries.selectedAddress).setSelected(false);
                                        }

                                        DataBaseQueries.addressModelList.add(new AddressModel(name.getText().toString(),fullAddress,phone.getText().toString(),true));

                                        if(getIntent().getStringExtra("INTENT").equals("deliveryIntent")) {
                                            Intent deliveryIntent = new Intent(AddAdressActivity.this, DeliveryActivity.class);
                                            startActivity(deliveryIntent);
                                        }else{
                                            MyAddressActivity.refresh(DataBaseQueries.selectedAddress,DataBaseQueries.addressModelList.size() - 1);
                                        }
                                        DataBaseQueries.selectedAddress = DataBaseQueries.addressModelList.size() - 1;
                                        finish();
                                    }else{
                                        String error = task.getException().getMessage();
                                        Toast.makeText(AddAdressActivity.this,"Lỗi: "+error,Toast.LENGTH_SHORT).show();
                                    }
                                    loadingDialog.dismiss();
                                }
                            });
                        }else{
                            phone.requestFocus();
                        }
                    }else{
                        address.requestFocus();
                    }
                }else{
                    name.requestFocus();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}