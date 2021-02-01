package com.example.ali;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.example.ali.system.Database;
import com.example.ali.system.Deal;
import com.example.ali.ui.main.Fragment_1;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import com.example.ali.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabs;
    private FloatingActionButton fab;
    public static int request_Code=-1;
    // public static ArrayList<Deal> deals =new ArrayList<>();
    private Database db;
   // private Deal deal;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (request_Code == 1){
            request_Code= -1;
            Fragment_1.NewItemAdded();
        }
    }

    @Override
    public void onBackPressed() {
        if (tabs.getSelectedTabPosition()==0){
            super.onBackPressed();
        }else {
            TabLayout.Tab tab = tabs.getTabAt(0);
            tab.select();
        }
    }


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        db = new Database(this);


        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        fab = findViewById(R.id.fab);
        fab.setBackgroundColor(Color.parseColor("#0096db"));






        // On tab change what will happen
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    fab.setImageResource(R.drawable.ic_add_24);
                }else if (tab.getPosition()==1){
                    fab.setImageResource(R.drawable.ic_search_24);

                }else if (tab.getPosition()==2){
                    fab.setImageResource(R.drawable.ic_wallet_24);

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewDealActivity.class);
                startActivityForResult(intent,0);

                //deals.add(new Deal());
                //Fragment_1.adapter.notifyItemInserted(0);
                //Fragment_1.recyclerView.scrollToPosition(0);

            }
    });









    }


        }

