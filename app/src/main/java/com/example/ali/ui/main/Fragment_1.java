package com.example.ali.ui.main;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ali.MainActivity;
import com.example.ali.R;
import com.example.ali.adapters.RecycleViewAdapter;
import com.example.ali.system.Database;
import com.example.ali.system.Deal;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Fragment_1 extends Fragment implements RecycleViewAdapter.OnClickItemListener {
    private static Database db;
    private static Deal deal = new Deal();;
    private View view;
    public static RecyclerView recyclerView;
    public static RecycleViewAdapter adapter;
    public static ArrayList<Deal> deals ;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SearchView searchView;

    public Fragment_1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_1.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_1 newInstance(String param1, String param2) {
        Fragment_1 fragment = new Fragment_1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static void NewItemAdded() {
        deals.clear();
        storeDataInArrays();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                recyclerView.scrollToPosition(0);
                adapter.notifyItemInserted(0);
            }
        }, 400);   //5 seconds

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_1,container,false);
        db = new Database(this.getContext());
        deals = new ArrayList<>();
        storeDataInArrays();
        buildRecycleView();

        return view;
    }

    private void buildRecycleView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.inbox_RecycleView);
        adapter = new RecycleViewAdapter(getContext(), deals,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);



    }


    @Override
    public void onItemClick(int position) {
        //list.add("position");
       // adapter.notifyItemInserted(position);

    }

    private static void storeDataInArrays() {
        Cursor cursor = db.readAllDeals();
        if(cursor == null){

            // empty icon here
            // empty_imageview.setVisibility(View.VISIBLE);
            //  no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                deal = new Deal();
                deal.setName(cursor.getString(1));
                deal.setPhone(cursor.getString(2));
                deal.setDate(cursor.getString(3));
                deal.setTotal(cursor.getDouble(4));
                deal.setBuilding(cursor.getString(5));
                deal.setRoad(cursor.getString(6));
                deals.add(deal);
            }
            // delete empty icon
            //empty_imageview.setVisibility(View.GONE);
            //no_data.setVisibility(View.GONE);
        }
    }


}