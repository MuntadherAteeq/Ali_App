package com.example.ali.ui.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.ali.DealItem;
import com.example.ali.R;
import com.example.ali.adapters.RecycleViewAdapter;
import com.example.ali.system.Database;
import com.example.ali.system.Deal;

import java.util.ArrayList;

public class Fragment_1 extends Fragment implements RecycleViewAdapter.OnClickItemListener {
    private static Database db;
    private static Deal deal = new Deal();;
    private View view;
    public static RecyclerView recyclerView;
    public static RecycleViewAdapter adapter;
    public static ArrayList<Deal> deals_active;
    public static Handler handler;


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
        deals_active = new ArrayList<>();
        handler = new Handler();
        buildRecycleView();
        return view;
    }

    private void buildRecycleView() {
        deals_active = db.readAllActiveDeals();
        recyclerView = (RecyclerView) view.findViewById(R.id.inbox_RecycleView);
        adapter = new RecycleViewAdapter(getContext(), deals_active,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onItemClick(int position) {

       Intent intent = new Intent(getActivity(), DealItem.class);
       intent.putExtra("Uid",String.valueOf(deals_active.get(position).getId()));
       intent.putExtra("position",String.valueOf(deals_active.get(position)));
       startActivityForResult(intent,11);
    }


    @Override
    public void onItemLongClick(int position) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("هل انت متأكد انك تريد حذف هذه المعاملة");
        builder1.setCancelable(true);

        builder1.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                db.deleteDealData(String.valueOf(deals_active.get(position).getId()));
                buildRecycleView();
                dialog.cancel();
            }
        });
        builder1.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder1.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        buildRecycleView();

    }

    @Override
    public void onStart() {
        buildRecycleView();
        super.onStart();
    }



}