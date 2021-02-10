package com.example.ali;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.adapters.TranRecycleViewAdapter;
import com.example.ali.system.Database;
import com.example.ali.system.Deal;
import com.example.ali.system.Transaction;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import static android.view.View.VISIBLE;

public class DealItem extends AppCompatActivity implements TranRecycleViewAdapter.OnClickTranItemListener  {

    View note_Bar;
    CardView line;
    TextView note_price ,dealer_name, local_total;
    Switch active_switch;
    ImageButton imageButton,clear_par,back_arrow;
    TextInputEditText edComment,edPrice;
    String comment;
    double price ;
    boolean done =false;
    ArrayList<Transaction> transactions;
    Transaction transaction;
    Database db = new Database(this);
    String Uid;
    private RecyclerView recyclerView;
    private TranRecycleViewAdapter adapter;
    Deal deal;
    Handler handler;
    String position;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deal_item_activity);
        init();
        getIntentFromActivity();
        setLayoutView();


    }


    private void setLayoutView() {
        clear_par.setVisibility(VISIBLE);
        dealer_name.setText(deal.getName());
        if (deal.isActive()){
            active_switch.setChecked(false);
        }else {
            active_switch.setChecked(true);
        }
        edPrice.clearFocus();
        setWidgetFunctions();
        onClickListeners();
        initLocal_sum();

    }


    private void init() {

        clear_par = findViewById(R.id.clear_bar);
        edPrice = findViewById(R.id.price_text);
        edComment = findViewById(R.id.comment_text);
        imageButton = findViewById(R.id.image_button);
        note_Bar = findViewById(R.id.price_bar);
        note_price = findViewById(R.id.price_tag);
        active_switch = findViewById(R.id.active_switch);
        dealer_name = findViewById(R.id.dealer_name);
        local_total = findViewById(R.id.local_price);
        line = findViewById(R.id.tag);
        back_arrow = findViewById(R.id.back_arrow);


    }

    @SuppressLint("ResourceAsColor")
    private void initLocal_sum() {
        double local_sum = 0;
        for (Transaction tran : transactions){
            local_sum += tran.gettPrice();
        }
        if (local_sum>0){
            local_total.setTextColor(getResources().getColor(R.color.green));
        }else{
            local_total.setTextColor(getResources().getColor(R.color.red));
        }
        local_total.setText(editPrice(local_sum));
        deal.setTotal(local_sum);
        db.updateDealData(deal);
    }

    private void onClickListeners() {
        // on X icon press
        clear_par.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePriceTag();
            }
        });
        // on send icon press
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!done){
                    setWidgetFunctions();
                }else {
                    transaction = new Transaction();
                    transaction.settPrice(price);
                    transaction.settName(edComment.getText().toString().trim());
                    transaction.setuID(Integer.parseInt(Uid));
                    boolean i = db.insert_New_Transaction(transaction);
                    deletePriceTag();
                    edPrice.setText("");
                    edComment.setText("");
                    transactions = db.readAllTransactions(Uid);
                    initLocal_sum();
                    adapter.notifyItemInserted(transactions.size()-1);
                    buildRecycleView(transactions);
                    recyclerView.scrollToPosition(transactions.size()-1);
                }
            }
        });
        // on keyboard button done pressed
        edPrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){

                    setWidgetFunctions();
                    done = true ;

                }
                return true;
            }

        });
        // on active swich change
        active_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                deal.setActive(!active_switch.isChecked());
                db.updateDealData(deal);
            }
        });
        // on back arrow clicked
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DealItem.super.onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean validatePrice(double iPrice){
        if (iPrice>1000 || iPrice<-1000){
            return false;
        }else if (iPrice<0.005 && iPrice>-0.005)return false;
        return true;
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
                    price = num_price;
                    done = true ;


                }else if (num_price<0){
                    note_price.setTextColor(getResources().getColor(R.color.red));
                    line.setCardBackgroundColor(getResources().getColor(R.color.red_dark));
                    setPriceTage(num_price);
                    price = num_price;
                    done =true ;

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
            outState.putBoolean("done" , done);
            outState.putDouble("price",temp);
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (!String.valueOf(savedInstanceState.getDouble("price")).equals("")){
            if (savedInstanceState.getDouble("price")!=0.0)edPrice.setText(String.valueOf(savedInstanceState.getDouble("price")));
            done = savedInstanceState.getBoolean("done");
            if (done)setWidgetFunctions();

        }
    }

    private void deletePriceTag(){
        note_Bar.setVisibility(View.GONE);
        edComment.setVisibility(View.GONE);
        note_Bar.clearFocus();
        edPrice.setVisibility(VISIBLE);
        edPrice.requestFocus();
        price = 0;
        done=false ;
    }

    private void setPriceTage(double num_price){
        edPrice.setVisibility(View.GONE);
        edComment.setVisibility(VISIBLE);
        edPrice.clearFocus();
        edComment.requestFocus();
        price = Double.parseDouble(edPrice.getText().toString().trim());
        note_Bar.setVisibility(VISIBLE);
        note_price.setText(editPrice(num_price)+" BD");
        done =true ;
    }

    private boolean getIntentFromActivity(){
            deal = new Deal();
            handler = new Handler();
            transactions =new ArrayList<>();
            transaction = new Transaction();
            Intent intent =getIntent();
            String  key = intent.getStringExtra("Uid");
            position = intent.getStringExtra("position");
            this.Uid =key;
            transactions = db.readAllTransactions(Uid);
            buildRecycleView(transactions);
            deal = db.readDealOwnerByID(Uid);
        return true;
    }

    private void buildRecycleView(ArrayList<Transaction> tran) {
        recyclerView = (RecyclerView) findViewById(R.id.pocket_RecycleView);
        adapter = new TranRecycleViewAdapter(this,this,tran);
        recyclerView.setLayoutManager(new LinearLayoutManager(DealItem.this));
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {

    }

    ItemTouchHelper.SimpleCallback simpleCallback =new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getLayoutPosition();
            AlertDialog.Builder builder1 = new AlertDialog.Builder(DealItem.this);
            builder1.setMessage("هل انت متأكد انك تريد حذف هذه المعاملة");
            builder1.setCancelable(true);
            builder1.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    adapter.notifyItemChanged(position);
                    dialog.cancel();
                }
            });
            builder1.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            db.deleteTransactionData(String.valueOf(transactions.remove(position).getId()));
                            adapter.notifyItemRemoved(position);
                            initLocal_sum();
                            dialog.cancel();
                        }
                    });
            builder1.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            builder1.show();
        }
    };


}