package com.hermawan.pendakian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hermawan.pendakian.adapter.TambahAnggotaAdapter;

import static android.content.ContentValues.TAG;

public class TambahAnggotaActivity extends AppCompatActivity {

    EditText jumlahEt;
    Button simpanBtn, selesaiBtn;
    RecyclerView rv;

    String idDaftar;

    int jumlahAnggota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_anggota);

        jumlahEt = findViewById(R.id.jumlahAnggotaEt);
        simpanBtn = findViewById(R.id.simpanJumlahAnggotaBtn);
        selesaiBtn = findViewById(R.id.btnSelesai);
        rv = findViewById(R.id.rv);

        idDaftar = getIntent().getStringExtra("id_daftar");

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumlahAnggota = Integer.parseInt(jumlahEt.getText().toString());
                rv.setAdapter(new TambahAnggotaAdapter(jumlahAnggota, idDaftar,TambahAnggotaActivity.this));
            }
        });

        selesaiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                onBackPressed();
                Toast.makeText(getApplicationContext(), "Silakan tunggu validasi admin untuk pendaftaran Anda sebelum melakukan pembayaran.", Toast.LENGTH_LONG).show();
            }
        });
    }
}