 package com.example.ali;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
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
    Button submit,getFromContactButton;
    TextInputLayout tilName,tilPhone,tilBuilding,tilRoad,tilDate,tilImage;
    String name,phone,road,building,date,image;
    Deal deal;
    Database db;
    TextView date_picker;
    @SuppressLint("SimpleDateFormat")
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private Boolean contextPermission;

    @Override
    protected void onStart() {
        super.onStart();
        contextPermission = ContextCompat.checkSelfPermission(NewDealActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

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
        getFromContactButton =findViewById(R.id.choose_from_content);

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
                if (contextPermission){

                    if (!getContactDisplayNameByNumber(tilPhone.getEditText().getText().toString()).equals("?")){
                        tilName.getEditText().setText(getContactDisplayNameByNumber(tilPhone.getEditText().getText().toString()));
                    }


                }



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

        getFromContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contextPermission)getContact(v);
                requestContactsPermission();
            }
        });

}
private void requestContactsPermission(){
        if (!contextPermission) ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},10);
}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        contextPermission = ContextCompat.checkSelfPermission(NewDealActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
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

    public void getContact(View view) {
        if (!contextPermission) {
            requestContactsPermission();
        }else {
            Intent contactsIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            int pickContact = 1;
            startActivityForResult(contactsIntent, pickContact);
        }

    }
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data){
        super.onActivityResult(reqCode, resultCode, data);

        if(contextPermission){
            if (resultCode == Activity.RESULT_OK) {
                Uri contactData = data.getData();
                Cursor contact =  getContentResolver().query(contactData, null, null, null, null);

                if (contact.moveToFirst()) {
                    String name = contact.getString(contact.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    // TODO Whatever you want to do with the selected contact's name.

                    ContentResolver cr = getContentResolver();
                    Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, "DISPLAY_NAME = '" + name + "'", null, null);
                    if (cursor.moveToFirst()) {
                        String contactId =
                                cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        //
                        //  Get all phone numbers.
                        //
                        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);

                        while (phones.moveToNext()) {
                            if (phones.getPosition()==0){
                                String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                String Cname = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                if (number.contains("+973")){
                                    number = number.replace("+973","").trim();
                                    number.contains(" ");
                                    number = number.replace(" ","");
                                }
                                tilName.getEditText().setText(Cname);
                                tilPhone.getEditText().setText(number);
                            }

                        }
                        phones.close();
                    }
                    cursor.close();
                }
            }
        }else{
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
        }


    }

    public  String getContactDisplayNameByNumber(String number) {
        String name = "?";
        if (contextPermission){
        Uri uri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));

        ContentResolver contentResolver = this.getContentResolver();
        Cursor contactLookup = contentResolver.query(uri, new String[] {
                        BaseColumns._ID, ContactsContract.PhoneLookup.DISPLAY_NAME },
                null, null, null);

        try {
            if (contactLookup != null && contactLookup.getCount() > 0) {
                contactLookup.moveToNext();
                name = contactLookup.getString(contactLookup
                        .getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                // String contactId =
                // contactLookup.getString(contactLookup.getColumnIndex(BaseColumns._ID));
            }
        } finally {
            if (contactLookup != null) {
                contactLookup.close();
            }
        }
        }
        return name;
    }



    }


