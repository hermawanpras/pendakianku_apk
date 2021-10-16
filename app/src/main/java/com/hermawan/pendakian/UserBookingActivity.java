package com.hermawan.pendakian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.hermawan.pendakian.adapter.KuotaAdapter;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.KuotaResponse;
import com.hermawan.pendakian.api.response.KuotayJalurResponse;
import com.hermawan.pendakian.model.KuotaModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserBookingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ApiInterface apiInterface;
    private int mYear, mMonth, mDay;

    private String idInfoJalur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking);

        apiInterface = ApiClient.getClient();

        idInfoJalur = getIntent().getStringExtra("ID_JALUR");

//        Spinner spinner_gunung = (Spinner) findViewById(R.id.spinner_gunung);
        EditText tgl_mulai = (EditText) findViewById(R.id.tgl_mulai);
        EditText tgl_selesai = (EditText) findViewById(R.id.tgl_selesai);
        Button lanjut = (Button) findViewById(R.id.btn_pesan);
        ImageView btn_mulai = (ImageView) findViewById(R.id.btn_mulai);
        ImageView btn_selesai = (ImageView) findViewById(R.id.btn_selesai);
        recyclerView = findViewById(R.id.recyclerViewKuota);

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
//                new DatePickerDialog(v.getContext(), date, calendar
//                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
//                        calendar.get(Calendar.DAY_OF_MONTH))
//                        .getDatePicker().setMinDate(Calendar.getInstance().getTime().getTime())
//                        .show();
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

        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),DataPendakiActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getKuota();
    }

    public void getKuota() {
        apiInterface.getKuota(idInfoJalur).enqueue(new Callback<KuotaResponse>() {
            @Override
            public void onResponse(Call<KuotaResponse> call, Response<KuotaResponse> response) {
                if (response.body().status) {
                    getKuotayJalur(response.body().data.get(0).kuota, response.body().date);
                }
            }

            @Override
            public void onFailure(Call<KuotaResponse> call, Throwable t) {

            }
        });
    }

    public void getKuotayJalur(String kuota, String tanggal) {
        apiInterface.getKuotayInfoJalur(idInfoJalur).enqueue(new Callback<KuotayJalurResponse>() {
            @Override
            public void onResponse(Call<KuotayJalurResponse> call, Response<KuotayJalurResponse> response) {
                if (response.body().status) {
                    List<KuotaModel> list = new ArrayList<>();
                    List<KuotaModel> newList = new ArrayList<>();
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormatServer = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    for (int i = 0; i < 30; i++) {
                        c.add(Calendar.DATE, 1);
                        list.add(new KuotaModel(simpleDateFormatServer.format(c.getTime()), kuota));
                    }

                    for (int i = 0; i < 30; i++) {
                        for (int j = 0; j < response.body().data.size(); j++) {
                            if (list.get(i).tgl_pendakian.equalsIgnoreCase(response.body().data.get(j).tanggal)) {
                                list.get(i).kuota = String.valueOf(Integer.parseInt(kuota) - Integer.parseInt(response.body().data.get(j).kuota));
                            }
                        }
                    }
                    recyclerView.setAdapter(new KuotaAdapter(list));
                }
            }

            @Override
            public void onFailure(Call<KuotayJalurResponse> call, Throwable t) {

            }
        });
    }
}
