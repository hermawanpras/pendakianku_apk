package com.hermawan.pendakian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hermawan.pendakian.adapter.DetailJalurAdapter;
import com.hermawan.pendakian.adapter.KuotaAdapter;
import com.hermawan.pendakian.adapter.KuotaJalurAdapter;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.BaseResponse;
import com.hermawan.pendakian.api.response.DetailJalurResponse;
import com.hermawan.pendakian.api.response.KuotaResponse;
import com.hermawan.pendakian.api.response.KuotayJalurResponse;
import com.hermawan.pendakian.api.response.UserResponse;
import com.hermawan.pendakian.model.KuotaModel;
import com.hermawan.pendakian.preference.AppPreference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class UserBookingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ApiInterface apiInterface;
    private int mYear, mMonth, mDay;

    Button btnLanjut, btnCekTanggal, btnCekLengkap;

    private String idInfoJalur;
    String tglMulaiServer, tglSelesaiServer;

    boolean tanggalValid = false;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking);

        progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Pesan");
        progressDialog.setMessage("Mohon tunggu sebentar...");

        apiInterface = ApiClient.getClient();

        idInfoJalur = getIntent().getStringExtra("ID_INFO_JALUR");

//        Spinner spinner_gunung = (Spinner) findViewById(R.id.spinner_gunung);
        EditText tgl_mulai = (EditText) findViewById(R.id.tgl_mulai);
        EditText tgl_selesai = (EditText) findViewById(R.id.tgl_selesai);
        btnLanjut = (Button) findViewById(R.id.btn_pesan);
        btnCekTanggal = (Button) findViewById(R.id.cekTanggalBtn);
        btnCekLengkap = (Button) findViewById(R.id.cekLengkapBtn);
        ImageView btn_mulai = (ImageView) findViewById(R.id.btn_mulai);
        ImageView btn_selesai = (ImageView) findViewById(R.id.btn_selesai);
        recyclerView = findViewById(R.id.recyclerViewKuota);

        btnLanjut.setVisibility(View.INVISIBLE);

        Calendar calendar = Calendar.getInstance();
        Calendar calendarSelesai = Calendar.getInstance();
        SimpleDateFormat simpleDateFormatView = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));
        SimpleDateFormat simpleDateFormatServer = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                tgl_mulai.setText(simpleDateFormatView.format(calendar.getTime()));
                tglMulaiServer = simpleDateFormatServer.format(calendar.getTime());
            }
        };

        DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                calendarSelesai.set(Calendar.YEAR, year);
                calendarSelesai.set(Calendar.MONTH, monthOfYear);
                calendarSelesai.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                tgl_selesai.setText(simpleDateFormatView.format(calendarSelesai.getTime()));
                tglSelesaiServer = simpleDateFormatServer.format(calendarSelesai.getTime());
            }
        };

        btn_mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, 30);
                DatePickerDialog dp = new DatePickerDialog(v.getContext(), date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dp.getDatePicker().setMinDate(Calendar.getInstance().getTime().getTime());
                dp.getDatePicker().setMaxDate(c.getTime().getTime());
                dp.show();
            }
        });

        btn_selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dp = new DatePickerDialog(v.getContext(), date1, calendarSelesai
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendarSelesai.get(Calendar.DAY_OF_MONTH));
                dp.getDatePicker().setMinDate(Calendar.getInstance().getTime().getTime());
                dp.show();
            }
        });

        btnCekTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekTanggal();
            }
        });

        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tanggalValid) {
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String sekarang = sdf.format(c.getTime());

                    UserResponse.UserModel u = AppPreference.getUser(getApplicationContext());

                    AlertDialog.Builder builder = new AlertDialog.Builder(UserBookingActivity.this);
                    builder.setTitle("Konfirmasi Tanggal");
                    builder.setMessage("Anda telah memilih booking pendakian dari tanggal " + tgl_mulai.getText().toString() + " sampai " + tgl_selesai.getText().toString() + ". Apa data sudah benar?");
                    builder.setPositiveButton("LANJUT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            apiInterface.daftarDaki(
                                    u.idUser,
                                    idInfoJalur,
                                    sekarang,
                                    tglMulaiServer,
                                    tglSelesaiServer,
                                    "0").enqueue(new Callback<BaseResponse>() {
                                @Override
                                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                    if (response.body().status) {
                                        Intent intent = new Intent(UserBookingActivity.this, DataPendakiActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(UserBookingActivity.this, "Terjadi kesalahan.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<BaseResponse> call, Throwable t) {

                                }
                            });

                        }
                    });
                    builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(UserBookingActivity.this, "Dibatalkan", Toast.LENGTH_SHORT).show();
                        }
                    });

                    AlertDialog ad = builder.create();
                    ad.show();
                } else {
                    Toast.makeText(getApplicationContext(), "Jadwal Anda tidak valid. Ada tanggal dengan jalur tutup.", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCekLengkap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JadwalLengkapActivity.class);
                String namaGunung = "";
                if (idInfoJalur.equals("1")) {
                    namaGunung = "Gn. Panderman";
                } else {
                    namaGunung = "Gn. Buthak";
                }

                intent.putExtra("NAMA_GUNUNG", namaGunung);
                intent.putExtra("ID_INFO_JALUR", idInfoJalur);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    private void konfirmasiDaftar() {

    }

    public void cekTanggal() {
        apiInterface.getTanggalDaki(
                idInfoJalur,
                tglMulaiServer,
                tglSelesaiServer).enqueue(new Callback<DetailJalurResponse>() {
            @Override
            public void onResponse(Call<DetailJalurResponse> call, Response<DetailJalurResponse> response) {
                if (response.body().status) {
                    List<DetailJalurResponse.DetailJalurModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getStatus().contains("tutup")) {
                            Toast.makeText(getApplicationContext(), "Jadwal Anda tidak valid. Ada tangal dengan jalur tutup.", Toast.LENGTH_LONG).show();
                            btnLanjut.setVisibility(View.INVISIBLE);
                            tanggalValid = false;
                            break;
                        } else {
                            btnLanjut.setVisibility(View.VISIBLE);
                            tanggalValid = true;
                        }
                    }

                    recyclerView.setAdapter(new KuotaJalurAdapter(list, getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<DetailJalurResponse> call, Throwable t) {

            }
        });
    }
}
