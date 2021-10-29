package com.hermawan.pendakian;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.BaseResponse;
import com.hermawan.pendakian.api.response.DetailJalurResponse;
import com.hermawan.pendakian.api.response.UserResponse;
import com.hermawan.pendakian.preference.AppPreference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahDetailJalurActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

   String[] gunung = {"Buthak", "Panderman"};
   Button tambahBtn;
   EditText ketEt, kuotaEt, tglJalur;
   ImageView btnJalur;
   RadioGroup radioGroup;

   String status = "buka";
   String id_info_jalur = "1";
   String tanggalServer;

   ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_detail_jalur);

        tambahBtn = findViewById(R.id.tambahBtn);
        ketEt = findViewById(R.id.keteranganEt);
        kuotaEt = findViewById(R.id.kuotaEt);
        tglJalur = findViewById(R.id.tglJalur);
        btnJalur = findViewById(R.id.btnJalur);
        radioGroup = findViewById(R.id.radioGroup);

        apiInterface = ApiClient.getClient();

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gunung);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

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
                tglJalur.setText(simpleDateFormatView.format(calendar.getTime()));
                tanggalServer = simpleDateFormatServer.format(calendar.getTime());
            }
        };

        btnJalur.setOnClickListener(new View.OnClickListener() {
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

        tambahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tambah();
            }
        });
    }

    private void tambah() {
        apiInterface.getTanggal(
                id_info_jalur,
                tanggalServer
        ).enqueue(new Callback<DetailJalurResponse>() {
            @Override
            public void onResponse(Call<DetailJalurResponse> call, Response<DetailJalurResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        Toast.makeText(TambahDetailJalurActivity.this, "Detail Jalur pada " + tglJalur.getText().toString() + " sudah ada", Toast.LENGTH_LONG).show();
                    } else {
                        apiInterface.tambahDetailJalur(
                                id_info_jalur,
                                kuotaEt.getText().toString(),
                                status,
                                tanggalServer,
                                ketEt.getText().toString()
                        ).enqueue(new Callback<BaseResponse>() {
                            @Override
                            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                if (response != null) {
                                    if (response.body().status) {
                                        startActivity(new Intent(TambahDetailJalurActivity.this, AdminHomeActivity.class));
                                        Toast.makeText(TambahDetailJalurActivity.this, "Detail Jalur berhasil ditambahkan", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<BaseResponse> call, Throwable t) {
                                Log.e("login", t.getMessage());
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<DetailJalurResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                id_info_jalur = "1";
                break;
            case 1:
                id_info_jalur = "2";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}