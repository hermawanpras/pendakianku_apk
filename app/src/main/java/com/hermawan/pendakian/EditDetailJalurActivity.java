package com.hermawan.pendakian;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.DetailJalurResponse;
import com.hermawan.pendakian.api.response.PendakiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDetailJalurActivity extends AppCompatActivity {

    TextView namaGunungTv, tanggalJalurTv;
    String idDetailJalur;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_detail_jalur);

        apiInterface = ApiClient.getClient();
        namaGunungTv = findViewById(R.id.namaGunungTv);
        tanggalJalurTv = findViewById(R.id.tanggalJalurTv);

        idDetailJalur = getIntent().getStringExtra("id_detail_jalur");

        getData(idDetailJalur);
    }

    private void getData(String id) {
        apiInterface.getDetailJalur(id).enqueue(new Callback<DetailJalurResponse>() {
            @Override
            public void onResponse(Call<DetailJalurResponse> call, Response<DetailJalurResponse> response) {
                if (response.body().status) {
                    DetailJalurResponse.DetailJalurModel dm = response.body().data.get(0);

                    namaGunungTv.setText(dm.getNamagunung());
                    tanggalJalurTv.setText(dm.getTanggalJalur());
                }
            }

            @Override
            public void onFailure(Call<DetailJalurResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}