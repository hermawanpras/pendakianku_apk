package com.hermawan.pendakian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hermawan.pendakian.adapter.KuotaJalurAdapter;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.DetailJalurResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalLengkapActivity extends AppCompatActivity {

    TextView judulTv;
    RecyclerView rv;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_lengkap);

        rv  = findViewById(R.id.rv);
        judulTv = findViewById(R.id.judulTv);

        apiInterface = ApiClient.getClient();

        String judul = getIntent().getStringExtra("NAMA_GUNUNG");
        String idInfoJalur = getIntent().getStringExtra("ID_INFO_JALUR");
        judulTv.setText("Jadwal " + judul);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String tanggalMulai = sdf.format(c.getTime());

        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.DATE, 30);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String tanggalSelesai = sdf1.format(c1.getTime());

        apiInterface.getTanggalDaki(
                idInfoJalur,
                tanggalMulai,
                tanggalSelesai).enqueue(new Callback<DetailJalurResponse>() {
            @Override
            public void onResponse(Call<DetailJalurResponse> call, Response<DetailJalurResponse> response) {
                if (response.body().status) {
                    List<DetailJalurResponse.DetailJalurModel> list = new ArrayList<>();

                    list.addAll(response.body().data);


                    rv.setAdapter(new KuotaJalurAdapter(list, getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<DetailJalurResponse> call, Throwable t) {

            }
        });
    }
}