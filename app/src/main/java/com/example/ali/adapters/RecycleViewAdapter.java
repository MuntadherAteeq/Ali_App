package com.example.ali.adapters;

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

import com.example.ali.R;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.myViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<String> item ;



    public RecycleViewAdapter(Context context, ArrayList item) {
        this.context = context;
        this.item = item;


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pocket_item, parent, false);
        return new myViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, final int position) {
        // Putting information into each view holder by it position
        holder.X.setText(item.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {


        // initialize layout elemnts here

        TextView X ;
        /*
        LinearLayout ;
        CardView ;
          */

        myViewHolder(@NonNull View itemView) {
            super(itemView);

            // Define the elements by thier id 
             X = itemView.findViewById(R.id.myText);

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

}

