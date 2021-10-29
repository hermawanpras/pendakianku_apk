package com.hermawan.pendakian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hermawan.pendakian.adapter.TambahAnggotaAdapter;

import static android.content.ContentValues.TAG;

public class TambahAnggotaActivity extends AppCompatActivity {

    EditText jumlahEt;
    Button simpanBtn, selesaiBtn;
    RecyclerView rv;

    int jumlahAnggota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_anggota);

        jumlahEt = findViewById(R.id.jumlahAnggotaEt);
        simpanBtn = findViewById(R.id.simpanJumlahAnggotaBtn);
        selesaiBtn = findViewById(R.id.btnSelesai);
        rv = findViewById(R.id.rv);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumlahAnggota = Integer.parseInt(jumlahEt.getText().toString());
                Log.e(TAG, "onClick: " + jumlahAnggota);
                rv.setAdapter(new TambahAnggotaAdapter(jumlahAnggota, TambahAnggotaActivity.this));
            }
        });
    }
}