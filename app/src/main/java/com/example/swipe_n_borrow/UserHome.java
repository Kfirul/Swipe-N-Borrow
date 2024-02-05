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
 * The {@code UserHome} class represents the main activity for the regular user.
 * It provides functionality for searching, viewing borrowed books, reissuing books, and logging out.
 * The user can navigate to different sections of the application by clicking on the corresponding buttons.
 * Additionally, the user has the ability to log out of the application.
 */
public class UserHome extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        tabLayout=findViewById(R.id.tabLayout);
        viewPager2=findViewById(R.id.viewpager);
        viewPagerAdapter=new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());

                // Check if the "Books" tab is selected and launch AdminAddBook activity
                // Replace the fragment when "Books" tab is selected
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                if (tab.getPosition() == 1) {
                    // Replace the fragmentContainer with the UserFragmentSearch
                    UserFragmentSearch userFragmentSearch = new UserFragmentSearch();
                    fragmentTransaction.replace(R.id.fragmentContainer, userFragmentSearch);

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
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        Button logOut = findViewById(R.id.logOut);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();

            }
        });

    }

}
