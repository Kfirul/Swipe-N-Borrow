package com.example.swipe_n_borrow;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.swipe_n_borrow.AdminAddNewBook;
import com.example.swipe_n_borrow.AdminHomeFragment;
import com.example.swipe_n_borrow.CollectFineFragment;
import com.example.swipe_n_borrow.EditBooksFragment;
import com.example.swipe_n_borrow.IssueFragment;

public class ViewPagerAdapter3 extends FragmentStateAdapter {
    public ViewPagerAdapter3(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new page1();
            case 1:
                return new page2();

            default:
                return new page3();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}