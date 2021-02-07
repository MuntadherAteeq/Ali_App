package com.example.ali.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ali.DealItem;
import com.example.ali.R;
import com.example.ali.system.Transaction;

import java.util.ArrayList;

import static com.example.ali.R.layout.item_negative;
import static com.example.ali.R.layout.item_positive;

public class TranRecycleViewAdapter extends RecyclerView.Adapter<TranRecycleViewAdapter.TranViewHolder> {

    private Context context;
    private ArrayList<Transaction> item;
    private OnClickTranItemListener OnClickTranItemListener;


    public TranRecycleViewAdapter( Context context, OnClickTranItemListener OnClickTranItemListener, ArrayList<Transaction> item) {
        this.context = context;
        this.item = item;
        this.OnClickTranItemListener = OnClickTranItemListener;

    }



    @Override
    public int getItemViewType(int position) {
        if (item.get(position).gettPrice() > 0){
            return 1;
        }else {
            return -1;
        }

    }



    @NonNull
    @Override
    public TranViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewPositive = inflater.inflate(item_positive,parent,false);
        View viewNegative = inflater.inflate(item_negative,parent,false);
        switch (viewType){
            case 1 :  return new TranViewHolder(viewPositive, OnClickTranItemListener);
            case -1 : return new TranViewHolder(viewNegative, OnClickTranItemListener);
        }
        return new TranViewHolder(viewPositive, OnClickTranItemListener);
    }


    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull final TranViewHolder holder, final int position) {
        // Putting information into each view holder by it position
        switch (holder.getItemViewType()){
            case 1 :
                holder.Pname.setText(item.get(position).gettName());
                if (item.get(position).gettName().length()==0)holder.Pname.setVisibility(View.GONE);
                holder.Pprice.setText(String.format("%.3f", item.get(position).gettPrice())+" BD");
                break;
            case -1 :
                holder.Nname.setText(item.get(position).gettName());
                if (item.get(position).gettName().length()==0)holder.Nname.setVisibility(View.GONE);
                holder.Nprice.setText(String.format("%.3f", item.get(position).gettPrice())+" BD");
                break;
        }


        // holder.X.setText(item.get(position));
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    class TranViewHolder extends RecyclerView.ViewHolder {

        private OnClickTranItemListener OnClickTranItemListener;
        // initialize layout elemnts here

       TextView Pprice ,Pname,Nprice,Nname ;


        TranViewHolder(@NonNull View itemView, final OnClickTranItemListener OnClickTranItemListener) {
            super(itemView);
            this.OnClickTranItemListener = OnClickTranItemListener;
            // Define the elements by thier id


            Nprice = itemView.findViewById(R.id.price_negative_tag);
            Nname = itemView.findViewById(R.id.comment_negative_text);

            Pprice = itemView.findViewById(R.id.price_positive_tag);
            Pname = itemView.findViewById(R.id.comment_psitive_text);

            //Animate Recyclerview
         /*
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
          */

            // Create On Click Listener for any spsific element
         
            /* X.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnClickTranItemListener.onItemClick(getAdapterPosition());
                }
            });
             */

        }


    }

    public interface OnClickTranItemListener {
        void onItemClick(int position);
    }
}

