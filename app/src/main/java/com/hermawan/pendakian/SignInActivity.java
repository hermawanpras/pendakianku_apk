package com.hermawan.pendakian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.BaseResponse;
import com.hermawan.pendakian.api.response.UserResponse;
import com.hermawan.pendakian.preference.AppPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    private ApiInterface apiInterface;
    TextView btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        apiInterface = ApiClient.getClient();

        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);
        Button signIn = findViewById(R.id.signIn);
        btnDaftar = findViewById(R.id.btnDaftar);

        if (AppPreference.getUser(this) != null) {
            if (AppPreference.getUser(this).roleUser.equals("admin")) {
                startActivity(new Intent(SignInActivity.this, AdminMainMenuActivity.class));
            } else {
                startActivity(new Intent(SignInActivity.this, UserMainMenuActivity.class));
            }
        }

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiInterface.login(
                        email.getText().toString(),
                        password.getText().toString()
                ).enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response != null) {
                            if (response.body().status) {
                                AppPreference.saveUser(SignInActivity.this, response.body().data.get(0));

                                if (response.body().data.get(0).roleUser.equals("admin")) {
                                    startActivity(new Intent(SignInActivity.this, AdminMainMenuActivity.class));
                                    Toast.makeText(SignInActivity.this, "Login Berhasil", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    startActivity(new Intent(SignInActivity.this, UserMainMenuActivity.class));
                                    Toast.makeText(SignInActivity.this, "Login Berhasil", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                                updateToken(response.body().data.get(0).idUser);
                            } else {
                                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.e("login", t.getMessage());
                    }
                });
            }
        });
    }

    public void updateToken(String idUser) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TOKEN", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        apiInterface.postToken(
                                idUser,
                                token
                        ).enqueue(new Callback<BaseResponse>() {
                            @Override
                            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                Log.e("token", response.body().message);
                            }

                            @Override
                            public void onFailure(Call<BaseResponse> call, Throwable t) {
                                Log.e("token", t.getMessage());
                            }
                        });
                    }
                });
    }
}
