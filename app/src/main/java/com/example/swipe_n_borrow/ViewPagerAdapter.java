package com.example.swipe_n_borrow;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new UserFragmentHome();
            case 1:
                return new UserFragmentSearch();
            case 2:
                return new UserFragmentMyBooks();
            case 3:
                return new UserFragmentReIssue();
            default:
                return new UserFragmentHome();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
