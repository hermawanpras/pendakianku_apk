package com.hermawan.pendakian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.BaseResponse;
import com.hermawan.pendakian.api.response.DetailJalurResponse;
import com.hermawan.pendakian.api.response.PendakiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDetailJalurActivity extends AppCompatActivity {

    TextView namaGunungTv, tanggalJalurTv;
    String idDetailJalur;
    Button simpanBtn;
    RadioGroup radioGroup;
    EditText kuotaEt, keteranganEt;

    ApiInterface apiInterface;
    String status = "buka";
    String sisaKuota = "0";
    String oldTotal = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_detail_jalur);

        apiInterface = ApiClient.getClient();
        namaGunungTv = findViewById(R.id.namaGunungTv);
        tanggalJalurTv = findViewById(R.id.tanggalJalurTv);
        simpanBtn = findViewById(R.id.simpanBtn);
        kuotaEt = findViewById(R.id.kuotaEt);
        keteranganEt = findViewById(R.id.keteranganEt);
        radioGroup = findViewById(R.id.radioGroup);

        idDetailJalur = getIntent().getStringExtra("id_detail_jalur");

        getData(idDetailJalur);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.buka:
                        status = "buka";
                        break;
                    case R.id.tutup:
                        status = "tutup";
                        break;
                }
            }
        });

        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
    }

    private void update() {
        apiInterface.updateDj(
                idDetailJalur,
                kuotaEt.getText().toString().trim(),
                oldTotal,
                sisaKuota,
                status,
                keteranganEt.getText().toString().trim()
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        onBackPressed();
                        finish();
                        Toast.makeText(EditDetailJalurActivity.this, "Detail jalur berhasil diupdate.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(EditDetailJalurActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("daftar", t.getMessage());
            }
        });
    }

    private void getData(String id) {
        apiInterface.getDetailJalur(id).enqueue(new Callback<DetailJalurResponse>() {
            @Override
            public void onResponse(Call<DetailJalurResponse> call, Response<DetailJalurResponse> response) {
                if (response.body().status) {
                    DetailJalurResponse.DetailJalurModel dm = response.body().data.get(0);

                    namaGunungTv.setText(dm.getNamagunung());
                    tanggalJalurTv.setText(dm.getTanggalJalur());
                    kuotaEt.setText(dm.getTotalKuota());
                    keteranganEt.setText(dm.getKeterangan());

                    sisaKuota = dm.getKuotaSisa();
                    oldTotal = dm.getTotalKuota();

                    if (dm.getStatus().equals("buka")) {
                        radioGroup.check(R.id.buka);
                    } else {
                        radioGroup.check(R.id.tutup);
                    }

                }
            }

            @Override
            public void onFailure(Call<DetailJalurResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}