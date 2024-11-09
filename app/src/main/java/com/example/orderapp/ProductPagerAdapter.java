package com.example.orderapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ProductPagerAdapter extends FragmentStateAdapter {

    public ProductPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Returning the correct fragments based on the position
        if (position == 0) {
            return new NewProductsFragment();  // First tab (New Products)
        } else if (position == 1) {
            return new ScheduledProductsFragment();  // Second tab (Scheduled Products)
        } else {
            return new DeliveredProductsFragment();  // Third tab (Delivered Products)
        }
    }

    @Override
    public int getItemCount() {
        return 3;  // Three fragments for the three tabs
    }
}
