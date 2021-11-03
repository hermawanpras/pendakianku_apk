package com.hermawan.pendakian;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TentangAplikasiActivity extends AppCompatActivity {

    TextView versiTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang_aplikasi);

        versiTv = findViewById(R.id.versiTv);

        versiTv.setText("Versi " + BuildConfig.VERSION_NAME);
    }
}