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

import com.bumptech.glide.Glide;
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

public class EditProfilActivity extends AppCompatActivity {

    EditText namaEt, emailEt, alamatEt, passwordEt, ulangiPasswordEt, noTelpEt;
    ImageView fotoIv;
    Button pilihFotoBtn;
    Button simpanBtn;
    private Uri fotoImg;

    ApiInterface apiInterface;
    ProgressDialog progressDialog;
    UserResponse.UserModel u;

    String fotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        apiInterface = ApiClient.getClient();
        u = AppPreference.getUser(getApplicationContext());

        namaEt = findViewById(R.id.namaEt);
        emailEt = findViewById(R.id.emailEt);
        alamatEt = findViewById(R.id.alamatEt);
        passwordEt = findViewById(R.id.passwordEt);
        ulangiPasswordEt = findViewById(R.id.ulangiPasswordEt);
        fotoIv = findViewById(R.id.fotoProfilIv);
        pilihFotoBtn = findViewById(R.id.pilihFotoBtn);
        simpanBtn = findViewById(R.id.simpanBtn);
        noTelpEt = findViewById(R.id.telpEt);

        setView();

        pilihFotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(EditProfilActivity.this)
                        .compress(3000)
                        .start();
            }
        });

        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData(u.idUser);
            }
        });
    }

    private void checkData(String id) {
        if (emailEt.getText().toString().isEmpty() && namaEt.getText().toString().isEmpty() && alamatEt.getText().toString().isEmpty()
        && passwordEt.getText().toString().isEmpty() && ulangiPasswordEt.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Ada field yang masih kosong.", Toast.LENGTH_SHORT).show();
        } else {
            if (passwordEt.getText().toString().equals(ulangiPasswordEt.getText().toString())) {
                progressDialog = new ProgressDialog(EditProfilActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setTitle("Pesan");
                progressDialog.setMessage("Mohon tunggu sebentar...");
                progressDialog.show();

                simpan(
                      id,
                        namaEt.getText().toString().trim(),
                        passwordEt.getText().toString().trim(),
                        emailEt.getText().toString().trim(),
                        noTelpEt.getText().toString().trim(),
                        alamatEt.getText().toString().trim()
                );
            }
        }
    }

    private void setView() {
        Glide.with(getApplicationContext())
                .load(getString(R.string.base_url) + getString(R.string.link_profile) + u.fotoProfil)
                .fitCenter()
                .placeholder(R.drawable.ic_gambar)
                .into(fotoIv);

        namaEt.setText(u.username);
        emailEt.setText(u.email);
        alamatEt.setText(u.alamat);
        passwordEt.setText(u.password);
        ulangiPasswordEt.setText(u.password);
        noTelpEt.setText(u.noTelp);
    }

    private void simpan(String id, String nama, String pass, String e, String telp, String a) {
        RequestBody idUser = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody namaUser = RequestBody.create(MediaType.parse("text/plain"), nama);
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), pass);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), e);
        RequestBody no = RequestBody.create(MediaType.parse("text/plain"), telp);
        RequestBody alamat = RequestBody.create(MediaType.parse("text/plain"), a);

        //image
        File file = new File(fotoImg.getPath());
        RequestBody reqFile =  RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part f =  MultipartBody.Part.createFormData("image", file.getName(), reqFile);

        apiInterface.editProfil(
                idUser,
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

                        AppPreference.removeUser(getApplicationContext());
                        UserResponse.UserModel r = new UserResponse.UserModel(id, nama, pass, e, a, telp, u.roleUser, fotoPath);
                        AppPreference.saveUser(EditProfilActivity.this, r);

                        onBackPressed();
                        Toast.makeText(EditProfilActivity.this, "Update berhasil.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(EditProfilActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
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
            fotoPath = new File(fileUri.getPath()).getName();

            fotoImg = fileUri;
            fotoIv.setImageURI(fileUri);
        }
    }
}