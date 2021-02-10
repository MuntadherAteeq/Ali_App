package com.example.ali.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ali.R;
import com.example.ali.adapters.RecycleViewAdapter;
import com.example.ali.adapters.TranRecycleViewAdapter;
import com.example.ali.system.Database;
import com.example.ali.system.Deal;
import com.example.ali.system.Pocket;
import com.example.ali.system.Transaction;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_3 extends Fragment implements TranRecycleViewAdapter.OnClickTranItemListener {
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<Transaction> item;
    public static TranRecycleViewAdapter adapter;
    private Database db;

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
        item = new ArrayList<>();
        db = new Database(getContext());
        buildRecycleView();
        return view;
    }
    private void buildRecycleView() {
        item = db.readAllPocketTransaction();
        recyclerView = (RecyclerView) view.findViewById(R.id.pocket_RecycleView);
        adapter = new TranRecycleViewAdapter(getContext(),this,item);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onItemClick(int position) {

    }

}