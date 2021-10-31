package com.hermawan.pendakian.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hermawan.pendakian.fragment.AdminButhakFragment;
import com.hermawan.pendakian.fragment.AdminPandermanFragment;

public class AdminPendaftaranPendakianFragmentAdapter extends FragmentStateAdapter {
    public AdminPendaftaranPendakianFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new AdminPendaftaranButhakFragment();
        }

        return new AdminPendaftaranPandermanFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}