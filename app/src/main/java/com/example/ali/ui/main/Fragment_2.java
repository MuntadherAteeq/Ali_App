package com.example.ali.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.DealItem;
import com.example.ali.R;
import com.example.ali.adapters.RecycleViewAdapter;
import com.example.ali.system.Database;
import com.example.ali.system.Deal;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_2 extends Fragment implements RecycleViewAdapter.OnClickItemListener {
    private View view;
    private RecyclerView recyclerView ;
    public static ArrayList<Deal> deals_unactive;
    public static Database db;
    private Deal deal;
    private Handler handler;
    public static RecycleViewAdapter adapter;
    private TextView public_total;
    private double total;





    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_2.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_2 newInstance(String param1, String param2) {
        Fragment_2 fragment = new Fragment_2();
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
        deals_unactive = new ArrayList<>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_1,container,false);
        db = new Database(this.getContext());
        deals_unactive = new ArrayList<>();
        handler = new Handler();
        public_total = view.findViewById(R.id.public_total);
        buildRecycleView();
        return view;
    }

    private void buildRecycleView() {
        total = 0;
        deals_unactive = db.readAllUnactiveDeals(total);
        recyclerView = (RecyclerView) view.findViewById(R.id.inbox_RecycleView);
        adapter = new RecycleViewAdapter(getContext(), deals_unactive,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
//        public_total.setText(String.valueOf(total));


    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(getActivity(), DealItem.class);
        intent.putExtra("Uid",String.valueOf(deals_unactive.get(position).getId()));
        startActivityForResult(intent,position);

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