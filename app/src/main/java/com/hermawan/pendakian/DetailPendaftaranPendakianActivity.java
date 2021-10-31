package com.hermawan.pendakian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.hermawan.pendakian.adapter.DetailJalurAdapter;
import com.hermawan.pendakian.adapter.DetailPendakiAdapter;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.BaseResponse;
import com.hermawan.pendakian.api.response.DetailJalurResponse;
import com.hermawan.pendakian.api.response.PembayaranResponse;
import com.hermawan.pendakian.api.response.PendaftaranPendakianResponse;
import com.hermawan.pendakian.api.response.PendakiResponse;
import com.hermawan.pendakian.api.response.UserResponse;
import com.hermawan.pendakian.preference.AppPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPendaftaranPendakianActivity extends AppCompatActivity {

    TextView namaUserTv, tanggalDakiTv, tanggalDaftarTv, namaGunungTv;
    Chip chip;
    Button validasiDaftarBtn;
    ApiInterface apiInterface;
    RecyclerView rv;
    UserResponse.UserModel user;

    String idDaftar, idInfoJalur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pendaftaran_pendakian);

        user = AppPreference.getUser(getApplicationContext());

        namaGunungTv = findViewById(R.id.namaGunungTv);
        namaUserTv = findViewById(R.id.namaUserTv);
        tanggalDaftarTv = findViewById(R.id.tanggalDaftarTv);
        tanggalDakiTv = findViewById(R.id.tanggalDakiTv);
        chip = findViewById(R.id.statusChip);
        validasiDaftarBtn = findViewById(R.id.validasiDaftarBtn);
        rv = findViewById(R.id.rv);

        validasiDaftarBtn.setEnabled(false);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        apiInterface = ApiClient.getClient();

        idDaftar = getIntent().getStringExtra("id_daftar");
        idInfoJalur = getIntent().getStringExtra("id_info_jalur");

        getData(idDaftar, idInfoJalur);

        getDetailPendaki(idDaftar);
    }

    void setButton(String role, String status) {
        if (role.equals("admin")) {
            validasiDaftarBtn.setText("VALIDASI PENDAFTARAN");
            validasiDaftarBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    apiInterface.validasiPendaftaran(
                            idDaftar
                    ).enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                            if (response != null) {
                                if (response.body().status) {
                                    Toast.makeText(DetailPendaftaranPendakianActivity.this, "Validasi pendaftaran berhasil", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(DetailPendaftaranPendakianActivity.this, "Validasi pendaftaran gagal", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {
                            Log.e("login", t.getMessage());
                        }
                    });
                }
            });
        } else {
            if (status.equals("2")) {
                validasiDaftarBtn.setText("DETAIL PEMBAYARAN");
                validasiDaftarBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        apiInterface.getPembayaranByIdDaftar(idDaftar).enqueue(new Callback<PembayaranResponse>() {
                            @Override
                            public void onResponse(Call<PembayaranResponse> call, Response<PembayaranResponse> response) {
                                if (response.body().status) {
                                    Intent i = new Intent(DetailPendaftaranPendakianActivity.this, DetailPembayaranActivity.class);
                                    i.putExtra("id_daftar", idDaftar);
                                    i.putExtra("id_pembayaran", response.body().data.get(0).getIdPembayaran());
                                    startActivity(i);
                                }
                            }

                            @Override
                            public void onFailure(Call<PembayaranResponse> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
            } else {
                validasiDaftarBtn.setText("BAYAR");
                validasiDaftarBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(DetailPendaftaranPendakianActivity.this, BayarActivity.class);
                        i.putExtra("id_daftar", idDaftar);
                        startActivity(i);
                    }
                });
            }

        }

    }

    private void getData(String a, String b) {
        apiInterface.cekDaftar(a, b).enqueue(new Callback<PendaftaranPendakianResponse>() {
            @Override
            public void onResponse(Call<PendaftaranPendakianResponse> call, Response<PendaftaranPendakianResponse> response) {
                if (response.body().status) {
                    PendaftaranPendakianResponse.PendaftaranPendakianModel pm = response.body().data.get(0);

                    namaGunungTv.setText(pm.getNamaGunung());
                    namaUserTv.setText(pm.getNamaUser());
                    tanggalDaftarTv.setText("Didaftarkan pada " + pm.getTglDaftarPendakian());
                    tanggalDakiTv.setText("Mendaki pada " + pm.getTglMulaiPendakian() + " - " + pm.getTglSelesaiPendakian());

                    if (pm.getStatusPendakian().equals("0")) {
                        chip.setText("Belum divalidasi");
                    } else if (pm.getStatusPendakian().equals("1")) {
                        chip.setText("Belum dibayar");
                    }else if (pm.getStatusPendakian().equals("2")) {
                        chip.setText("Sudah dibayar");
                    } else if (pm.getStatusPendakian().equals("3")) {
                        chip.setText("Sudah cekin");
                    }else if (pm.getStatusPendakian().equals("4")) {
                        chip.setText("Selesai");
                    }

                    if (pm.getStatusPendakian().equals("0") && user.roleUser.equals("admin")) {
                        validasiDaftarBtn.setEnabled(true);
                    } else if ((pm.getStatusPendakian().equals("2")
                            || pm.getStatusPendakian().equals("3")
                    || pm.getStatusPendakian().equals("4"))&& user.roleUser.equals("user")) {
                        validasiDaftarBtn.setEnabled(true);
                    }

                    setButton(user.roleUser, pm.getStatusPendakian());
                }
            }

            @Override
            public void onFailure(Call<PendaftaranPendakianResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDetailPendaki(String id) {
        apiInterface.getPendakiByIdDaftar(id).enqueue(new Callback<PendakiResponse>() {
            @Override
            public void onResponse(Call<PendakiResponse> call, Response<PendakiResponse> response) {
                if (response.body().status) {
                    List<PendakiResponse.PendakiModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    rv.setAdapter(new DetailPendakiAdapter(list, DetailPendaftaranPendakianActivity.this));
                }
            }

            @Override
            public void onFailure(Call<PendakiResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}