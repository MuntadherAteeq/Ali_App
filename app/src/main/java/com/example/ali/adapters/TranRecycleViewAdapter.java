package com.example.ali.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ali.R;
import com.example.ali.system.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.ali.R.layout.date_tag;
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
        if (item.get(position).getId() == 0){
            return 2;
        }
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
        View viewDate = inflater.inflate(date_tag,parent,false);
        switch (viewType){
            case 1 :  return new TranViewHolder(viewPositive, OnClickTranItemListener);
            case -1 : return new TranViewHolder(viewNegative, OnClickTranItemListener);
            case 2 : return new TranViewHolder(viewDate, OnClickTranItemListener);
        }
        return new TranViewHolder(viewPositive, OnClickTranItemListener);
    }


    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull final TranViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (getItemViewType(position)==2){
            String day = "";
            try {

                @SuppressLint("SimpleDateFormat") Date date=new SimpleDateFormat("yyyy/MM/dd").parse(item.get(position).getDate());
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dayofWeek = new SimpleDateFormat("EEEE");
                assert date != null;
                day = dayofWeek.format(date);

            }catch (Exception ignore){}
            holder.date.setText(item.get(position).getDate()+"   "+ day);
        }else {
            holder.time.setText(item.get(position).getTime());
            holder.price.setText(String.format("%.3f", item.get(position).gettPrice())+" BD");
            holder.comment.setText(item.get(position).gettName());
            if (item.get(position).gettName().length()==0)holder.comment.setVisibility(View.GONE);
            else holder.comment.setVisibility(View.VISIBLE);
            holder.toolbar.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    OnClickTranItemListener.onItemLongClick(position);
                    return false;
                }
            });
        }




    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    class TranViewHolder extends RecyclerView.ViewHolder {

        private OnClickTranItemListener OnClickTranItemListener;
        // initialize layout elemnts here

       TextView price ,comment, time , date ;
       View toolbar;



        TranViewHolder(@NonNull View itemView, final OnClickTranItemListener OnClickTranItemListener) {
            super(itemView);
            this.OnClickTranItemListener = OnClickTranItemListener;
            // Define the elements by their id


            price = itemView.findViewById(R.id.price_tag);
            comment = itemView.findViewById(R.id.comment_text);
            toolbar= itemView.findViewById(R.id.toolbar);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date_tag);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    OnClickTranItemListener.onItemLongClick(getAdapterPosition());
                    return false;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnClickTranItemListener.onItemClick(getAdapterPosition());
                }
            });
            





            //Animate Recyclerview
         /*
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
          */

            // Create On Click Listener for any specific element
         
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
        void onItemLongClick(int position );
    }
}

