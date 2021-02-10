package com.example.ali;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import com.example.ali.system.Database;
import com.example.ali.system.Deal;
import com.example.ali.ui.main.Fragment_1;
import com.example.ali.ui.main.Fragment_2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private TabLayout tabs;
    private FloatingActionButton fab;
    public static int request_Code=-1;
    private Database db;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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


    @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
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

        setupTabIcons();





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



    @SuppressLint("UseCompatLoadingForDrawables")
    private void setupTabIcons() {
        tabs.getTabAt(0).setText("");
        tabs.getTabAt(1).setText("");
        tabs.getTabAt(2).setText("");

        tabs.getTabAt(0).setIcon((getResources().getDrawable(R.drawable.ic_archive_24)));
        tabs.getTabAt(1).setIcon((getResources().getDrawable(R.drawable.ic_history_24)));
        tabs.getTabAt(2).setIcon((getResources().getDrawable(R.drawable.ic_wallet_24)));

        tabs.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_IN);
        tabs.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN);
        tabs.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN);




        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_IN);
                switch (tab.getPosition()){
                    case 0:fab.setImageResource(R.drawable.ic_add_24);break;
                    case 1:fab.setImageResource(R.drawable.ic_search_24);break;
                    case 2:fab.setImageResource(R.drawable.ic_wallet_24);break;
                }

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN);
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}

