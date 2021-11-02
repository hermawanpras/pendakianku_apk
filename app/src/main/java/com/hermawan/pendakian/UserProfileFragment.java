package com.hermawan.pendakian;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.BaseResponse;
import com.hermawan.pendakian.api.response.UserResponse;
import com.hermawan.pendakian.preference.AppPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileFragment extends Fragment {

    private TextView tvNama, tvAlamat, tvTelepon, tvEmail;
    private MaterialButton btnKeluar;

    private ApiInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        tvNama = view.findViewById(R.id.txtViewNama);
        tvAlamat = view.findViewById(R.id.txtViewAlamat);
        tvTelepon = view.findViewById(R.id.txtNomorHP);
        tvEmail = view.findViewById(R.id.txtViewEmail);
        btnKeluar = view.findViewById(R.id.btnKeluar);

        apiInterface = ApiClient.getClient();

        UserResponse.UserModel model = AppPreference.getUser(getContext());
        tvNama.setText(model.username);
        tvAlamat.setText(model.alamat);
        tvTelepon.setText(model.noTelp);
        tvEmail.setText(model.email);

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeToken();
                AppPreference.removeUser(v.getContext());
                Intent intent = new Intent(v.getContext(), SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        return view;
    }

    public void removeToken() {
        apiInterface.postToken(
                AppPreference.getUser(getContext()).idUser,
                ""
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
}
