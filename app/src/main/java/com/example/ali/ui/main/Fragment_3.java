package com.example.ali.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.MainActivity;
import com.example.ali.R;
import com.example.ali.adapters.TranRecycleViewAdapter;
import com.example.ali.system.Database;
import com.example.ali.system.Transaction;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_3 extends Fragment implements TranRecycleViewAdapter.OnClickTranItemListener, MainActivity.OnPocketFabClicked {
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<Transaction> item;
    public static TranRecycleViewAdapter adapter;
    private Database db;
    private TextInputEditText edPrice,edComment;
    private TextView note_price,public_sum;
    private View note_Bar;
    private CardView line;
    double price;
    boolean done =false;
    Transaction transaction;
    ImageButton clear_par;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Fragment_3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_3.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_3 newInstance(String param1, String param2) {
        Fragment_3 fragment = new Fragment_3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_3,container,false);
        edPrice = view.findViewById(R.id.pocket_edtext);
        clear_par = view.findViewById(R.id.clear_bar);
        edComment = view.findViewById(R.id.comment_text);
        note_Bar = view.findViewById(R.id.price_bar);
        note_price = view.findViewById(R.id.price_tag);
        line = view.findViewById(R.id.tag);
        public_sum = Objects.requireNonNull(getActivity()).findViewById(R.id.public_total);
        clear_par.setVisibility(VISIBLE);

        item = new ArrayList<>();
        db = new Database(getContext());
        buildRecycleView();
        setOnClickListeners();


        return view;
    }

    public void setOnClickListeners() {

                if (!done){
                    setWidgetFunctions();
                }else {
                    transaction = new Transaction();
                    transaction.settPrice(price);
                    transaction.settName(edComment.getText().toString().trim());
                    transaction.setuID(0);
                    boolean i = db.insert_New_Transaction(transaction);
                    deletePriceTag();
                    edPrice.setText("");
                    edComment.setText("");
                    setPublicTotal();
                    buildRecycleView();
                }




        note_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text_price=edPrice.getText().toString().trim();
                if (!text_price.isEmpty()) {
                    edPrice.setText(String.valueOf(Double.parseDouble(text_price)*-1));
                    setWidgetFunctions();
                }

            }
        });

        clear_par.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePriceTag();
            }
        });


    }

    private void buildRecycleView() {
        item = db.readAllPocketTransaction();
        recyclerView = (RecyclerView) view.findViewById(R.id.pocket_RecycleView);
        adapter = new TranRecycleViewAdapter(getContext(),this,item);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(item.size()-1);

    }


    @Override
    public void onItemClick(int position) {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onItemLongClick(int position) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("هل انت متأكد انك تريد حذف هذه المعاملة");
        builder1.setCancelable(true);
        builder1.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.cancel();
            }
        });
        builder1.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                db.deleteTransactionData(item.get(position));
                buildRecycleView();
                setPublicTotal();
                dialog.cancel();
            }
        });
        builder1.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();

            }
        });
        builder1.show();
    }


    public void setWidgetFunctions(){
        edPrice = view.findViewById(R.id.pocket_edtext);
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

            }

        }

    }
    private void deletePriceTag(){
        note_Bar.setVisibility(View.GONE);
        edComment.setVisibility(View.GONE);
        note_Bar.clearFocus();
        edPrice.setVisibility(VISIBLE);
        edPrice.requestFocus();
        price = 0;
        done=false;
    }
    public void setPublicTotal() {

        double num =db.getTotalSum();
        @SuppressLint("DefaultLocale") String text= String.format("%.3f", num);
        if (num>0){
            public_sum.setTextColor(getResources().getColor(R.color.green));
        }else {
            public_sum.setTextColor(getResources().getColor(R.color.red));
        }
        public_sum.setText(text);

    }
    public boolean validatePrice(double iPrice){
        return ((iPrice >= 0.005) && (iPrice < 1000)) || ((iPrice > -1000) && (iPrice <= -0.005));
    }
    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void setPriceTage(double num_price){
        edPrice.setVisibility(View.GONE);
        edComment.setVisibility(VISIBLE);
        edPrice.clearFocus();
        edComment.requestFocus();
        price = Double.parseDouble(edPrice.getText().toString().trim());
        note_Bar.setVisibility(VISIBLE);
        note_price.setText(String.format("%.3f", num_price)+" BD");
        done =true;
    }


    @Override
    public void onClick() {
        setOnClickListeners();
    }
}