package com.example.swipe_n_borrow;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter3 extends FragmentStateAdapter {
    public ViewPagerAdapter3(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AdminAddNewBook();
            case 1:
                return new AdminHomeFragment();

            default:
                return new AdminBooks();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}