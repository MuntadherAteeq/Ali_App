package com.example.ali;

import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import com.example.ali.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    TabLayout tabs;
    FloatingActionButton fab;

    @Override
    public void onBackPressed() {
        if (tabs.getSelectedTabPosition()==0){
            super.onBackPressed();
        }else {
            TabLayout.Tab tab = tabs.getTabAt(0);
            tab.select();
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        fab = findViewById(R.id.fab);


        tabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, String.valueOf(tabs.getSelectedTabPosition()), Toast.LENGTH_SHORT).show();
            }
        });
        // On tab change what will happen
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    fab.setImageResource(R.drawable.ic_add_24);
                }else if (tab.getPosition()==1){
                    fab.setImageResource(R.drawable.ic_add_24);

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
                if (tabs.getSelectedTabPosition() != 2) {
                    Intent intent = new Intent(MainActivity.this, NewDealActivity.class);
                    startActivityForResult(intent, 1);
                }else {
                    // open new page to add to wallet
                }
            }
        });
    }

}
