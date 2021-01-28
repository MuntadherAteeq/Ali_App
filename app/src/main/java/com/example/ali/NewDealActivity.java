package com.example.ali;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ali.system.Database;
import com.example.ali.system.Deal;
import com.example.ali.ui.main.Fragment_1;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class NewDealActivity extends AppCompatActivity {
    Button submit;
    TextInputLayout tilName,tilPhone,tilBuilding,tilRoad,tilDate,tilImage;
    String name,phone,road,building,date,image;
    Deal deal;
    Database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_deal);
        deal = new Deal();
        db = new Database(this);


        confirmInputs();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateName() && validatePhone()){
                   deal.setName(Objects.requireNonNull(tilName.getEditText()).getText().toString());    
                   deal.setPhone(Objects.requireNonNull(tilPhone.getEditText()).getText().toString());
                   deal.setBuilding(tilBuilding.getEditText().getText().toString());
                   deal.setRoad(tilRoad.getEditText().getText().toString());
                    if ( db.insert_New_Deal(deal) ){
                        Fragment_1.deals.add(deal);
                        MainActivity.request_Code=1;
                        finish();
                    }else {
                        Toast.makeText(NewDealActivity.this, "There was something wrong", Toast.LENGTH_SHORT).show();
                    }
                }
                
            }
        });

 


    }

    private boolean validateName(){
        String vName = Objects.requireNonNull(tilName.getEditText()).getText().toString().trim();
        if (vName.isEmpty()){
            tilName.setError(null);
            tilName.setError("الاسم لايمكن ان يكوف فارغ");
        }else if (vName.length()<=2){
            tilName.setError(null);
            tilName.setError("الاسم غير صالح");
        }else {
            tilName.setError(null);
            name=vName;
            return true;
        }
        return false;
    }
    private boolean validatePhone(){
        String vName = Objects.requireNonNull( tilPhone.getEditText()).getText().toString().trim();
        if (vName.isEmpty()){
            tilPhone.setError(null);
            tilPhone.setError("رقم الهاتف لايمكن ان يكوف فارغ");
        }else if (vName.length()<8){
            tilPhone.setError(null);
            tilPhone.setError("رقم الهاتف غير صالح");
        }else {
            tilPhone.setError(null);
            return true;
        }
        return false;
    }


    private void confirmInputs() {
        submit = findViewById(R.id.submit_button);
        tilName=findViewById(R.id.name_field);
        tilPhone=findViewById(R.id.phone_filed);
        tilBuilding=findViewById(R.id.building_field);
        tilRoad=findViewById(R.id.road_field);

        tilName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tilName.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    validateName();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tilPhone.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tilPhone.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePhone();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}

