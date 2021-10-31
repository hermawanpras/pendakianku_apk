package com.hermawan.pendakian;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.BaseResponse;
import com.hermawan.pendakian.api.response.PendaftaranPendakianResponse;
import com.hermawan.pendakian.api.response.UserResponse;
import com.hermawan.pendakian.preference.AppPreference;

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

public class DataPendakiActivity extends AppCompatActivity {

    EditText namaEt, alamatEt, noTelpEt, noIdentitasEt, tglLahirLeader;
    ImageView identitasIv, suratIv, tglLahirBtn;
    MaterialButton fotoBtn, suratBtn, simpanBtn;
    RadioGroup radioGroup;

    private Uri fotoImg, suratImg;
    int dipilih;
    String tglLahirServer;
    String jenis = "Laki-laki";

    ApiInterface apiInterface;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pendaki);



        apiInterface = ApiClient.getClient();

        namaEt = findViewById(R.id.namaLeaderEt);
        alamatEt = findViewById(R.id.alamatLeaderEt);
        noTelpEt = findViewById(R.id.noTelpLeaderEt);
        noIdentitasEt = findViewById(R.id.noIdentitasLeaderEt);
        identitasIv = findViewById(R.id.identitasIv);
        suratIv = findViewById(R.id.suratIv);
        tglLahirBtn = findViewById(R.id.btnTanggalLahirLeader);
        tglLahirLeader = findViewById(R.id.tglLahirLeader);
        radioGroup = findViewById(R.id.radioGroup);

        fotoBtn = findViewById(R.id.pilihFotoBtn);
        suratBtn = findViewById(R.id.pilihSuratBtn);
        simpanBtn = findViewById(R.id.simpanBtn);

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
                tglLahirLeader.setText(simpleDateFormatView.format(calendar.getTime()));
                tglLahirServer = simpleDateFormatServer.format(calendar.getTime());
            }
        };

        tglLahirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                DatePickerDialog dp = new DatePickerDialog(view.getContext(), date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dp.getDatePicker().setMaxDate(c.getTime().getTime());
                dp.show();
            }
        });

        fotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dipilih = 1;
                ImagePicker.Companion.with(DataPendakiActivity.this)
                        .compress(3000)
                        .start();
            }
        });

        suratBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dipilih = 2;
                ImagePicker.Companion.with(DataPendakiActivity.this)
                        .compress(3000)
                        .start();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.laki:
                        jenis = "Laki-laki";
                        break;
                    case R.id.perempuan:
                        jenis = "Perempuan";
                        break;
                }
            }
        });


        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(DataPendakiActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setTitle("Pesan");
                progressDialog.setMessage("Mohon tunggu sebentar...");
                progressDialog.show();

                UserResponse.UserModel u = AppPreference.getUser(getApplicationContext());

                apiInterface.getIdDaftar(
                        u.idUser
                ).enqueue(new Callback<PendaftaranPendakianResponse>() {
                    @Override
                    public void onResponse(Call<PendaftaranPendakianResponse> call, Response<PendaftaranPendakianResponse> response) {
                        if (response != null) {
                            if (response.body().status) {
                                simpan(response.body().data.get(0).idDaftar,
                                        noIdentitasEt.getText().toString().trim(),
                                        namaEt.getText().toString().trim(),
                                        tglLahirServer,
                                        jenis,
                                        noTelpEt.getText().toString().trim(),
                                        alamatEt.getText().toString().trim()
                                        );
                            } else {
                                Toast.makeText(DataPendakiActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PendaftaranPendakianResponse> call, Throwable t) {
                        Log.e("daftar", t.getMessage());
                    }
                });
            }
        });
    }

    private void simpan(String id, String noId, String nama, String tgl, String jk, String no, String al) {
        RequestBody idDaftar = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody noIdentitas = RequestBody.create(MediaType.parse("text/plain"), noId);
        RequestBody namaPendaki = RequestBody.create(MediaType.parse("text/plain"), nama);
        RequestBody tglLahir = RequestBody.create(MediaType.parse("text/plain"), tgl);
        RequestBody jkPendaki = RequestBody.create(MediaType.parse("text/plain"), jk);
        RequestBody noTelp = RequestBody.create(MediaType.parse("text/plain"), no);
        RequestBody statusPendaki = RequestBody.create(MediaType.parse("text/plain"), "ketua");
        RequestBody alamat = RequestBody.create(MediaType.parse("text/plain"), al);

        //image
        File file = new File(fotoImg.getPath());
        RequestBody reqFile =  RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part f =  MultipartBody.Part.createFormData("image", file.getName(), reqFile);

        //image
        File file1 = new File(suratImg.getPath());
        RequestBody reqFile1 =  RequestBody.create(MediaType.parse("image/*"), file1);
        MultipartBody.Part f1 =  MultipartBody.Part.createFormData("image1", file1.getName(), reqFile1);

        apiInterface.simpanPendaki(
                noIdentitas,
                idDaftar,
                namaPendaki,
                tglLahir,
                jkPendaki,
                alamat,
                noTelp,
                statusPendaki,
                f,
                f1
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Intent i = new Intent(DataPendakiActivity.this, TambahAnggotaActivity.class);
                        i.putExtra("id_daftar", id);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(DataPendakiActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("daftar", t.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri fileUri = data.getData();

            if (dipilih == 1) {
                fotoImg = fileUri;
                identitasIv.setImageURI(fileUri);
            } else {
                suratImg = fileUri;
                suratIv.setImageURI(fileUri);
            }
        }
    }
}
