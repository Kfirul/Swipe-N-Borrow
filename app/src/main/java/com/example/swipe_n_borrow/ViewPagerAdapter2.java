package com.example.swipe_n_borrow;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter2 extends FragmentStateAdapter {
    public ViewPagerAdapter2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AdminHomeFragment();
            case 1:
                return new EditBooksFragment();
            case 2:
                return new BookListFragment();
            case 3:
                return new IssueFragment();
            case 4:
                return new CollectFineFragment();
            default:
                return new AdminHomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
