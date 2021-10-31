package com.hermawan.pendakian.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdminLaporanFragmentAdapter extends FragmentStateAdapter {
    public AdminLaporanFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new AdminLaporanSosFragment();
            case 2:
                return new AdminLaporanReportFragment();
        }

        return new AdminLaporanBlacklistFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}