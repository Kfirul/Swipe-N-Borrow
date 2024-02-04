package com.example.swipe_n_borrow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home2);


// Get the FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

// Replace the fragmentContainer with the AdminAddBook fragment
        AdminHomeFragment adminAddBookFragment = new AdminHomeFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, adminAddBookFragment);

// Add the transaction to the back stack (optional)
        fragmentTransaction.addToBackStack(null);

// Commit the transaction
        fragmentTransaction.commit();

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter2(this);
        viewPager2.setAdapter(viewPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());

                // Check if the "Books" tab is selected and launch AdminAddBook activity
                // Replace the fragment when "Books" tab is selected
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                if (tab.getPosition() == 2) {
                    // Get the FragmentManager

                    // Replace the fragmentContainer with the AdminAddBookFragment
                    AdminAddNewBook adminAddNewBook = new AdminAddNewBook();
                    fragmentTransaction.replace(R.id.fragmentContainer, adminAddNewBook);

                    // Add the transaction to the back stack (optional)
                    fragmentTransaction.addToBackStack(null);

                    // Commit the transaction
                    fragmentTransaction.commit();
                }
                if (tab.getPosition() == 0) {
                    // Get the FragmentManager


                    // Replace the fragmentContainer with the AdminAddBookFragment
                    AdminHomeFragment adminHomeFragment = new AdminHomeFragment();
                    fragmentTransaction.replace(R.id.fragmentContainer, adminHomeFragment);

                    // Add the transaction to the back stack (optional)
                    fragmentTransaction.addToBackStack(null);

                    // Commit the transaction
                    fragmentTransaction.commit();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                tabLayout.getTabAt(position).select();
//
//                // Show the "Log Out" button only when the "Home" tab is selected
//                if (position == 0) {
//                    logOutButton.setVisibility(View.VISIBLE);
//                } else {
//                    logOutButton.setVisibility(View.GONE);
//                }
//            }
//        });
    }
}
