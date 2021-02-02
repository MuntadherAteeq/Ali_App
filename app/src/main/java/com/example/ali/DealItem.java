package com.example.ali;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import static android.view.View.VISIBLE;

public class DealItem extends AppCompatActivity {
    View note_Bar;
    CardView line;
    TextView note_price;
    ImageButton imageButton,clear_par;
    TextInputEditText edComment,edPrice;
    String comment;
    double price ;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deal_item_activity);
        Toolbar toolbar = findViewById(R.id.myNewToolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setActionBar(toolbar);
        }
        init();
        edPrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE)setWidgetFunctions();
                return true;
            }

        });
    }



    private void init() {

        clear_par = findViewById(R.id.clear_bar);
        edPrice = findViewById(R.id.price_text);
        edComment = findViewById(R.id.comment_text);
        imageButton = findViewById(R.id.image_button);
        note_Bar = findViewById(R.id.price_bar);
        note_price = findViewById(R.id.price_tag);
        line = findViewById(R.id.tag);
        clear_par.setVisibility(VISIBLE);
        edPrice.clearFocus();
        setWidgetFunctions();

        clear_par.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePriceTag();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWidgetFunctions();
            }
        });






    }
    public boolean validatePrice(double iPrice){
        return !(iPrice < (-1000)) && !(iPrice > (1000));
    }
    @SuppressLint("DefaultLocale")
    public String editPrice(double text){
      return String.format("%.3f", text);
    }
    public void setWidgetFunctions(){
        String text_price=edPrice.getText().toString().trim();
        if (!text_price.isEmpty()){

            double num_price = Double.parseDouble(text_price);
            if (validatePrice(num_price)){

                if (num_price >0 ){
                    note_price.setTextColor(getResources().getColor(R.color.green));
                    line.setCardBackgroundColor(getResources().getColor(R.color.green_dark));
                    setPriceTage(num_price);


                }else if (num_price<0){
                    note_price.setTextColor(getResources().getColor(R.color.red));
                    line.setCardBackgroundColor(getResources().getColor(R.color.red_dark));
                    setPriceTage(num_price);

                }else {
                    edPrice.setText("");
                }

            }else Toast.makeText(DealItem.this, "السعر مرفوض", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!edPrice.getText().toString().trim().equals("")){
            double temp = Double.parseDouble(edPrice.getText().toString().trim());
            outState.putDouble("price",temp);
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (!String.valueOf(savedInstanceState.getDouble("price")).equals("")){
            edPrice.setText(String.valueOf(savedInstanceState.getDouble("price")));
            setWidgetFunctions();
        }
    }
    private void deletePriceTag(){
        note_Bar.setVisibility(View.GONE);
        edComment.setVisibility(View.GONE);
        note_Bar.clearFocus();
        edPrice.setVisibility(VISIBLE);
        edPrice.requestFocus();
    }
    private void setPriceTage(double num_price){
        edPrice.setVisibility(View.GONE);
        edComment.setVisibility(VISIBLE);
        edPrice.clearFocus();
        edComment.requestFocus();
        price = Double.parseDouble(edPrice.getText().toString().trim());
        note_Bar.setVisibility(VISIBLE);
        note_price.setText(editPrice(num_price)+" BD");
    }
}