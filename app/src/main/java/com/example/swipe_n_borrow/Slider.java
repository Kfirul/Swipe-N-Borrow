package com.example.swipe_n_borrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Slider extends AppCompatActivity {

    ViewPager viewPager;
    Button btnNext;
    int[] layouts;
    Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        viewPager = findViewById(R.id.pager);
        btnNext = findViewById(R.id.nexBtn);
        layouts = new int[]{
                R.layout.slider_1,
                R.layout.slider_2,
                R.layout.slider_3
        };

        adapter = new Adapter(this,layouts);
        viewPager.setAdapter(adapter);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem()+1<layouts.length){
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }else{
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            }
        });
viewPager.addOnPageChangeListener(viewPagerChangeListener);
    }
    ViewPager.OnPageChangeListener viewPagerChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(position == layouts.length-1){
                btnNext.setText("Continue");
            }else{
                btnNext.setText("NEXT");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}

