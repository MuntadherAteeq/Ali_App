package com.example.ali.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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
                else holder.Pname.setVisibility(View.VISIBLE);
                holder.Pprice.setText(String.format("%.3f", item.get(position).gettPrice())+" BD");
                holder.po_toolbar.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        OnClickTranItemListener.onItemLongClick(position);
                        return false;
                    }
                });
                break;
            case -1 :
                holder.Nname.setText(item.get(position).gettName());
                if (item.get(position).gettName().length()==0)holder.Nname.setVisibility(View.GONE);
                else holder.Nname.setVisibility(View.VISIBLE);
                holder.Nprice.setText(String.format("%.3f", item.get(position).gettPrice())+" BD");
                holder.ne_toolbar.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        OnClickTranItemListener.onItemLongClick(position);
                        return false;
                    }
                });
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
       View ne_toolbar,po_toolbar;



        TranViewHolder(@NonNull View itemView, final OnClickTranItemListener OnClickTranItemListener) {
            super(itemView);
            this.OnClickTranItemListener = OnClickTranItemListener;
            // Define the elements by thier id


            Nprice = itemView.findViewById(R.id.price_negative_tag);
            Nname = itemView.findViewById(R.id.comment_negative_text);

            Pprice = itemView.findViewById(R.id.price_positive_tag);
            Pname = itemView.findViewById(R.id.comment_psitive_text);

            po_toolbar= itemView.findViewById(R.id.po_toolbar);
            ne_toolbar=itemView.findViewById(R.id.ne_toolbar);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    OnClickTranItemListener.onItemLongClick(getAdapterPosition());
                    return false;
                }
            });





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
        void onItemLongClick(int position);
    }
}

