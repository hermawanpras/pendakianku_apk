package com.hermawan.pendakian;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.BaseResponse;
import com.hermawan.pendakian.api.response.UserResponse;
import com.hermawan.pendakian.preference.AppPreference;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahAdminActivity extends AppCompatActivity {

    EditText namaEt, emailEt, alamatEt, passwordEt, noTelpEt;
    ImageView fotoIv;
    Button pilihFotoBtn;
    Button simpanBtn;
    private Uri fotoImg;

    ApiInterface apiInterface;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_admin);

        apiInterface = ApiClient.getClient();

        namaEt = findViewById(R.id.namaEt);
        emailEt = findViewById(R.id.emailEt);
        alamatEt = findViewById(R.id.alamatEt);
        passwordEt = findViewById(R.id.passwordEt);
        fotoIv = findViewById(R.id.fotoProfilIv);
        pilihFotoBtn = findViewById(R.id.pilihFotoBtn);
        simpanBtn = findViewById(R.id.simpanBtn);
        noTelpEt = findViewById(R.id.telpEt);

        pilihFotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(TambahAdminActivity.this)
                        .compress(3000)
                        .start();
            }
        });

        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });
    }

    private void checkData() {
        if (emailEt.getText().toString().isEmpty() && namaEt.getText().toString().isEmpty() && alamatEt.getText().toString().isEmpty()
                && passwordEt.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Ada field yang masih kosong.", Toast.LENGTH_SHORT).show();
        } else {

                progressDialog = new ProgressDialog(TambahAdminActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setTitle("Pesan");
                progressDialog.setMessage("Mohon tunggu sebentar...");
                progressDialog.show();

                simpan(
                        namaEt.getText().toString().trim(),
                        passwordEt.getText().toString().trim(),
                        emailEt.getText().toString().trim(),
                        noTelpEt.getText().toString().trim(),
                        alamatEt.getText().toString().trim()
                );

        }
    }

    private void simpan(String nama, String pass, String e, String telp, String a) {
        RequestBody namaUser = RequestBody.create(MediaType.parse("text/plain"), nama);
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), pass);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), e);
        RequestBody no = RequestBody.create(MediaType.parse("text/plain"), telp);
        RequestBody alamat = RequestBody.create(MediaType.parse("text/plain"), a);

        //image
        File file = new File(fotoImg.getPath());
        RequestBody reqFile =  RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part f =  MultipartBody.Part.createFormData("image", file.getName(), reqFile);

        apiInterface.tambahAdmin(
                namaUser,
                password,
                email,
                no,
                alamat,
                f
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        onBackPressed();
                        Toast.makeText(TambahAdminActivity.this, "Update berhasil.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(TambahAdminActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
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

            fotoImg = fileUri;
            fotoIv.setImageURI(fileUri);
        }
    }
}