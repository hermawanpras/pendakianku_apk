package com.hermawan.pendakian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.hermawan.pendakian.adapter.DetailJalurAdapter;

public class AdminHomeActivity extends Fragment {

    AdminHomeFragmentAdapter adapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_admin_home, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.viewPager);
        fab = view.findViewById(R.id.fab);

        FragmentManager fm = getChildFragmentManager();
        adapter = new AdminHomeFragmentAdapter(fm, getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Buthak"));
        tabLayout.addTab(tabLayout.newTab().setText("Panderman"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
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
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TambahDetailJalurActivity.class));
            }
        });

        return view;
    }
}
