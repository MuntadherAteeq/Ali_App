package com.example.ali.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.example.ali.MainActivity;
import com.example.ali.R;
import com.example.ali.system.Deal;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.myViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<Deal> item ;
    private OnClickItemListener onClickItemListener;



    public RecycleViewAdapter(Context context, ArrayList<Deal> item,OnClickItemListener onClickItemListener) {
        this.context = context;
        this.item = item;
        this.onClickItemListener=onClickItemListener;

    }

    @Override
    public int getItemViewType(int position) {

     return position;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pocket_item, parent, false);
        return new myViewHolder(view,onClickItemListener);
    }


    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, final int position) {
        // Putting information into each view holder by it position
       holder.name_text.setText(item.get(position).getName());
       holder.phone_text.setText(item.get(position).getPhone());
       holder.date_text.setText(item.get(position).getDate());
       if (item.get(position).isActive()){
           holder.image_shadow.setVisibility(View.GONE);
           holder.name_text.setTextColor(context.getResources().getColor(R.color.white));
       }else {
           holder.image_shadow.setVisibility(View.VISIBLE);
           holder.name_text.setTextColor(context.getResources().getColor(R.color.textColor));
       }
    }

    @Override
    public int getItemCount() {
        return item.size();
    }



    class myViewHolder extends RecyclerView.ViewHolder {

        private OnClickItemListener onClickItemListener;


        // initialize layout elemnts here

      TextView name_text,date_text,phone_text ;
      View image_shadow;
        /*
        LinearLayout ;
        CardView ;
          */

        myViewHolder(@NonNull View itemView , final OnClickItemListener onClickItemListener) {
            super(itemView);
            this.onClickItemListener= onClickItemListener;


            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemListener.onItemClick(getAdapterPosition());
                }


            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onClickItemListener.onItemLongClick(getAdapterPosition());
                    return true;
                }
            });

            // Define the elements by thier id 
             name_text = itemView.findViewById(R.id.chat_name_txt);
             phone_text = itemView.findViewById(R.id.chat_phone_text);
             date_text = itemView.findViewById(R.id.chat_date_txt);
             image_shadow = itemView.findViewById(R.id.image_shadow);





            //Animate Recyclerview
         /*
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
          */

            // Create On Click Listener for any spsific element
         
            /* X.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    .onItemClick(getAdapterPosition());
                }
            });
             */

        }



    }
    public interface OnClickItemListener{
        void onItemClick(int position);
        void onItemLongClick(int position);
    }



}



