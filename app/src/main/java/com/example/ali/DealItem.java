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
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.ali.adapters.RecycleViewAdapter;
import com.example.ali.adapters.TranRecycleViewAdapter;
import com.example.ali.system.Database;
import com.example.ali.system.Deal;
import com.example.ali.system.Transaction;
import com.example.ali.ui.main.Fragment_1;
import com.example.ali.ui.main.Fragment_2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import static android.view.View.VISIBLE;

public class DealItem extends AppCompatActivity implements TranRecycleViewAdapter.OnClickTranItemListener  {

    View note_Bar;
    CardView line;
    TextView note_price ,dealer_name, local_total;
    Switch active_switch;
    ImageButton imageButton,clear_par;
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
            active_switch.setChecked(true);
        }else {
            active_switch.setChecked(false);
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
        local_total.setText(String.valueOf(local_sum));
        deal.setTotal(local_sum);
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
        // on active swich clicked
        active_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deal.setActive(active_switch.isChecked());
                db.updateDealData(deal);

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", "result");
        setResult(1,returnIntent);
        super.onBackPressed();
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
            edPrice.setText(String.valueOf(savedInstanceState.getDouble("price")));
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
        recyclerView = (RecyclerView) findViewById(R.id.transaction_recycleview);
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