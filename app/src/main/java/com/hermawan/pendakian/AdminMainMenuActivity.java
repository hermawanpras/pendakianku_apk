package com.hermawan.pendakian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminMainMenuActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_menu);

        loadFragment (new AdminHomeActivity());
        BottomNavigationView bottomNavigationView = findViewById(R.id.btn_nav_admin);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameContainerAdmin, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case  R.id.admin_menu_home:
                fragment = new AdminHomeActivity();
                break;
            case  R.id.admin_menu_check:
                fragment = new AdminCekActivity();
                break;
            case  R.id.admin_menu_report:
                fragment = new AdminLaporanActivity();
                break;
            case  R.id.admin_menu_profile:
                fragment = new AdminProfileActivity();
                break;
        }
        return loadFragment(fragment);
    }
}
