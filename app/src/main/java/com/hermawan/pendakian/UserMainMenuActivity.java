package com.hermawan.pendakian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserMainMenuActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_main_menu);

        loadFragment (new UserHomeFragment());
        BottomNavigationView bottomNavigationView = findViewById(R.id.btn_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case  R.id.menu_home:
                fragment = new UserHomeFragment();
                break;
            case  R.id.menu_guide:
                fragment = new UserGuideFragment();
                break;
            case  R.id.menu_history:
                fragment = new UserHistoryFragment();
                break;
            case  R.id.menu_profile:
                fragment = new UserProfileFragment();
                break;
        }
        return loadFragment(fragment);
    }
}
