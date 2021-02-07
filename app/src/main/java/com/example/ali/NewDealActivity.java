package com.example.ali;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.system.Database;
import com.example.ali.system.Deal;
import com.example.ali.ui.main.Fragment_1;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class NewDealActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    Button submit;
    TextInputLayout tilName,tilPhone,tilBuilding,tilRoad,tilDate,tilImage;
    String name,phone,road,building,date,image;
    Deal deal;
    Database db;
    TextView date_picker;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");






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
                if (validateName() && validatePhone() && validateBuilding() && validateRoad()){
                   deal.setName(name);
                   deal.setPhone(phone);
                   deal.setBuilding(building);
                   deal.setRoad(road);
                   deal.setDate(date);
                    if ( db.insert_New_Deal(deal) ){
                        Fragment_1.deals_active.add(deal);
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
        String vPhone = Objects.requireNonNull( tilPhone.getEditText()).getText().toString().trim();
        if (vPhone.isEmpty()){
            tilPhone.setError(null);
            tilPhone.setError("رقم الهاتف لايمكن ان يكوف فارغ");
        }else if (vPhone.length()<8){
            tilPhone.setError(null);
            tilPhone.setError("رقم الهاتف غير صالح");
        }else {
            tilPhone.setError(null);
            phone = vPhone;
            return true;
        }
        return false;
    }
    private boolean validateBuilding(){
        String vBuilding = Objects.requireNonNull( tilBuilding.getEditText()).getText().toString().trim();

         if (vBuilding.length()>6){
            tilBuilding.setError(null);
            tilBuilding.setError("رقم المبنى غير صحيح");
        }else {
            tilBuilding.setError(null);
            building = vBuilding;
            return true;
        }
        return false;
    }
    private boolean validateRoad(){
        String vRoad = Objects.requireNonNull( tilPhone.getEditText()).getText().toString().trim();

        if (vRoad.length()<5){
            tilRoad.setError(null);
            tilRoad.setError("رقم الطريق غير صالح");
        }else {
            tilRoad.setError(null);
            road = vRoad;
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
        date_picker = findViewById(R.id.date_picker);

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


        Date newDate = new Date();
        this.date  = dateFormat.format(newDate);
        date_picker.setText(this.date);



        date_picker.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DialogFragment datePicker =new com.example.ali.DatePicker();
                datePicker.show(getSupportFragmentManager(),"Date Picker");

        }


    });
}

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c =Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDate = dateFormat.format(c.getTime());

        date_picker = findViewById(R.id.date_picker);
        date_picker.setText(currentDate);
        date = currentDate;

    }
    }


