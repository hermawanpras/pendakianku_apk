package com.hermawan.pendakian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.RecoverySystem;
import android.widget.Toast;

import com.hermawan.pendakian.adapter.DetailJalurAdapter;
import com.hermawan.pendakian.adapter.PembayaranAdapter;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.DetailJalurResponse;
import com.hermawan.pendakian.api.response.PembayaranResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CekPembayaranActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_pembayaran);

        apiInterface = ApiClient.getClient();
        rv = findViewById(R.id.rv);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        getData();
    }

    void getData() {
        apiInterface.getPembayaran("").enqueue(new Callback<PembayaranResponse>() {
            @Override
            public void onResponse(Call<PembayaranResponse> call, Response<PembayaranResponse> response) {
                if (response.body().status) {
                    List<PembayaranResponse.PembayaranModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    rv.setAdapter(new PembayaranAdapter(list, CekPembayaranActivity.this));
                }
            }

            @Override
            public void onFailure(Call<PembayaranResponse> call, Throwable t) {
                Toast.makeText(CekPembayaranActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}