package com.example.swipe_n_borrow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

/**
 * The {@code AdminHome} class represents the main activity for the admin user.
 * It provides functionality for navigating different sections of the application.
 */
public class AdminHome extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter2 viewPagerAdapter;
    Button logOutButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home2);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter2(this);
        viewPager2.setAdapter(viewPagerAdapter);
        logOutButton = findViewById(R.id.logOut);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());

                // Check if the "Books" tab is selected and launch AdminAddBook activity
                if (tab.getPosition() == 2) {  // Assuming "Books" tab is at position 2
                    Intent addBookIntent = new Intent(AdminHome.this, AdminAddBook.class);
                    startActivity(addBookIntent);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();

                // Show the "Log Out" button only when the "Home" tab is selected
                if (position == 0) {
                    logOutButton.setVisibility(View.VISIBLE);
                } else {
                    logOutButton.setVisibility(View.GONE);
                }
            }
        });
    }
}
