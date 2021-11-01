package com.hermawan.pendakian;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.PendaftaranPendakianResponse;
import com.hermawan.pendakian.api.response.PendakiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPendakiActivity extends AppCompatActivity {

    TextView noIdentitasTv, namaTv, tglLahirTv, jkTv, alamatTv, noTelpTv, statusTv;
    ImageView identitasIv, ketKesIv;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pendaki);

        noIdentitasTv = findViewById(R.id.noIdentitasTv);
        namaTv = findViewById(R.id.namaTv);
        tglLahirTv = findViewById(R.id.tglLahirTv);
        jkTv = findViewById(R.id.jkTv);
        alamatTv = findViewById(R.id.alamatTv);
        noTelpTv = findViewById(R.id.noTelpTv);
        statusTv = findViewById(R.id.statusTv);

        identitasIv = findViewById(R.id.fotoIdentitasTv);
        ketKesIv = findViewById(R.id.fotoSuratKesTv);

        apiInterface = ApiClient.getClient();

        String idPendaki = getIntent().getStringExtra("id_pendaki");
        String idDaftar = getIntent().getStringExtra("id_daftar");

        getData(idPendaki, idDaftar);

    }

    void getData(String a, String b) {
        apiInterface.getPendaki(a, b).enqueue(new Callback<PendakiResponse>() {
            @Override
            public void onResponse(Call<PendakiResponse> call, Response<PendakiResponse> response) {
                if (response.body().status) {
                     PendakiResponse.PendakiModel pm = response.body().data.get(0);

                    noIdentitasTv.setText(pm.getNoIdentitas());
                    namaTv.setText(pm.getNamaPendaki());
                    tglLahirTv.setText(pm.getTglLahir());
                    statusTv.setText(pm.getStatusPendaki());
                    jkTv.setText(pm.getJkPendaki().equals("L") ? "Laki-laki" : "Perempuan");
                    alamatTv.setText(pm.getAlamatPendaki());
                    noTelpTv.setText(pm.getNoTelp());

                    Glide.with(getApplicationContext())
                            .load( getString(R.string.base_url) + getString(R.string.link_identitas) + pm.getIdentitasPendaki())
                            .fitCenter()
                            .into(identitasIv);

                    Glide.with(getApplicationContext())
                            .load(getString(R.string.base_url) + getString(R.string.link_surat) + pm.getSuratKetKes())
                            .fitCenter()
                            .into(ketKesIv);
                }
            }

            @Override
            public void onFailure(Call<PendakiResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}