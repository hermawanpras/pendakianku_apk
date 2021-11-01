package com.hermawan.pendakian;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.BaseResponse;
import com.hermawan.pendakian.api.response.PembayaranResponse;
import com.hermawan.pendakian.api.response.PendakiResponse;
import com.hermawan.pendakian.preference.AppPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPembayaranActivity extends AppCompatActivity {

    TextView tglTransferTv, namaRekTv, bankPengTv, statusTv, nominalTv;
    ImageView buktiIv;
    Button validasiBayarBtn;

    ApiInterface apiInterface;
    String idPembayaran, idDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pembayaran);

        tglTransferTv = findViewById(R.id.tglTransferTv);
        namaRekTv = findViewById(R.id.namaRekeningTv);
        bankPengTv = findViewById(R.id.bankPengirimTv);
        statusTv = findViewById(R.id.statusTv);
        nominalTv = findViewById(R.id.nominalTv);
        buktiIv = findViewById(R.id.buktiBayarIv);
        validasiBayarBtn = findViewById(R.id.validasiBayarBtn);

        validasiBayarBtn.setVisibility(View.INVISIBLE);

        apiInterface = ApiClient.getClient();

        idPembayaran = getIntent().getStringExtra("id_pembayaran");
        idDaftar = getIntent().getStringExtra("id_daftar");

        getData(idPembayaran);

        validasiBayarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiInterface.validasiPembayaran(idPembayaran, idDaftar).enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.body().status) {
                            onBackPressed();
                            Toast.makeText(getApplicationContext(), "Validasi pembayaran berhasil", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    void getData(String id) {
        apiInterface.getPembayaran(id).enqueue(new Callback<PembayaranResponse>() {
            @Override
            public void onResponse(Call<PembayaranResponse> call, Response<PembayaranResponse> response) {
                if (response.body().status) {
                    PembayaranResponse.PembayaranModel pm = response.body().data.get(0);

                    tglTransferTv.setText(pm.getTanggalBayar());
                    namaRekTv.setText(pm.getNamaRekening());
                    nominalTv.setText("Rp." + pm.getNominal());
                    statusTv.setText(pm.getStatus().equals("0") ? "Pending" : "Sukses");
                    bankPengTv.setText(pm.getBankPengirim());

                    Glide.with(getApplicationContext())
                            .load( getString(R.string.base_url) + getString(R.string.link_pembayaran) + pm.getBukti())
                            .centerCrop()
                            .into(buktiIv);

                    if (pm.getStatus().equals("0") && AppPreference.getUser(DetailPembayaranActivity.this).roleUser.equals("admin")) {
                        validasiBayarBtn.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<PembayaranResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}