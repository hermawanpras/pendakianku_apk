package com.hermawan.pendakian;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.BaseResponse;
import com.hermawan.pendakian.api.response.BlacklistResponse;
import com.hermawan.pendakian.api.response.PendaftaranPendakianResponse;
import com.hermawan.pendakian.api.response.PendakiResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class TambahBlacklistActivity extends AppCompatActivity {

    EditText tglMulaiEt, tglSelesaiEt, noIdentCariEt, ketEt;
    Button cariBtn, simpanBtn;
    ImageView mulaiBtn, selesaiBtn;
    LinearLayout layout;
    TextView namaTv, noTv;

    String tglMulaiServer, tglSelesaiServer;

    TextView tidakTv;
    String idPendaki;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_blacklist);

        apiInterface = ApiClient.getClient();

        tglMulaiEt = findViewById(R.id.tgl_mulai);
        tglSelesaiEt = findViewById(R.id.tgl_selesai);
        noIdentCariEt = findViewById(R.id.noIdenEt);
        cariBtn = findViewById(R.id.cariBtn);
        simpanBtn = findViewById(R.id.simpanBtn);
        mulaiBtn = findViewById(R.id.btn_mulai);
        selesaiBtn = findViewById(R.id.btn_selesai);
        ketEt = findViewById(R.id.keteranganEt);
        layout = findViewById(R.id.formLayout);
        layout.setVisibility(View.INVISIBLE);
        namaTv = findViewById(R.id.namaPendakiTv);
        noTv = findViewById(R.id.noIdentitasTv);
        tidakTv = findViewById(R.id.tidakTv);
        tidakTv.setVisibility(View.INVISIBLE);

        cariBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cari(noIdentCariEt.getText().toString().trim());
            }
        });

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
                tglMulaiEt.setText(simpleDateFormatView.format(calendar.getTime()));
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
                tglSelesaiEt.setText(simpleDateFormatView.format(calendarSelesai.getTime()));
                tglSelesaiServer = simpleDateFormatServer.format(calendarSelesai.getTime());
            }
        };

        mulaiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                DatePickerDialog dp = new DatePickerDialog(v.getContext(), date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dp.getDatePicker().setMinDate(Calendar.getInstance().getTime().getTime());
                dp.getDatePicker().setMaxDate(c.getTime().getTime());
                dp.show();
            }
        });

        selesaiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dp = new DatePickerDialog(v.getContext(), date1, calendarSelesai
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendarSelesai.get(Calendar.DAY_OF_MONTH));
                dp.getDatePicker().setMinDate(Calendar.getInstance().getTime().getTime());
                dp.show();
            }
        });

        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tambahBl();
            }
        });
    }

    void tambahBl() {
        apiInterface.tambahBlacklist(
                idPendaki,
                tglMulaiServer,
                tglSelesaiServer,
                ketEt.getText().toString().trim(),
                "1"
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        onBackPressed();
                        Toast.makeText(TambahBlacklistActivity.this, "Blacklist berhasil ditambahkan", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(TambahBlacklistActivity.this, "Gagal menambahkan blacklist", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });
    }

    void cari(String no) {
        apiInterface.search(
                no
        ).enqueue(new Callback<PendakiResponse>() {
            @Override
            public void onResponse(Call<PendakiResponse> call, Response<PendakiResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        PendakiResponse.PendakiModel p = response.body().data.get(0);

                        idPendaki = p.getIdPendaki();
                        Log.e(TAG, "onResponse: " + p.getNamaPendaki() );

                        tidakTv.setVisibility(View.GONE);
                        layout.setVisibility(View.VISIBLE);

                        namaTv.setText(p.getNamaPendaki());
                        noTv.setText(p.getNoIdentitas());

                    } else {
                        tidakTv.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<PendakiResponse> call, Throwable t) {
                Log.e("daftar", t.getMessage());
            }
        });
    }
}