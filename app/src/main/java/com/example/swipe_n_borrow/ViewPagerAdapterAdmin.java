package com.example.swipe_n_borrow;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapterAdmin extends FragmentStateAdapter {
    public ViewPagerAdapterAdmin(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AdminProfile();
            case 1:
                 return new AdminAddNewBook();

            default:
                return new AdminBooks();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}