package com.hermawan.pendakian;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.BaseResponse;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BayarActivity extends AppCompatActivity {

    EditText tglBayar, nominal, namaRekening, bankPengirim;
    ImageView buktiBayar, tglTransferBtn;
    Button konfirmBayarBtn, pilihFotoBtn;

    ApiInterface apiInterface;

    String id;
    String tglBayarServer;

    private Uri buktiImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar);

        apiInterface = ApiClient.getClient();
        id = getIntent().getStringExtra("id_daftar");

        tglBayar = findViewById(R.id.tglTransferEt);
        tglTransferBtn = findViewById(R.id.tglTransferBtn);
        nominal = findViewById(R.id.nominalEt);
        namaRekening = findViewById(R.id.namaRekeningEt);
        bankPengirim = findViewById(R.id.bankPengirimEt);
        buktiBayar = findViewById(R.id.buktiBayarIv);
        konfirmBayarBtn = findViewById(R.id.konfirmBayarBtn);
        pilihFotoBtn = findViewById(R.id.pilihFotoBtn);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormatView = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));
        SimpleDateFormat simpleDateFormatServer = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                tglBayar.setText(simpleDateFormatView.format(calendar.getTime()));
                tglBayarServer = simpleDateFormatServer.format(calendar.getTime());
            }
        };

        tglTransferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                DatePickerDialog dp = new DatePickerDialog(v.getContext(), date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dp.getDatePicker().setMaxDate(c.getTime().getTime());
                dp.show();
            }
        });

        pilihFotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(BayarActivity.this)
                        .compress(3000)
                        .start();
            }
        });

        konfirmBayarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });
    }

    private void checkData() {
        String nominalA = nominal.getText().toString().trim();
        String namaPengirim = namaRekening.getText().toString().trim();
        String bank = bankPengirim.getText().toString().trim();

        if (!nominalA.isEmpty() && !namaPengirim.isEmpty() && !bank.isEmpty() && tglBayarServer.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Ada yang masih kosong. Silakan diisi.", Toast.LENGTH_LONG).show();
        } else {
            RequestBody idDaftar = RequestBody.create(MediaType.parse("text/plain"), id);
            RequestBody tglBayar = RequestBody.create(MediaType.parse("text/plain"), tglBayarServer);
            RequestBody namaRek = RequestBody.create(MediaType.parse("text/plain"), namaPengirim);
            RequestBody bankPeng = RequestBody.create(MediaType.parse("text/plain"), bank);
            RequestBody nomin = RequestBody.create(MediaType.parse("text/plain"), nominalA);

            //image
            File file = new File(buktiImg.getPath());
            RequestBody reqFile =  RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part f =  MultipartBody.Part.createFormData("image", file.getName(), reqFile);

            apiInterface.bayarPendakian(
                    idDaftar,
                    tglBayar,
                    nomin,
                    namaRek,
                    bankPeng,
                    f
            ).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response != null) {
                        if (response.body().status) {
                            onBackPressed();
                            Toast.makeText(BayarActivity.this, "Pembayaran Berhasil.", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(BayarActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    Log.e("daftar", t.getMessage());
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri fileUri = data.getData();

            buktiImg = fileUri;
            buktiBayar.setImageURI(fileUri);
        }
    }
}