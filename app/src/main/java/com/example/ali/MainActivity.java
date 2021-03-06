package com.example.ali;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import com.example.ali.system.Database;
import com.example.ali.system.Deal;
import com.example.ali.ui.main.Fragment_1;
import com.example.ali.ui.main.Fragment_2;
import com.example.ali.ui.main.Fragment_3;
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
import com.google.android.material.textfield.TextInputEditText;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static android.view.View.VISIBLE;
import static java.lang.String.format;

public class MainActivity extends AppCompatActivity {

    public static TabLayout tabs;
    public static FloatingActionButton fab;
    public static int request_Code=-1;
    private Database db;
    private TextView public_sum;
    private SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;
    private OnPocketFabClicked onPocketFabClicked;



    public interface OnPocketFabClicked{
        void onClick();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof OnPocketFabClicked) {
            onPocketFabClicked = (OnPocketFabClicked) fragment;
        }
    }


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
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        db = new Database(this);
        setPublicTotal();


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
            }
    });

    }

    @SuppressLint("DefaultLocale")
    public void setPublicTotal() {
        public_sum =findViewById(R.id.public_total);
        double num =db.getTotalSum();
        String text= String.format("%.3f", num);
        if (num>0){
            public_sum.setTextColor(getResources().getColor(R.color.green));
        }else {
            public_sum.setTextColor(getResources().getColor(R.color.red));
        }
        public_sum.setText(text);
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
                    case 0:
                        fab.setImageResource(R.drawable.ic_add_24);
                        fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, NewDealActivity.class);
                            startActivityForResult(intent,0);
                        }
                    });
                        break;
                    case 1:
                        fab.setOnClickListener(null);
                        fab.setImageResource(R.drawable.ic_search_24);break;
                    case 2:
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    onPocketFabClicked.onClick();
                                }catch (Exception e){}
                            }
                        });
                        fab.setImageResource(R.drawable.ic_wallet_24);break;

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

