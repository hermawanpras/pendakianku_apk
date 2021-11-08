package com.hermawan.pendakian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hermawan.pendakian.adapter.DetailPendakiAdapter;
import com.hermawan.pendakian.adapter.DetailUpdatePendakiAdapter;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.PendakiResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAnggotaActivity extends AppCompatActivity {

    Button tambahBtn, simpanBtn;
    ApiInterface apiInterface;

    RecyclerView rv;

    String idDaftar, idInfoJalur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_anggota);

        tambahBtn = findViewById(R.id.tambahBtn);
        simpanBtn = findViewById(R.id.simpanBtn);
        rv = findViewById(R.id.rv);

        apiInterface = ApiClient.getClient();

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        idDaftar = getIntent().getStringExtra("id_daftar");
        idInfoJalur = getIntent().getStringExtra("id_info_jalur");

        getDetailPendaki(idDaftar, idInfoJalur);

        tambahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UpdateAnggotaActivity.this, TambahUpdateAnggotaActivity.class);
                i.putExtra("id_daftar", idDaftar);
                startActivity(i);
                finish();
            }
        });

        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UpdateAnggotaActivity.this, DetailPendaftaranPendakianActivity.class);
                i.putExtra("id_daftar", idDaftar);
                i.putExtra("id_info_jalur", idInfoJalur);
                startActivity(i);
                finish();
            }
        });
    }

    private void getDetailPendaki(String id, String idInfoJalur) {
        apiInterface.getPendakiByIdDaftar(id).enqueue(new Callback<PendakiResponse>() {
            @Override
            public void onResponse(Call<PendakiResponse> call, Response<PendakiResponse> response) {
                if (response.body().status) {
                    List<PendakiResponse.PendakiModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    rv.setAdapter(new DetailUpdatePendakiAdapter(list, UpdateAnggotaActivity.this, idInfoJalur));
                }
            }

            @Override
            public void onFailure(Call<PendakiResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}